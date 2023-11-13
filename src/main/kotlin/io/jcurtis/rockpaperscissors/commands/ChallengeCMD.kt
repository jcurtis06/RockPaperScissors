package io.jcurtis.rockpaperscissors.commands

import io.jcurtis.rockpaperscissors.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChallengeCMD : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return false

        if (args == null || args.isEmpty()) {
            sender.sendMessage("Usage: /rps <player>|accept")
            return true
        }

        if (args[0] == "accept") {
            GameManager.accept(sender)
        } else {
            GameManager.challenge(sender, sender.server.getPlayer(args[0]) ?: run {
                sender.sendMessage("Player not found.")
                return true
            })
        }

        return true
    }
}