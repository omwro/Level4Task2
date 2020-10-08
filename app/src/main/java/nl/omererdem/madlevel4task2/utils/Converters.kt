package nl.omererdem.madlevel4task2.utils

import androidx.room.TypeConverter
import java.util.*
import nl.omererdem.madlevel4task2.model.Result

// Type converter calls for the dates and results
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromResult(value: Result?): Int? {
        return value?.getId()
    }

    @TypeConverter
    fun toResult(value: Int?): Result? {
        return value?.let { Result.findResult(it) }
    }
}