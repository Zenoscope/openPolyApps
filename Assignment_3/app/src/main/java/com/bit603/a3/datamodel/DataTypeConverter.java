package com.bit603.a3.datamodel;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * Data type converter
 */

import androidx.room.TypeConverter;

import java.util.Date;

public class DataTypeConverter {
    @TypeConverter
    public static Date fromTimeStamp(Long value){
        return value == null? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date){
        return date == null? null : date.getTime();
    }
}
