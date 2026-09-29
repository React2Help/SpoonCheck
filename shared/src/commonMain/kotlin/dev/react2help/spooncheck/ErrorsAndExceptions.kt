package dev.react2help.spooncheck

enum class SaveError {
    INVALID_TASK,
    DUPLICATE_TASK,
    INTERNAL_ERROR
}

class SaveException(
    val error: SaveError
): Exception()
