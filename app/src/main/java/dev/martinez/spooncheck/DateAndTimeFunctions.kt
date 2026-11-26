@file:Suppress("WildcardImport", "MagicNumber")

package dev.martinez.spooncheck

// commonMain
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.random.Random
import kotlin.time.Clock
import kotlinx.datetime.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.*
import kotlinx.datetime.toLocalDateTime


enum class DateFormatStyle {
    SHORT,
    MEDIUM,
    LONG
}

// Fallback format if needed for specific non-localized patterns
private val customFallbackDate =
    LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        day(padding = Padding.NONE)
        chars(", ")
        year()
    }

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.formatSmart(
    now: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    useCustomFallback: Boolean = false
): String {
    val today = now.date
    val targetDate = this.date
    val timePart = this.time.toLocalizedString()

    // 1. Check relative time for today
    if (targetDate == today) {
        val diffMinutes = (now.hour * 60 + now.minute) - (this.hour * 60 + this.minute)
        return when {
            diffMinutes in 0..1 -> "Just now"
            diffMinutes in 2..59 -> "$diffMinutes min ago"
            else -> "Today at $timePart"
        }
    }

    // 2. Check relative dates
    val datePart =
        when (targetDate) {
            today.minus(1, DateTimeUnit.DAY) -> "Yesterday"
            today.plus(1, DateTimeUnit.DAY) -> "Tomorrow"
            else ->
                if (useCustomFallback) {
                    targetDate.format(customFallbackDate)
                } else {
                    targetDate.toLocalizedString(DateFormatStyle.MEDIUM)
                }
        }

    return "$datePart at $timePart"
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
fun TextToTime(text: String): LocalTime {
    /* converts the text in a TextField, which is formatted as follows:
       into a LocalTime
    */
    val hour = text.substring(0, 2).toInt()
    val minute = text.substring(2, 4).toInt()
    val time = LocalTime(hour, minute, 0, 0)
    return time
}

@Suppress("MagicNumber")
fun TextToDate(text: String): LocalDate {
    /* converts the text in a TextField, which is formatted as follows:
       into a LocalDate
    */
    val month = text.substring(0, 2).toInt()
    val day = text.substring(2, 4).toInt()
    val year = text.substring(4, 6).toInt()
    val date = LocalDate(year = year, month = month, day = day)
    return date
}

@Suppress("MagicNumber")
fun generateRandomFutureDate(): LocalDate {

    val year =
        Random.nextInt(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
            2099
        )
    val month = Random.nextInt(1, 12)
    val yearMonth = YearMonth(year, month)
    val day = Random.nextInt(1, yearMonth.numberOfDays)
    return LocalDate(year = year, month = month, day = day)
}

fun generateRandomFutureTime(): LocalTime {
    return LocalTime(hour = Random.nextInt(13), minute = Random.nextInt(60))
}
