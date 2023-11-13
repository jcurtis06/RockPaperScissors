package io.jcurtis.rockpaperscissors.commands

import io.jcurtis.rockpaperscissors.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.stream.Collectors

class ChallengeCMD : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return false
        if (!sender.hasPermission("rps.challenge")) {
            sender.sendMessage("You do not have permission to use this command.")
            return true
        }

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

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        if (command.name.equals("rps", ignoreCase = true)) {
            if (args.size == 1) {
                val completions = ArrayList<String>()
                completions.add("accept")

                if (sender is Player) {
                    val players = sender.server.onlinePlayers.stream()
                        .map { it.name }
                        .filter { it != sender.name }
                        .collect(Collectors.toList())

                    completions.addAll(players)
                }

                return completions
            }
        }
        return null
    }
}