package com.rheagroup.efcr.util

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import org.ocpsoft.prettytime.PrettyTime

fun prettyPrintDateTime(dateTime: LocalDateTime): String {
    val prettyTime = PrettyTime()
    return prettyTime.format(dateTime)
}
