package com.dmc.minesweeper

import com.dmc.minesweeper.data.GameDataTest
import spock.lang.Specification

import static com.dmc.minesweeper.Game.GameStatus.GAME_OVER
import static com.dmc.minesweeper.Game.GameStatus.PLAYING
import static com.dmc.minesweeper.util.GameMatcher.*
import static spock.util.matcher.HamcrestSupport.expect

/**
 * @see https://en.wikipedia.org/wiki/Minesweeper_(video_game)
 *
 * Minesweeper has a very basic gameplay style.
 * In its original form, mines are scattered throughout a board.
 * This board is divided into cells, which have three states: uncovered, covered and flagged.
 * A covered cell is blank and clickable, while an uncovered cell is exposed,
 * either containing a number (the mines adjacent to it), or a mine.
 *
 * When a cell is uncovered by a player click, and if it bears a mine, the game ends.
 *
 * A flagged cell is similar to a covered one, in the way that mines are not triggered
 * when a cell is flagged, and it is impossible to lose through the action of flagging a cell.
 * However, flagging a cell implies that a player thinks there is a mine underneath, which causes the game to deduct an available mine from the display.
 *
 * In order to win the game, players must logically deduce where mines exist through
 * the use of the numbers given by uncovered cells.
 *
 * To win, all non-mine cells must be uncovered. At this stage, the timer is stopped.
 *
 * Commonly all mine cells are also flagged, but this is not required.
 *
 * When a player left-clicks on a cell, the game will uncover it.
 * If there are no mines adjacent to that particular cell,
 * the cell will display a blank tile or a "0",
 * and all adjacent cells will automatically be uncovered.
 *
 * Right-clicking on a cell will flag it, causing a flag to appear on it.
 * Note that flagged cells are still covered, and a player can click on it to uncover it, like a normal covered cell
 * (in many implementations, one must first unflag it, by right-clicking it again).
 */
class GameSpec extends Specification implements GameDataTest {


    void "test that can create a Game defining columns, rows and mines"() {

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

    void 'test that can trow an IndexOutOfBoundsException if uncover has an incorrect row number'() {

        given:
            aGame {
                with rows: 12 and columns: 10 and mines: 4
            }

        when:
            game.uncover(-1, 2)

        then:
            thrown IndexOutOfBoundsException

        when:
            game.uncover(1000, 2)

        then:
            thrown IndexOutOfBoundsException
    }

    void 'test that can trow an IndexOutOfBoundsException if uncover has an incorrect column number'() {

        given:
            aGame {
                with rows: 12 and columns: 10 and mines: 4
            }

        when:
            game.uncover(1, -1)

        then:
            thrown IndexOutOfBoundsException

        when:
            game.uncover(1, 20)

        then:
            thrown IndexOutOfBoundsException
    }


    void 'test that can uncover a tile without mine without changing game status'() {

        given:
            aGame {
                with rows: 12 and columns: 10
                withMine 1, 1
                withMine 2, 4
            }

        when:
            game.uncover 5, 5

        then:
            expect game, isStatus(PLAYING)

    }

    void 'test that can finish game if a tile without contains a mine'() {

        given:
            aGame {
                with rows: 4 and columns: 4
                withMine 1, 1
                withMine 2, 4
            }

        when:
            game.uncover 1, 1

        then:
            expect game, isStatus(GAME_OVER)

    }

    void 'test that can uncover a tile in row 1 and column 1 for a board of 4 columns and 4 rows with a mine in row 2 and column 2'() {

        given:
            aGame {
                with rows: 4 and columns: 4
                withMine 2, 2
            }

        when:
            game.uncover 0, 0

        then:
            expect game, isStatus(PLAYING)

        and:
            expect game, hasZeroNeighbouringMinesIn(0, 0)
    }

}
