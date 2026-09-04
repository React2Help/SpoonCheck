package dev.react2help.spooncheck.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class DateAndTimeFunctions {
    fun LocalDateTime.format() = toString().substringBefore('T')
}

const val HoursInDay = 24

fun LocalTime.plusHoursSimple(hours: Int): LocalTime {
    val newHour = (this.hour + hours) % HoursInDay
    return LocalTime(
        hour = newHour,
        minute = this.minute,
        second = this.second,
        nanosecond = this.nanosecond
    )
}
