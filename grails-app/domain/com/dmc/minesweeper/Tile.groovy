package com.dmc.minesweeper

import grails.compiler.GrailsCompileStatic

import static com.dmc.minesweeper.Tile.Content.MINE
import static com.dmc.minesweeper.Tile.Content.ZERO_NEIGHBOURING_MINES


@GrailsCompileStatic
class Tile implements Entity {

    /**
     * -1 means a mine in this tile
     * from 0 to 9 represents the total amount of tile with mines
     * around.
     */
    Content content
    Status status = Status.UNCOVERED
    Position position

    static belongsTo = Board

    static constraints = {
        position nullable: false
        status nullable: false
        content nullable: false
    }

    static embedded = ['position']

    boolean isMined() {
        return content == MINE
    }

    boolean hasZeroNeighbouringBombs() {
        return content == ZERO_NEIGHBOURING_MINES
    }

    Integer numberOfMines() {
        return content.number
    }

    static Tile withMine(Integer row, Integer column) {
        return withMine(Position.Pos(row, column))
    }

    static Tile withoutMine(Integer row, Integer column) {
        return withoutMine(Position.Pos(row, column))
    }

    static Tile withMine(Position position) {
        return new Tile(position: position, content: MINE)
    }

    static Tile withoutMine(Position position) {
        return new Tile(position: position, content: ZERO_NEIGHBOURING_MINES)
    }

    static Tile withMinesInNeighbours(Position position, Integer numberOfMines) {
        return new Tile(position: position, content: Content.For(numberOfMines))
    }

    enum Content {
        MINE(-1),
        ZERO_NEIGHBOURING_MINES(0),
        ONE_NEIGHBOURING_MINES(1),
        TWO_NEIGHBOURING_MINES(2),
        THREE_NEIGHBOURING_MINES(3),
        FOUR_NEIGHBOURING_MINES(4),
        FIVE_NEIGHBOURING_MINES(5),
        SIX_NEIGHBOURING_MINES(6),
        SEVEN_NEIGHBOURING_MINES(7),
        EIGHT_NEIGHBOURING_MINES(8),
        NINE_NEIGHBOURING_MINES(9)

        final Integer number

        Content(Integer number) {
            this.number = number
        }

        static Content For(Integer number) {
            return values().find { it.number == number }
        }
    }

    enum Status {
        UNCOVERED, COVERED, FLAGGED
    }
}
