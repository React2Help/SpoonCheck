package dev.react2help.spooncheck

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime

actual fun LocalDate.toLocalizedString(style: DateFormatStyle): String {
    val formatStyle =
        when (style) {
            DateFormatStyle.SHORT -> FormatStyle.SHORT
            DateFormatStyle.MEDIUM -> FormatStyle.MEDIUM
            DateFormatStyle.LONG -> FormatStyle.LONG
        }
    val formatter = DateTimeFormatter.ofLocalizedDate(formatStyle).withLocale(Locale.getDefault())
    return this.toJavaLocalDate().format(formatter)
}

actual fun LocalTime.toLocalizedString(): String {
    val formatter =
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
    return this.toJavaLocalTime().format(formatter)
}
