package dev.react2help.spooncheck

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = sayHello(platform.name)
}

