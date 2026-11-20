package dev.react2help.spooncheck

// androidMain
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
actual fun LocalDate.toLocalizedString(style: DateFormatStyle): String {
    val formatStyle = when (style) {
        DateFormatStyle.SHORT -> FormatStyle.SHORT
        DateFormatStyle.MEDIUM -> FormatStyle.MEDIUM
        DateFormatStyle.LONG -> FormatStyle.LONG
    }
    val formatter = DateTimeFormatter.ofLocalizedDate(formatStyle)
        .withLocale(Locale.getDefault())
    return this.toJavaLocalDate().format(formatter)
}

actual fun LocalTime.toLocalizedString(): String {
    val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
    return this.toJavaLocalTime().format(formatter)
}
