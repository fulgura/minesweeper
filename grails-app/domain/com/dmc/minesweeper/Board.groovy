package com.dmc.minesweeper

import com.dmc.minesweeper.util.GameUtil
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Board implements Entity {

    Integer columns
    Integer rows
    List<Tile> tiles

    static belongsTo = Game
    static hasMany = [tiles: Tile]

    Board(Integer columns, Integer rows, Integer mines) {
        this.columns = columns
        this.rows = rows
        mines.times {
            addToTiles(GameUtil.randomTile(this.rows, this.columns).save())
        }
    }
    static constraints = {
        columns range: 0..100
        rows range: 0..100
    }

    List<Tile> getTilesWithMines() {
        return tiles.findAll { it.isMined() }
    }

    boolean hasTile(Position position) {
        validate position
        return tiles.find { it.position == position }
    }

    Tile lookupTile(Position position) {
        validate position
        return tiles.find { it.position == position }
    }

    Tile uncover(Position position) {
        Tile tile = lookupTile position
        if (!tile) {
            tile = buildTileFor position
        }

        return tile
    }

    private Tile buildTileFor(Position position) {

        Integer numberOfMinesInNeighbours = 0

        for (Position neighbourPosition in position.neighbourPositions(rows, columns)) {
            Tile neighbour = lookupTile(neighbourPosition)
            if (neighbour?.isMined()) {
                numberOfMinesInNeighbours++
            }
        }

        Tile newTile = Tile.withMinesInNeighbours(position, numberOfMinesInNeighbours)
        addToTiles(newTile)

        return newTile
    }

    /**
     * Validates in an Position is valid for the current board configuration.
     *
     * @param position
     */
    void validate(Position position) {

        //TODO: CHeck if this is not a responsability of Position class
        if (!position.isInRowsRange(rows)) {
            throw new IndexOutOfBoundsException(position.row.toString())
        }

        if (!position.isInColumnsRange(columns)) {
            throw new IndexOutOfBoundsException(position.col.toString())
        }
    }
}
