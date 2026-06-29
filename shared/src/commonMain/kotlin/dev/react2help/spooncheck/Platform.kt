package dev.react2help.spooncheck

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform