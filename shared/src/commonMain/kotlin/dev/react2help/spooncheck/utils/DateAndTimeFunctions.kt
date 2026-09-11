@file:Suppress

package dev.react2help.spooncheck.utils

import kotlin.random.Random
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.toLocalDateTime

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
@Suppress("MagicNumber")
fun generateRandomFutureDate(): LocalDate? {

    val year =
        Random.nextInt(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
            2099
        )
    val month = Random.nextInt(1, 12)
    val yearMonth = YearMonth(year, month)
    val day = Random.nextInt(1, yearMonth.numberOfDays)
    return LocalDate.orNull(year = year, month = month, day = day)
}
