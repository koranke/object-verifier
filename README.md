# ObjectVerifier

## Purpose:
To assert that an expected custom object and an actual custom object are equal or pass other comparison criteria.  
No special supporting code in the custom class is needed.  There is no need to implement an equals method.

This framework's primary purpose is to make it easy to verify complex objects.  Expected results must be
constructed using an instance of the same class that is used to hold actual results.  For example, a web
service call returns a JSON document that is deserialized into a Java object.  You can use this framework
to verify the content of the deserialized object by populating another instance of the object with expected
results and then using the Verify.that() method.

## Usage
While you can use the ObjectVerifier directly, it's preferable to use the 
custom Verify.that() method.

Example call: 

```java
Verify.that(actualTrack).isEqualTo(expectedTrack);
```


There are two supporting classes that are needed for more complex object comparisons: FieldsToCheck
and VerificationRule.  Basically, FieldsToCheck lets you control which fields to check
in an object and VerificationRule lets you control how to compare two objects.  If you do not
specify a FieldsToCheck object, all fields will be checked.  If you do not specify VerificationRule, 
all fields will be checked for exact matches and, in the case of collections, exact order.

One other configuration element is the @Transient annotation.  If you are working with a class that 
includes additional methods besides the standard getters and setters, and any of those additional 
methods start with "get", the method must be annotated with @Transient to prevent it from being included 
in comparisons.

## Use Cases:

### Simple object, Verify all
The object under test contains a number of native data type members.  It also contains a list of String.
All fields need to be tested.  The list of String needs to match exactly, including order.  Since all fields
need to be checked and the default verification rules are what are needed, there is no need to specify
fields to check or verification rules.

```java
Verify.that(actualTrack).isEqualTo(expectedTrack)
```

### Simple object, verify only two fields
The object under test contains a number of native data type members.
It also contains a list of String. Only two fields need to be tested.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.includeField("trackId")
				.includeField("trackTitle");

Verify.that(actualTrack).usingFields(fieldsToCheck).isEqualTo(expectedTrack)
```

### Simple object, verify all fields except for one
The object under test contains a number of native data type members.
It also contains a list of String.  All fields except one need to be tested.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck().
				withKey(Track.class)
				.excludeField("trackId");

Verify.that(actualTrack).usingFields(fieldsToCheck).isEqualTo(expectedTrack)
```

### Simple object, do not check for exact order when comparing collections
The objecct under test contains a number of native data type members.
It also contains a list of String.  All fields except one need to be tested.  The order of the list of strings is not important.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.excludeField("trackId");

Verify.that(actualTrack)
		.usingFields(fieldsToCheck)
		.usingRule(new ListUnsortedRule())
		.isEqualTo(expectedTrack);
```

### Simple object, consider dates as equal if they are within 5 minutes of each other
The object under test contains a date member.
For the purpose of the test, if the actual date is withing 5 minutes of the expected
date, the test should pass.

```java
Verify.that(actualTrack)
		.usingRule(new DateTimeInRangeRule(5, ChronoUnit.MINUTES))
		.isEqualTo(expectedTrack);
```

### Simple object, check all fields but don't check for exact order when comparing one collection
The object under test contains a number of native data type members.
It also contains two lists of String.  All fields need to be tested.  
The order of the list of strings is not important for one list but is important for
the other list.

Explanation:  Verification rules can be global, in which case they apply to all fields
for all objects, or they can be field specific.  In the below example, the
FieldsToCheck method checkAllFieldsForCurrentKey is needed as we want to check all
fields but at the same time need to specify a field level verification rule for one field.
Without the final call to checkAllFieldsForCurrentKey, only the field "listOfStringUnsorted"
would be checked.

Also note that the verification rule is attached to the field, so default matching applies to all other fields.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.includeField("listOfStringUnsorted", new ListUnsortedRule())
				.checkAllFieldsForCurrentKey();

Verify.that(actualTrack)
		.usingFields(fieldsToCheck)
		.isEqualTo(expectedTrack);
```

### Complex object, check all fields and use default validation for all
The object under test contains some native data type
members and also contains members that are themselves domain objects, which in turn
may also have members that are domain objects.  In this case, all fields for the
domain object and all its child domain objects need to be tested.

```java
Verify.that(actualTrack).isEqualTo(expectedTrack);
```

### Complex object, check all fields for the main domain except one.  Only check one field for a child domain object
The object under test contains some native data type
members and also contains members that are themselves domain objects, which in turn
may also have members that are domain objects.  In this case, all fields for the
parent domain object need to be checked, but only one field for one of its child domain 
objects need to be tested.

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

Within a domain object, the following can be tested:
* Native data types: boolean, byte, short, int, long, char
* Simple data types: String, Boolean, Byte, Short, Integer, Long, Double, Float, Char
* Dates/Time: Calendar, Date, Timestamp, LocalDate, LocalDateTime
* Other domain ojbects
* Lists of any data type
* Arrays of any data type
* Sets of any data type
* Maps of any data type

### Unsupported data types
Support for the following has not been added yet.
* float
* double

### FieldsToCheck
FieldsToCheck allows you to specify which fields to check and it also allows you to associate verification rules at the
field level.  FieldsToCheck allows you to configure fields for both the main domain object and any domain objects that are child objects of the current
object under test.  This is done by setting the current key to the desired domain object and then adding or excluding fields as needed.

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

Finally, there is a shortcut for adding fields.  If you don't need to specify the class key for the field, you
can simply pass in the field names as string values in the Verify.that() method.  In this case, verification
will run on any field that matches the name, regardless of the class that the field name is associated with.

For example:

```java
Verify.that(actualTrack).usingFields("id", "releaseDate").isEqualTo(expectedTrack);
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



### Limitations
* Value comparisons are achieved through Introspection.  This is slower than getting values directly through an object.
This is not significant when comparing objects that have collections with multiple items in the 10s or even 100s, but
the time impact can be significant if the objects under comparison have collections with thousands of items.