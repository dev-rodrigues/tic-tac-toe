package br.com.devrodrigues

import br.com.devrodrigues.core.AIAgentPlayer
import br.com.devrodrigues.core.Board
import br.com.devrodrigues.core.Game
import br.com.devrodrigues.core.MonteCarloAgentPlayer
import br.com.devrodrigues.core.StatePlayed

fun main(args: Array<String>) {

    val game = Game(
        oPlayer = MonteCarloAgentPlayer(
            symbol = StatePlayed.CROSS,
            verbose = true
        ),
        xPlayer = AIAgentPlayer(
            symbol = StatePlayed.CIRCLE,
            verbose = true
        ),
        board = Board(),
        verbose = true
    )

    game.play()
}

