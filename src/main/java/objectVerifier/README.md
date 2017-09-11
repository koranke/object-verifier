# DomainObjectValidator

## Purpose:
To assert that an expected domain object and an actual domain object are equal or pass comparison criteria in terms 
of the content of both.  No special supporting code in the domain object is needed.
There is no need to implement an equals method.

## Usage
While you can use the DomainObjectValidator directly, it's preferable to use the 
custom Assert.entitiesAreEqual method in rds-test-common core package.
The custom Assert class extends testng.Assert, so test classes should simply use
an import for rhapsody.qa.core.Assert instead of testng.Assert.

Example call: 

```java
Assert.entitiesAreEqual(actualTrack, expectedTrack);
```

All asserts also support a optional context message.  This can be used to provide helpful info in cases where an assert fails.
For example:

```java
Assert.entitiesAreEqual(actualTrack, expectedTrack, String.format("Testing trackId: %s.", trackId));
```


There are two supporting classes that are needed for more complex object comparisons: FieldsToCheck
and ValidatorParameters.  Basically, FieldsToCheck lets you control which fields to check
in an object and ValidatorParameters lets you control certain types of comparisons.  If you do not
specify a FieldsToCheck object, all fields will be checked.  If you do not specify
a ValidatorParameters object, all fields will be checked for exact matches and, in the case of
collections, exact order.

One other configuration element is the @Transient annotation.  This is not needed for domain classes that are
true POJOs.  However, if you are working with a domain class that includes additional methods besides the
standard getters and setters, and if one of those additional methods starts with "get", the method must be
annotated with @Transient to prevent it from being included in comparisons.

## Use Cases:

### Simple object, Verify all
The service under test returns an object that contains a number of native data type members 
(String, Integers, etc).  It also contains a list of String.
All fields need to be tested.  The list of String needs to match exactly, including order.

```java
Assert.entitiesAreEqual(actualTrack, expectedTrack)
```

### Simple object, verify only two fields
The service under test returns an object that contains a number of native data type members (String, Integers, etc).
It also contains a list of String. Only two fields need to be tested.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.addField("trackId")
				.addField("trackTitle");

Assert.entitiesAreEqual(actualTrack, expectedTrack, fieldsToCheck);
```

### Simple object, verify all fields except for one
The service under test returns an object that contains a number of native data type members (String, Integers, etc).
It also contains a list of String.  All fields except one need to be tested.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck().
				withKey(Track.class)
				.excludeField("trackId");

Assert.entitiesAreEqual(actualTrack, expectedTrack, fieldsToCheck);
```

### Simple object, do not check for exact order when comparing collections
The service under test returns an object that contains a number of native data type members (String, Integers, etc).
It also contains a list of String.  All fields except one need to be tested.  The order of the list of strings is not important.

```java
ValidatorParameters parameters = new ValidatorParameters()
				.setCollectionMatchType(CollectionMatchType.unsorted);

FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.excludeField("trackId");

Assert.entitiesAreEqual(actualTrack, expectedTrack, fieldsToCheck, parameters);
```

### Simple object, consider dates as equal if they are within 5 minutes of eachother
The service under test returns an object that contains a date member.
For the purpose of the test, if the actual date is withing 5 minutes of the expected
date, the test should pass.

```java
ValidatorParameters parameters = new ValidatorParameters()
				.setAcceptableTimeDeviation(5, ChronoUnit.MINUTES)

Assert.entitiesAreEqual(actualTrack, expectedTrack, parameters);
```

### Simple object, check all fields but don't check for exact order when comparing one collection
The service under test returns an object that contains a number of native data type members (String, Integers, etc).
It also contains two lists of String.  All fields need to be tested.  
The order of the list of strings is not important for one list but is important for
the other list.

Explanation:  Validator parameters can be global, in which case they apply to all fields
for all domain objects, or they can be field specific.  In the below example, the
FieldsToCheck method checkAllFieldsForCurrentKey is needed as we want to check all
fields but at the same time need to specify a field level validation parameter for one field.
Without the final call to checkAllFieldsForCurrentKey, only the field "listOfStringUnsorted"
would be checked.

Also note that the validator parameters are attached to the field, so default matching applies to all other fields.

```java
ValidatorParameters parameters = new ValidatorParameters()
				.setCollectionMatchType(CollectionMatchType.unsorted);

FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Track.class)
				.addField("listOfStringUnsorted", parameters)
				.checkAllFieldsForCurrentKey();

Assert.entitiesAreEqual(actualTrack, expectedTrack, fieldsToCheck);
```

### Complex object, check all fields and use default validation for all
The service under test returns an object that, in addition to some native data type
members, also contains members that are themselves domain objects, which in turn
may also have members that are domain objects.  In this case, all fields for the
domain object and all its child domain objects need to be tested.

```java
Assert.entitiesAreEqual(actualAlbum, expectedAlbum);
```

### Complex object, check all fields for the main domain except one.  Only check one field for a child domain object
The service under test returns an object that, in addition to some native data type
members, also contains members that are themselves domain objects, which in turn
may also have members that are domain objects.  In this case, all fields for the
parent domain object need to be checked, but only one field for one of its child domain 
objects need to be tested.

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Album.class)
				.excludeField("id")
				.withKey(Track.class)
				.addField("id");

Assert.entitiesAreEqual(actualAlbum, expectedAlbum, fieldsToCheck);
```

## Details:

### Supported data types
Assert.entitiesAreEqual can test the following.
* Two domain objects
* Two lists of domain objects
* Two arrays of domain objects

Within a domain object, Assert.entitiesAreEqual can test the following.
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
FieldsToCheck allows you to specify which fields to check and it also allows you to associate validator parameters at the
field level.  FieldsToCheck allows you to configure fields for both the main domain object and any domain objects that are child objects of the current
object under test.  This is done by setting the current key to the desired domain object and then adding or excluding fields as needed.

For example:

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Album.class)
				.excludeField("id")
				.withKey(Track.class)
				.addField("id");
```

In the above example, all fields in the Album object will be checked except for the "id" field.  The Album object contains
a list of child Track objects.  Only the "id" field will be checked for the child Track objects.

In cases where you need to specify a field-level ValidatorParameter and also need to check all fields, use the method
checkAllFieldsForCurrentKey() at the end of adding fields for a particular key.

For example:

```java
FieldsToCheck fieldsToCheck = new FieldsToCheck()
				.withKey(Album.class)
				.addField("releaseDate", parametersForReleaseDate)
				.checkAllFieldsForCurrentKey()
				.withKey(Track.class)
				.addField("id");
```

In the above example, all fields for Album will be checked, but only the "id" field will be checked for Track objects.

#### @Transient Annotation
If you are working with a domain class that includes additional methods besides the
standard getters and setters, and if any of those additional methods start with "get", the method must be
annotated with @Transient to prevent it from being included in comparisons.

### ValidatorParameters
Currently there are five kinds of configurations available through ValidatorParameters.
1. Case matching for strings (isIgnoreCase).  Default value is false.  If set to true, this will apply to
individual string fields/members or collections of strings.  It does not apply to maps of string values.
2. String matching method.  Default is for an exact match, but you can also specify starts-with, contains, and regex-match.
This applies to fields that are of type String or collections of strings.  It does not apply to maps of String at this time.
3. Numeric Matching method.  Default is for an exact match, but you can also specify lessThan, greaterThan, notEquals,
lessThanOrEquals, greaterThanOrEquals.  This only works for data types int, short, long, Integer, Short and Long.
4. Collection matching method.  Default is for an exact match, which means both collections must have the same number of items
and the items must be in the same order.  You can also specify an unsorted match, contains match or not contains.
5. Date/time acceptable deviation.  Default is for date/times to be considered a match if they are within 5 minutes of each other.
You can set this to any desired range.
6. Flag to skip date checking.  Default is false.  Use this if you want to globally skip date verifications.  If there are multiple
 date fields, this saves you from needing to exclude each field from checking via the FieldsToCheck object.

ValidatorParameters can be applied globally and locally for each field.  Field-level parameters take precedence over global parameters.

Example settings:

```java
ValidatorParameters parameters = new ValidatorParameters()
				.setAcceptableTimeDeviation(10, ChronoUnit.MINUTES)
				.setCollectionMatchType(CollectionMatchType.unsorted)
				.setStringMatchType(StringMatchType.startsWith)
				.isIngoreCase(true);

```

or

```java
ValidatorParameters parameters = new ValidatorParameters()
				.skipDates();

```


### Limitations
* Time ranged date comparisons will not be applied to collections of dates.  For example, if the object under test
contains a list of dates, then the comparison between the actual and expected objects will be for exact matches.
* Value comparisons are achieved through Introspection.  This is slower than getting values directly through an object.
This is not significant when comparing objects that have collections with multiple items in the 10s or even 100s, but
the time impact can be significant if the objects under comparison have collections with thousands of items.