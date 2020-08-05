package com.dmc.minesweeper

import com.dmc.minesweeper.security.User
import grails.gorm.transactions.Transactional

@Transactional
class GameService {


    Game createGame(User user, Integer rows, Integer columns, Integer mines) {

        Game game = new Game(
                user: user,
                board: new Board(
                        rows: rows,
                        columns: columns,
                        mines: mines
                )
        )

        if (game.validate()) {
            game.save(failOnError: true)
        }

        return game
    }
}
