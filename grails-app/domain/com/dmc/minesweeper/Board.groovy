package com.dmc.minesweeper

import com.dmc.minesweeper.util.GameUtil
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Board implements Entity {

    Integer columns
    Integer rows
    List<Mine> mines

    List<Tile> tiles

    static belongsTo = Game
    static hasMany = [tiles: Tile, mines: Mine]

    Board(Integer columns, Integer rows, Integer mines) {
        this.columns = columns
        this.rows = rows
        this.mines = mines
        this.mines.times {
            addToTiles(GameUtil.randomTile(this.rows, this.columns).save())
        }
    }
    static constraints = {
        columns range: 0..100
        rows range: 0..100
        mines range: 0..50
    }

    List<Tile> getTilesWithMines() {
        return tiles.findAll { it.hasMine() }
    }

    boolean hasTile(Integer row, Integer column) {
        validate row, column
        return tiles.find { it.row == row && it.col == column }
    }

    Tile getTile(Integer row, Integer column) {
        validate row, column
        return tiles.find { it.row == row && it.col == column }
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
