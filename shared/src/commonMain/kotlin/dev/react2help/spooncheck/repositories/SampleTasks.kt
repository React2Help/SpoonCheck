package dev.react2help.spooncheck.repositories

import dev.react2help.spooncheck.database.Task
import dev.react2help.spooncheck.modelsandstate.Category
import dev.react2help.spooncheck.modelsandstate.Priority
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
            priority = Priority.Critical,
            category = Category.Work,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(10, 22, 0),
        ),
        Task(
            id = 2L,
            title = "Clean The Dishes",
            description = "Wash everything on the left side of the sink",
            spoons = 2,
            priority = Priority.High,
            category = Category.Hygiene,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(10, 22, 0),
        ),
        Task(
            id = 3L,
            title = "Call The Pharmacy",
            description = "Refill the prescription before the weekend",
            spoons = 1,
            priority = Priority.High,
            category = Category.Hygiene,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(15, 0, 0),
        ),
        Task(
            id = 4L,
            title = "Read Chapter 4",
            description = "Biology reading for Thursday",
            spoons = 2,
            priority = Priority.Medium,
            category = Category.School,
            dueDate = LocalDate(2025, 9, 23),
            dueTime = LocalTime(10, 22, 0),
        ),
        Task(
            id = 5L,
            title = "Shower",
            description = "",
            spoons = 2,
            priority = Priority.Medium,
            category = Category.Hygiene,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(10, 22, 0),
            isDone = true,
        ),
        Task(
            id = 6L,
            title = "Answer Emails",
            description = "Just the urgent ones",
            spoons = 1,
            priority = Priority.Low,
            category = Category.Work,
            dueDate = LocalDate(2025, 9, 22),
            dueTime = LocalTime(10, 22, 0),
            isDone = true,
        ),
    )
