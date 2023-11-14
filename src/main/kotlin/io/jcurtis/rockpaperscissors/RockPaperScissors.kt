package io.jcurtis.rockpaperscissors

import io.jcurtis.rockpaperscissors.commands.ChallengeCMD
import io.jcurtis.rockpaperscissors.game.GameManager
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

@Suppress("unused")
class RockPaperScissors: JavaPlugin() {
    companion object {
        var messages = YamlConfiguration()
    }

    override fun onEnable() {
        createConfigs()
        server.pluginManager.registerEvents(GameManager, this)
        getCommand("rps")?.setExecutor(ChallengeCMD())
    }

    private fun createConfigs() {
        saveDefaultConfig()

        val customConfig = File(dataFolder, "messages.yml")
        if (!customConfig.exists()) {
            customConfig.parentFile.mkdirs()
            saveResource("messages.yml", false)
        }

        messages = YamlConfiguration.loadConfiguration(File(dataFolder, "messages.yml"))
    }
}