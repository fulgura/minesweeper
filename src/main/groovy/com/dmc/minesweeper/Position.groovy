package com.dmc.minesweeper

import groovy.transform.Immutable

@Immutable
class Position {

    final Integer row
    final Integer col

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Position position = (Position) o

        if (col != position.col) return false
        if (row != position.row) return false

        return true
    }

    int hashCode() {
        int result
        result = (row != null ? row.hashCode() : 0)
        result = 31 * result + (col != null ? col.hashCode() : 0)
        return result
    }

    boolean isInRowsRange(Integer rows) {
        return row in 0..(rows)
    }

    boolean isInColumnsRange(Integer columns) {
        return col in 0..(columns)
    }

    boolean isInRange(Integer rows, Integer columns) {
        return isInRowsRange(rows) && isInColumnsRange(columns)
    }

    private List<Position> neighbourPositions(Integer rows, Integer columns) {

        return [
                [row-1, col-1],[row-1, col],[row-1, col+1],
                [row,   col-1],             [row, col+1],
                [row+1, col-1],[row+1, col],[row+1, col+1]
        ].findAll {Integer rowPos, Integer colPos ->
            Position.For(rowPos, colPos).isInRange(rows, columns)
        }
    }

    static Position For(Integer row, Integer col) {
        return new Position(row, col)
    }
}
