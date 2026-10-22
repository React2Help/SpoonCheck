package dev.react2help.spooncheck.database

import androidx.room3.ColumnTypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class LocalDateConverter {
    @ColumnTypeConverter
    fun fromString(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value) }
    }

    @ColumnTypeConverter
    fun toString(value: LocalDate?): String? {
        return value?.toString()
    }
}

class LocalTimeConverter {
    @ColumnTypeConverter
    fun fromString(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(value) }
    }

    @ColumnTypeConverter
    fun toString(value: LocalTime?): String? {
        return value?.toString()
    }
}
