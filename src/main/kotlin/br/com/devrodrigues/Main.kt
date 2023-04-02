package br.com.devrodrigues

import br.com.devrodrigues.core.Board
import br.com.devrodrigues.core.Game
import br.com.devrodrigues.core.HumanAgentPlayer
import br.com.devrodrigues.core.MiniMaxAgentPlayer
import br.com.devrodrigues.core.StatePlayed

fun main(args: Array<String>) {

    val game = Game(
        oPlayer = HumanAgentPlayer(
            symbol = StatePlayed.CROSS,
            verbose = false
        ),
        xPlayer = MiniMaxAgentPlayer(
            symbol = StatePlayed.CIRCLE,
            verbose = false
        ),
        board = Board(),
        verbose = true
    )

    game.play()
}

