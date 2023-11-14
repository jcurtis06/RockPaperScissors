package io.jcurtis.rockpaperscissors

object ConfigReader {
    val prefix = RockPaperScissors.messages.get("prefix") as String
    const val defaultColor = "§7"
    const val highlightColor = "§a"

    fun getMsg(key: String): String {
        return "$prefix ${RockPaperScissors.messages.get(key) as String}"
    }
}