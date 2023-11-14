package io.jcurtis.rockpaperscissors.game

import io.jcurtis.rockpaperscissors.ConfigReader
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class Game(private val challenger: Player, private val challenged: Player) : Listener {
    val gui = GameGUI()

    private var challengerChoice = Choice.NONE
    private var challengedChoice = Choice.NONE

    fun start() {
        challenger.openInventory(gui.inventory)
        challenged.openInventory(gui.inventory)
    }

    private fun checkWin() {
        val challengerMessage = when {
            challengerChoice == challengedChoice ->
                ConfigReader.getMsg("challenge-tie")
                    .replace("%player%", challenged.name)
            challengerChoice.beats(challengedChoice) ->
                ConfigReader.getMsg("challenge-won")
                    .replace("%player%", challenged.name)
            else ->
                ConfigReader.getMsg("challenge-lost")
                    .replace("%player%", challenged.name)
        }

        val challengedMessage = when {
            challengerChoice == challengedChoice ->
                ConfigReader.getMsg("challenge-tie")
                    .replace("%player%", challenger.name)
            challengedChoice.beats(challengerChoice) ->
                ConfigReader.getMsg("challenge-won")
                    .replace("%player%", challenger.name)
            else ->
                ConfigReader.getMsg("challenge-lost")
                    .replace("%player%", challenger.name)
        }

        challenger.sendMessage(challengerMessage)
        challenged.sendMessage(challengedMessage)

        challenger.playSound(challenger.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        challenged.playSound(challenged.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)

        end()
    }

    private fun end() {
        GameManager.removeGame(this)
    }

    fun handleClick(event: InventoryClickEvent) {
        if (event.slot < 3 || event.slot > 5) return

        val player = event.whoClicked as Player

        if (player == challenger) {
            challengerChoice = Choice.entries.toTypedArray()[event.slot - 3]

            player.sendMessage(
                ConfigReader.getMsg("item-chosen")
                    .replace("%item%", challengerChoice.name.lowercase())
            )

            player.sendMessage(
                ConfigReader.getMsg("challenge-waiting")
                    .replace("%player%", challenged.name)
            )
        } else if (player == challenged) {
            challengedChoice = Choice.entries.toTypedArray()[event.slot - 3]
            player.sendMessage(
                ConfigReader.getMsg("item-chosen")
                    .replace("%item%", challengedChoice.name.lowercase())
            )

            player.sendMessage(
                ConfigReader.getMsg("challenge-waiting")
                    .replace("%player%", challenger.name)
            )
        }

        player.closeInventory()

        if (challengerChoice != Choice.NONE && challengedChoice != Choice.NONE) {
            checkWin()
        }
    }
}

enum class Choice {
    ROCK,
    PAPER,
    SCISSORS,
    NONE
}

fun Choice.beats(other: Choice): Boolean {
    return (this == Choice.ROCK && other == Choice.SCISSORS) ||
            (this == Choice.PAPER && other == Choice.ROCK) ||
            (this == Choice.SCISSORS && other == Choice.PAPER)
}