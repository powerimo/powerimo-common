# Powerimo Java Common library

## Overview

Common library with utils.

## Database utils

### readEnumValue

read a value from a ResultSet and convert it into an enum value of the specified enum type

```java
public enum SampleEnum {
    STATUS0,
    STATUS1
}

// resultSet contains `status_field_name` with value 'STATUS0'
var v1 = DatabaseUtils.readEnumValue(resultSet, "status_field_name", SampleEnum.class);
// v1=SampleEnum.STATUS0
```

## Date utils

### formatDurationHMS

Returns formatted string 'hh:mm:ss' or 'hh:mm:ss.nnn' as a duration between two dates.

```java
var d1 = LocalDateTime.of(2020,1,1,1,1, 5);
var d2 = LocalDateTime.of(2020,1,10,10,15, 10, 666);
var result = DateUtils.formatDurationHMS(d1.toInstant(ZoneOffset.UTC), d2.toInstant(ZoneOffset.UTC), false);
// result is "225:14:05";
```

