package io.jcurtis.rockpaperscissors

import io.jcurtis.rockpaperscissors.commands.ChallengeCMD
import io.jcurtis.rockpaperscissors.game.GameManager
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class RockPaperScissors: JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(GameManager, this)
        getCommand("rps")?.setExecutor(ChallengeCMD())
    }
}