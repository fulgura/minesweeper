package com.dmc.minesweeper

import com.dmc.minesweeper.util.GameUtil
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Board implements Entity {

    Integer columns
    Integer rows
    Integer mines

    List<Tile> tiles

    static belongsTo = Game
    static hasMany = [tiles: Tile]

    static constraints = {
        columns range: 0..100
        rows range: 0..100
        mines range: 0..50
    }

    List<Tile> getTilesWithMines() {
        return tiles.findAll { it.hasMine() }
    }

    def beforeInsert() {
        if (mines) {
            mines.times {
                addToTiles(GameUtil.randomTile(rows, columns).save(failOnError: true))
            }
        }
    }

    boolean hasTile(Integer row, Integer column) {
        validate row, column
        return tiles.find { it.row == row && it.column == column }
    }

    Tile getTile(Integer row, Integer column) {
        validate row, column
        return tiles.find { it.row == row && it.column == column }
    }

    /**
     * Validates if row and column param
     * are in range for board configuration
     * @param row
     * @param column
     */
    void validate(Integer row, Integer column) {

        if (!(row in 0..(rows))) {
            throw new IndexOutOfBoundsException(row)
        }

        if (!(column in 0..(columns))) {
            throw new IndexOutOfBoundsException(row)
        }
    }
}
