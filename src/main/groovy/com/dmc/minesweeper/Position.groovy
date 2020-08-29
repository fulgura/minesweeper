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

        return (-1..1).collect { Integer neighbourRow ->
            (-1..1).collect { Integer neighbourColumn ->
                Pos(row + neighbourRow, col - neighbourColumn)
            }
        }.flatten().findAll { Position position ->
            position != this && position.isInRange(rows, columns)
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
