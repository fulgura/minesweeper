package com.dmc.minesweeper

import com.dmc.minesweeper.security.User
import grails.compiler.GrailsCompileStatic


@GrailsCompileStatic
class Game implements Entity {

    User user
    Board board

    enum GameStatus {
        PLAYING, WON, GAME_OVER
    }

    GameStatus status = GameStatus.PLAYING

    static constraints = {
        board nullable: false
    }

    Tile reveal(Integer row, Integer column) {

        Tile selectedTile = board.getTile row, column

        // TODO: replace with GameStatus Strategy
        if (selectedTile && selectedTile.hasMine()) {
            this.status = GameStatus.GAME_OVER
            this.save(failOnError: true)

        } else {
            selectedTile = Tile.withoutMine row, column
            board.addToTiles(selectedTile.save(failOnError: true))
            this.save(failOnError: true)
        }

        return selectedTile
    }

}
