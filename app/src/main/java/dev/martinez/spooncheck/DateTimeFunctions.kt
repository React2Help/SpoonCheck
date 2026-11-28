package dev.martinez.spooncheck

// androidMain
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLocalizedString(style: DateFormatStyle): String {
    val formatStyle =
        when (style) {
            DateFormatStyle.SHORT -> FormatStyle.SHORT
            DateFormatStyle.MEDIUM -> FormatStyle.MEDIUM
            DateFormatStyle.LONG -> FormatStyle.LONG
        }
    val formatter = DateTimeFormatter.ofLocalizedDate(formatStyle).withLocale(Locale.getDefault())
    return this.toJavaLocalDate().format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.toLocalizedString(): String {
    val formatter =
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
    return this.toJavaLocalTime().format(formatter)
}
