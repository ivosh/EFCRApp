package com.rheagroup.efcr.util

import java.time.LocalDateTime
import org.ocpsoft.prettytime.PrettyTime

fun prettyPrintDateTime(dateTime: LocalDateTime): String {
    val prettyTime = PrettyTime()
    return prettyTime.format(dateTime)
}
