package dev.react2help.spooncheck.ui

// The three variants of the Daily Reflection screen, each with its own heading message.
enum class DailyReflectionVariant(val message: String) {
    Success("Congratulations!\nYou Completed All Your Tasks!"),
    SuccessPartial("Congratulations!\nYou Completed Most Of Your Tasks!"),
    RoughDay("Today might’ve been a hard day.\nThat’s Ok, take some time to reflect.")
}
