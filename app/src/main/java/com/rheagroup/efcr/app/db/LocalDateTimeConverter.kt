package com.rheagroup.efcr.app.db

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String = localDateTime.format(formatter)

    @TypeConverter
    fun toLocalDateTime(localDateTime: String): LocalDateTime = LocalDateTime.parse(localDateTime, formatter)
}
