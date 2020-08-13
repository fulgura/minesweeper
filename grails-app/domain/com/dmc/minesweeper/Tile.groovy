package com.dmc.minesweeper

import grails.compiler.GrailsCompileStatic


@GrailsCompileStatic
class Tile implements Entity {

    Integer row
    Integer col
    /**
     * -1 means a mine in this tile
     * from 0 to 9 represents the total amount of tile with mines
     * around.
     */
    Integer value

    static belongsTo = Board

    static constraints = {
        row nullable: false
        col nullable: false
        value nullable: false, range: -1..8
    }

    boolean hasMine() {
        return value == -1
    }

    static Tile withMine(Integer row, Integer column) {
        return new Tile(row: row, col: column, value: -1)
    }

    static Tile withoutMine(Integer row, Integer column) {
        return new Tile(row: row, col: column, value: 0)
    }
}
