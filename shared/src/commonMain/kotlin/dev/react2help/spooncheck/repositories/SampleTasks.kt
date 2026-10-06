package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
import dev.react2help.spooncheck.modelsandstate.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

// Seed data for InMemoryTaskRepository and previews until tasks are persisted.
val SampleTasks =
    listOf(
        Task(
            id = 1L,
            title = "Pay Rent",
            description = "This is super important, do not forget!",
            spoons = 3,
            priority = Priority.critical,
            category = Category.WORK,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(10, 22, 0),
        ),
        Task(
            id = 2L,
            title = "Clean The Dishes",
            description = "Wash everything on the left side of the sink",
            spoons = 2,
            priority = Priority.high,
            category = Category.HYGIENE,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = null,
        ),
        Task(
            id = 3L,
            title = "Call The Pharmacy",
            description = "Refill the prescription before the weekend",
            spoons = 1,
            priority = Priority.high,
            category = Category.HYGIENE,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(15, 0, 0),
        ),
        Task(
            id = 4L,
            title = "Read Chapter 4",
            description = "Biology reading for Thursday",
            spoons = 2,
            priority = Priority.medium,
            category = Category.SCHOOL,
            dueDate = LocalDate(2025, 9, 23),
            dueTime = null,
        ),
        Task(
            id = 5L,
            title = "Shower",
            description = "",
            spoons = 2,
            priority = Priority.medium,
            category = Category.HYGIENE,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = null,
            isDone = true,
        ),
        Task(
            id = 6L,
            title = "Answer Emails",
            description = "Just the urgent ones",
            spoons = 1,
            priority = Priority.low,
            category = Category.WORK,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = null,
            isDone = true,
        ),
    )
