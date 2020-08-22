package com.dmc.minesweeper

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

import static com.dmc.minesweeper.Tile.Content.MINE
import static com.dmc.minesweeper.Tile.Content.ONE_NEIGHBOURING_MINES
import static com.dmc.minesweeper.Tile.Content.TWO_NEIGHBOURING_MINES
import static com.dmc.minesweeper.Tile.Content.ZERO_NEIGHBOURING_MINES
import static com.dmc.minesweeper.Tile.Status.COVERED
import static com.dmc.minesweeper.Tile.Status.FLAGGED
import static com.dmc.minesweeper.Tile.Status.UNCOVERED

class TileSpec extends Specification implements DomainUnitTest<Tile> {

    @Unroll('Tile.validate() with content: #aContent should have returned #expected with errorCode: #expectedErrorCode')
    void 'test tile content field validation'() {

        when:
            domain.content = aContent

        then:
            expected == domain.validate(['content'])
            expectedErrorCode == domain.errors['content']?.code

        where:
            aContent                || expected | expectedErrorCode
            null                    || false    | 'nullable'
            MINE                    || true     | null
            ZERO_NEIGHBOURING_MINES || true     | null
            ONE_NEIGHBOURING_MINES  || true     | null
            TWO_NEIGHBOURING_MINES  || true     | null
    }

    @Unroll('Tile.validate() with status: #aStatus should have returned #expected with errorCode: #expectedErrorCode')
    void 'test tile status field validation'() {

        when:
            domain.status = aStatus

        then:
            expected == domain.validate(['status'])
            expectedErrorCode == domain.errors['status']?.code

        where:
            aStatus   || expected | expectedErrorCode
            null      || false    | 'nullable'
            UNCOVERED || true     | null
            COVERED   || true     | null
            FLAGGED   || true     | null
    }

    @Unroll('Tile.validate() with position: #aPosition should have returned #expected with errorCode: #expectedErrorCode')
    void 'test tile position field validation'() {

        when:
            domain.position = aPosition

        then:
            expected == domain.validate(['position'])
            expectedErrorCode == domain.errors['position']?.code

        where:
            aPosition          || expected | expectedErrorCode
            null               || false    | 'nullable'
            Position.For(1, 2) || true     | null
            Position.For(0, 0) || true     | null
    }


    void 'test can create a tile with a mine'() {

        when:
            Tile tile = Tile.withMine(Position.For(1, 3))

        then:
            tile.isMined()

        and:
            tile.status == Tile.Status.UNCOVERED
    }
}
