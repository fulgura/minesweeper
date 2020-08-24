package com.dmc.minesweeper

import com.dmc.minesweeper.security.User
import grails.compiler.GrailsCompileStatic

import static Position.Pos


@GrailsCompileStatic
class Game implements Entity {

    User user
    Board board

    enum GameStatus {
        PLAYING, WON, GAME_OVER
    }

    GameStatus status = GameStatus.PLAYING

    static constraints = {
        user nullable: false
        board nullable: false
    }

    void uncover(Integer row, Integer column) {

        Tile uncoveredTile = board.uncover(Pos(row, column))
        // TODO: Throw an exception if a cell has not a bomb

        if (uncoveredTile.isMined()) {
            this.status = GameStatus.GAME_OVER
            // TODO: release all bombs!
        }

        this.save(failOnError: true)
    }

}
