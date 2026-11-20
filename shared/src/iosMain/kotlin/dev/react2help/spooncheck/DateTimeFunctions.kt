package dev.react2help.spooncheck

// iosMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import platform.Foundation.*

actual fun LocalDate.toLocalizedString(style: DateFormatStyle): String {
    val calendar = NSCalendar.currentCalendar
    val components =
        NSDateComponents().apply {
            year = this@toLocalizedString.year.toLong()
            month = this@toLocalizedString.monthNumber.toLong()
            day = this@toLocalizedString.dayOfMonth.toLong()
        }
    val nsDate = calendar.dateFromComponents(components) ?: return this.toString()

    val formatter =
        NSDateFormatter().apply {
            locale = NSLocale.currentLocale
            timeStyle = NSDateFormatterNoStyle
            dateStyle =
                when (style) {
                    DateFormatStyle.SHORT -> NSDateFormatterShortStyle
                    DateFormatStyle.MEDIUM -> NSDateFormatterMediumStyle
                    DateFormatStyle.LONG -> NSDateFormatterLongStyle
                }
        }
    return formatter.stringFromDate(nsDate)
}

actual fun LocalTime.toLocalizedString(): String {
    val calendar = NSCalendar.currentCalendar
    val components =
        NSDateComponents().apply {
            hour = this@toLocalizedString.hour.toLong()
            minute = this@toLocalizedString.minute.toLong()
        }
    val nsDate = calendar.dateFromComponents(components) ?: return this.toString()

    val formatter =
        NSDateFormatter().apply {
            locale = NSLocale.currentLocale
            dateStyle = NSDateFormatterNoStyle
            timeStyle = NSDateFormatterShortStyle // Automatically uses device 12h/24h setting
        }
    return formatter.stringFromDate(nsDate)
}
