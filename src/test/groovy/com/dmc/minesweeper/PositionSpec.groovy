package com.dmc.minesweeper

import spock.lang.Specification
import spock.lang.Unroll

import static com.dmc.minesweeper.Position.Pos

class PositionSpec extends Specification {


    void 'test that can create an immutable position'() {

        given:
            Position position = Pos(1, 3)

        when:
            position.row = 4

        then:
            ReadOnlyPropertyException exception = thrown(ReadOnlyPropertyException)
            exception.message == 'Cannot set readonly property: row for class: com.dmc.minesweeper.Position'
    }


    def 'test Position equality'() {

        when:
            List<Position> positions = [Pos(2, 6)]

        then:
            positions.contains(Pos(2, 6))
    }

    def 'test Position hascode'() {

        when:
            Map<Position, Boolean> positionsMap = [(Pos(1, 3)): true]

        then:
            positionsMap.containsKey(Pos(1, 3))
    }

    @Unroll
    def "test that row of #position is in range=#isInRange, for range: 0..#rows"() {

        expect:
            position.isInRowsRange(rows) == isInRange

        where:
            position  | rows || isInRange
            Pos(2, 2) | -1   || false
            Pos(2, 2) | 0    || false
            Pos(2, 2) | 1    || false
            Pos(2, 2) | 2    || true
            Pos(2, 2) | 3    || true
    }

    @Unroll
    def "test that column of #position is in range=#isInRange, for range: 0..#columns"() {

        expect:
            position.isInColumnsRange(columns) == isInRange

        where:
            position  | columns || isInRange
            Pos(2, 2) | -1      || false
            Pos(2, 2) | 0       || false
            Pos(2, 2) | 1       || false
            Pos(2, 2) | 2       || true
            Pos(2, 2) | 3       || true
    }

    @Unroll
    void 'test that #position is in range=#isInRange for rows: #rows and columns: #columns'() {

        expect:
            position.isInRange(rows, columns) == isInRange

        where:
            position  | rows | columns || isInRange
            Pos(2, 2) | -1   | -1      || false
            Pos(2, 2) | 0    | -1      || false
            Pos(2, 2) | -1   | 0       || false
            Pos(2, 2) | 1    | -1      || false
            Pos(2, 2) | 2    | -1      || false
            Pos(2, 2) | 3    | -1      || false
            Pos(2, 2) | 0    | 1       || false
            Pos(2, 2) | 1    | 1       || false
            Pos(2, 2) | 1    | 2       || false
            Pos(2, 2) | 2    | 2       || true
    }

    void 'test that can calculate neighbouringPositions for a tile in a range of rows and columns'() {

        given:
            Position position = Pos(1, 1)

        when:
            List<Position> neighbours = position.neighbouringPositions(2, 2)

        then:
            neighbours.contains(Pos(0, 0))
            neighbours.contains(Pos(0, 1))
            neighbours.contains(Pos(0, 2))
            neighbours.contains(Pos(1, 0))
            neighbours.contains(Pos(1, 2))
            neighbours.contains(Pos(2, 0))
            neighbours.contains(Pos(2, 1))
            neighbours.contains(Pos(2, 2))
    }
}
