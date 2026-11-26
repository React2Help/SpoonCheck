@file:Suppress("SwallowedException")

package dev.martinez.spooncheck.database

import androidx.room3.ColumnTypeConverter
import dev.martinez.spooncheck.modelsandstate.Category
import dev.martinez.spooncheck.modelsandstate.Priority
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

class PriorityConverter {
    @ColumnTypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @ColumnTypeConverter
    fun toPriority(value: String): Priority {
        return try {
            Priority.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Priority.Low // Fallback default if an unknown value exists
        }
    }
}

class CategoryConverter {
    @ColumnTypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @ColumnTypeConverter
    fun toCategory(value: String): Category {
        return try {
            Category.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Category.Work
        }
    }
}
