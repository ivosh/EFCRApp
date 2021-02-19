package com.rheagroup.efcr.util

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeConverter {
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private val millisecondsFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String = localDateTime.format(formatter)

    @TypeConverter
    fun toLocalDateTime(localDateTime: String): LocalDateTime {
        return try {
            LocalDateTime.parse(localDateTime, formatter)
        } catch (ex: DateTimeParseException) {
            LocalDateTime.parse(localDateTime, millisecondsFormatter)
        }
    }
}
