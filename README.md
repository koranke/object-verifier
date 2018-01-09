# ObjectVerifier

## Purpose:
QA test framework for assertions.  To assert that an expected custom object and an actual 
custom object are equal or pass other comparison criteria.  No special supporting code in 
the custom class is needed.  There is no need to implement an equals method.

This framework's primary purpose is to make it easy to verify complex objects.  Expected results must be
constructed using an instance of the same class that is used to hold actual results.  For example, a web
service call returns a JSON document that is deserialized into a Java object.  You can use this framework
to verify the content of the deserialized object by populating another instance of the object with expected
results and then using the Verify.that() method.

Normally, a test might include a series of assertions for each member of an object.  For example, the object
under test includes 10 different members.  In this case, a test would need to include 10 different assertions.
When using this framework, the test only needs to make one assertion.  Of course, it still needs to specify each of the
10 expected result values by assigning them to a companion "expected result" object.
  

## Usage
While you can use the ObjectVerifier directly, it's preferable to use the 
custom Verify.that() method.

Example call: 

```java
Verify.that(actualTrack).isEqualTo(expectedTrack);
```


There are two supporting classes that are needed for more complex object comparisons: FieldsToCheck
and VerificationRule.  FieldsToCheck lets you control which fields to check
in an object and VerificationRule lets you control how to compare two objects.  If you do not
specify a FieldsToCheck object, all fields will be checked.  If you do not specify any VerificationRules, 
all fields will be checked for exact matches.

One other configuration element is the @Transient annotation.  If you are working with a class that 
includes additional methods besides the standard getters and setters, and any of those additional 
methods start with "get", the method must be annotated with @Transient to prevent it from being included 
in comparisons.

## Use Cases:

### Verify all
If all object members need to be checked and the default verification rules are what are needed, 
there is no need to specify fields to check or verification rules.

```java
Verify.that(actualTrack).isEqualTo(expectedTrack)
```

### Verify only two fields
If only two members of an object need to be tested, the fields to check must be specified.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.includeField("trackId")
				.includeField("trackTitle");

Verify.that(actualTrack).usingFields(fieldsToCheck).isEqualTo(expectedTrack)
```

### Verify all fields except for one
If all fields except one need to be tested, specify the field to exclude.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck().
				withKey(Track.class)
				.excludeField("trackId");

Verify.that(actualTrack).usingFields(fieldsToCheck).isEqualTo(expectedTrack)
```

### Do not check for exact order when comparing fields that are collections
If the object under test includes one or more members that are collections,
 and if the order of those collections is not important, use the ListUnsortedRule.

```java
Verify.that(actualTrack).usingRule(new ListUnsortedRule()).isEqualTo(expectedTrack);
```

### Consider dates as equal if they are within 5 minutes of each other
If the object under test includes a date value, it's unlikely that the actual time
and the expected time will precisely match.  If that is the case, using the DateTimeInRange rule.
In the below example, ff the actual date is withing 5 minutes of the expected date, the test will pass.

```java
Verify.that(actualTrack)
		.usingRule(new DateTimeInRangeRule(5, ChronoUnit.MINUTES))
		.isEqualTo(expectedTrack);
```

### Check all fields but don't check for exact order when comparing one collection
Verification rules can be global, in which case they apply to all fields
for all objects, or they can be field specific.  In the below example, we want to check all
fields but at the same time need to specify a field-specific verification rule for one field.
The method checkAllFieldsForCurrentKey forces all fields to be checked, not only the field 
specified by "includeField()".

Since the verification rule is attached to the field, default matching applies to all other fields.
If the object under test had another member that was also a list, that member would not be verified
with the unsorted rule.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.includeField("listOfStringUnsorted", new ListUnsortedRule())
				.checkAllFieldsForCurrentKey();

Verify.that(actualTrack)
		.usingFields(fieldsToCheck)
		.isEqualTo(expectedTrack);
```

### Child objects: Checking all fields and using default validation for all
If the object under test has a member that itself is a custom object, the child object
will also be verified.  For example, if the Track object includes a list of related Track objects,
the following call will verify both the parent Track object and the child track objects.

```java
Verify.that(actualTrack).isEqualTo(expectedTrack);
```

### Child objects: check all fields for the parent except one.  Only check one field for a child object
In this case, for the parent object, all members except one need to be checked.
For the child object, only one field needs to be checked. 

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Album.class)
				.excludeField("id")
				.withKey(Track.class)
				.includeField("id");

Verify.that(actualTrack)
		.usingFields(fieldsToCheck)
		.isEqualTo(expectedTrack);
```

## Details:

### Supported data types
Verify.that() can test the following.
* Two domain objects
* Two lists of domain objects
* Two arrays of domain objects

Within a custom object, the following can be tested:
* Native data types: boolean, byte, short, int, long, double, float, char
* Simple data types: String, Boolean, Byte, Short, Integer, Long, Double, Float, Char
* Dates/Time: Calendar, Date, Timestamp, LocalDate, LocalDateTime
* Other custom objects
* Lists of any data type
* Arrays of any data type
* Sets of any data type
* Maps of any data type
* Any object that implements the equals method


### FieldsToCheck
FieldsToCheck allows you to specify which fields to check and it also allows you to associate verification rules at the
field level.  FieldsToCheck allows you to configure fields for both a parent object and any child objects.
 This is done by setting the current key to the desired custom object and then adding or excluding fields as needed.

For example:

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Album.class)
				.excludeField("id")
				.withKey(Track.class)
				.includeField("id");
```

In the above example, all fields in the Album object will be checked except for the "id" field.  The Album object contains
a list of child Track objects.  Only the "id" field will be checked for the child Track objects.

In cases where you need to specify a field-level VerificationRule and also need to check all fields, use the method
checkAllFieldsForCurrentKey() at the end of adding fields for a particular key.

For example:

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Album.class)
				.includeField("releaseDate", new DateTimeInRangeRule(5, ChronoUnit.MINUTES))
				.checkAllFieldsForCurrentKey()
				.withKey(Track.class)
				.includeField("id");
```

In the above example, all fields for Album will be checked, but only the "id" field will be checked for Track objects.

You do not have to specify a key.  Omitting the "key" statement will result in all fields matching the specified name
to be included or excluded.  For example, the object under test has a member named "id".  It also has a child
object and that object also has a member named "id".  The following code would result in the "id" field for both
the parent and child getting checked.  If the object under test has no child objects or if the member names that
need to be checked are unique across both parent and child objects, you can simplify the statement by omitting
the key specifiers.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.includeField("id");
```


Finally, there is a shortcut for adding fields.  If you don't need to specify the class key for the field, you
can simply pass in the field names as string values.  In this case, verification
will run on any field that matches the name, regardless of the class that the field name is associated with.

For example:

```java
Verify.that(actualTrack).usingFields("id", "releaseDate").isEqualTo(expectedTrack);
```

Verification will fail if you misspell an object member name.  For example, if the object under test
includes a member named "title" but we try to verify a field named "tittle", verification would fail
with a message that the field "tittle" could not be found.

```java
Verify.that(actualTrack).usingFields("tittle").isEqualTo(expectedTrack);
```



#### @Transient Annotation
If you are working with a domain class that includes additional methods besides the
standard getters and setters, and if any of those additional methods start with "get", the method must be
annotated with @Transient to prevent it from being included in comparisons.

### Verification Rules
A verification rule controls how two items are compared.  For example, the StringContainsRule will assert
that the actual result contains the expected result.

Verification rules can be applied globally or locally for a particular field.  
Field-level rules take precedence over global rules.  Verification rules contain application rules that
control which data types the rule is applied to.  For example, the StringExactMatchRule contains a
StringApplicationRule, consequently this verification rule only applies to items of type String.  

When evaluating global verification rules and field-level rules, global rules will not be used in any
case where a field-level rule uses the same application rule.  For example, if there is a global rule
for StringExactMatchRule and a field-level rule for StringContainsRule, since both use the same 
StringApplicationRule, only the field-level rule will be used (for that particular field).

Example collection of rules:

```java
List<VerificationRule> rules = Lists.newArrayList(
	new NumberInRangeRule(5),
	new ListUnsortedRule()
	new StringContainsRule()		
);

```

### Notes
* If both an actual and expected item are null, the comparison will pass.
* If only one of the compared items is null, the comparison will fail.


### Limitations
* Value comparisons are achieved through Introspection.  This is slower than getting values directly through an object.
This is not significant when comparing objects that have collections with multiple items in the 10s or even 100s, but
the time impact can be significant if the objects under comparison have collections with thousands of items.