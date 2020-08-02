package com.dmc.minesweeper

import com.dmc.minesweeper.data.GameDataTestBuilder
import spock.lang.Specification

import static com.dmc.minesweeper.Game.GameStatus.GAME_OVER
import static com.dmc.minesweeper.Game.GameStatus.PLAYING
import static com.dmc.minesweeper.util.GameMatcher.*
import static spock.util.matcher.HamcrestSupport.expect

class GameSpec extends Specification implements GameDataTestBuilder {


    void "test can create a Game defining columns, rows and mines"() {

        when:
            aGame {
                withRows 12
                withColumns 10
                withMines 4
            }

        then:
            verifyAll game, {
                hasColumns 10
                hasRows 10
                hasMines 10
                isStatus PLAYING
            }
    }

    void 'test can trow an IndexOutOfBoundsException if reveals has an incorrect row value'() {

        given:
            aGame {
                with rows: 12 and columns: 10 and mines: 4
            }

        when:
            game.reveal(-1, 2)

        then:
            thrown IndexOutOfBoundsException

        when:
            game.reveal(1000, 2)

        then:
            thrown IndexOutOfBoundsException
    }

    void 'test can trow an IndexOutOfBoundsException if reveals has an incorrect column value'() {

        given:
            aGame {
                with rows: 12 and columns: 10 and mines: 4
            }

        when:
            game.reveal(1, -1)

        then:
            thrown IndexOutOfBoundsException

        when:
            game.reveal(1, 20)

        then:
            thrown IndexOutOfBoundsException
    }


    void 'test can reveal a tile without mine without changing game status'() {

        given:
            aGame {
                with rows: 12 and columns: 10
                withMine 1, 1
                withMine 2, 4
            }

        when:
            game.reveal 5, 5

        then:
            expect game, isStatus(PLAYING)

    }

    void 'test can finish game if a tile without contains a mine'() {

        given:
            aGame {
                with rows: 12 and columns: 10
                withMine 1, 1
                withMine 2, 4
            }

        when:
            game.reveal 1, 1

        then:
            expect game, isStatus(GAME_OVER)

    }

}
