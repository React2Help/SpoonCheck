package dev.react2help.spooncheck.utils

import kotlinx.datetime.LocalDateTime

class DateAndTimeFunctions {
    fun LocalDateTime.format() = toString().substringBefore('T')
}
