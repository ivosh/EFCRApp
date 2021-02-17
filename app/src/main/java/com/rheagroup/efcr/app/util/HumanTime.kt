package com.rheagroup.efcr.app.util

import org.ocpsoft.prettytime.PrettyTime
import java.time.LocalDateTime
import java.time.ZoneOffset

fun prettyPrintDateTime(dateTime: LocalDateTime): String {
    val prettyTime = PrettyTime()
    return prettyTime.format(dateTime)
}