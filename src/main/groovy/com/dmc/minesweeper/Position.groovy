package com.dmc.minesweeper

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable

@Immutable
@EqualsAndHashCode(allProperties = true)
class Position {

    final Integer row
    final Integer col

    boolean isInRowsRange(Integer rows) {
        return new IntRange(true, 0, rows).contains(row)
    }

    boolean isInColumnsRange(Integer columns) {
        return new IntRange(true, 0, columns).contains(col)
    }

    boolean isInRange(Integer rows, Integer columns) {
        return isInRowsRange(rows) && isInColumnsRange(columns)
    }

    /**
     *
     * @param rows
     * @param columns
     * @return
     */
    List<Position> neighbouringPositions(Integer rows, Integer columns) {

        return [
                Pos(row - 1, col - 1), Pos(row - 1, col), Pos(row - 1, col + 1),
                Pos(row, col - 1), Pos(row, col + 1),
                Pos(row + 1, col - 1), Pos(row + 1, col), Pos(row + 1, col + 1)
        ].findAll { Position position ->
            position.isInRange(rows, columns)
        }
    }

    String toString() {
        return "Position(${row}, ${col})"
    }
    /**
     *
     * @param row
     * @param col
     * @return
     */
    static Position Pos(Integer row, Integer col) {
        return new Position(row, col)
    }

}
