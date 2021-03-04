package com.rheagroup.efcr.util

import org.ocpsoft.prettytime.PrettyTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun prettyPrintElapsed(dateTime: LocalDateTime): String {
    val prettyTime = PrettyTime()
    return prettyTime.format(dateTime)
}

fun prettyPrintDetailed(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)
    return formatter.format(dateTime)
}
