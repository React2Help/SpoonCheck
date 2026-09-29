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
import kotlin.math.min

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
fun TextToTime(text: String): LocalTime {
    /* converts the text in a TextField, which is formatted as follows:
        into a LocalTime
     */
    val hour = text.substring(0,2).toInt()
    val minute = text.substring(3, 4).toInt()
    val time = LocalTime(hour, minute, 0,0)
    return time
}
fun TextToDate(text:String): LocalDate {
    /* converts the text in a TextField, which is formatted as follows:
        into a LocalDate
     */
    val month = text.substring(0,2).toInt()
    val day = text.substring(3, 5).toInt()
    val year = text.substring(6, 8).toInt()
    val date = LocalDate(
        year = year,
        month = month,
        day = day
    )
    return date
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
