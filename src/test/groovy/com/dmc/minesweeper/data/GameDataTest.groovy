package com.dmc.minesweeper.data

import com.dmc.minesweeper.Board
import com.dmc.minesweeper.Game
import com.dmc.minesweeper.Tile
import com.dmc.minesweeper.security.User
import grails.testing.gorm.DataTest
import groovy.transform.NamedParam
import groovy.transform.NamedParams

import static groovy.lang.Closure.DELEGATE_ONLY

trait GameDataTest extends DataTest {

    Game game
    Integer amountRows = 2
    Integer amountColumns = 2
    Integer amountMines = 0
    List<Tile> tiles = []

    GameDataTest aGame(
            @DelegatesTo(value = GameDataTest, strategy = DELEGATE_ONLY)
                    Closure closure
    ) {
        mockDomains Game, Board, Tile

        closure.delegate = this
        closure.resolveStrategy = DELEGATE_ONLY
        closure()

        buildData()
        return this
    }

    private void buildData() {
        game = new Game(
                user: new User(username: 'test', password: 'test'),
                board: new Board(amountColumns, amountRows, amountMines)
        ).save(failOnError: true, flush: true)

        tiles.each { Tile tile ->
            game.board.addToTiles(tile.save(failOnError: true))
        }
    }

    GameDataTest with(
            @NamedParams([
                    @NamedParam(value = 'rows', type = Integer.class),
                    @NamedParam(value = 'columns', type = Integer.class),
                    @NamedParam(value = 'mines', type = Integer.class)
            ]) Map<String, Integer> map
    ) {
        if (map.rows) this.amountRows = map.rows
        if (map.columns) this.amountColumns = map.columns
        if (map.columns) this.amountMines = map.columns
        return this
    }

    GameDataTest and(
            @NamedParams([
                    @NamedParam(value = 'rows', type = Integer.class),
                    @NamedParam(value = 'columns', type = Integer.class),
                    @NamedParam(value = 'mines', type = Integer.class)
            ]) Map<String, Integer> map
    ) {
        if (map.rows) this.amountRows = map.rows
        if (map.columns) this.amountColumns = map.columns
        if (map.columns) this.amountMines = map.columns
        return this
    }

    GameDataTest withRows(Integer rows) {
        amountRows = rows
        return this
    }

    GameDataTest withColumns(Integer columns) {
        amountColumns = columns
        return this
    }

    GameDataTest andColumns(Integer columns) {
        amountColumns = columns
        return this
    }

    GameDataTest withMines(Integer mines) {
        amountMines = mines
        return this
    }

    GameDataTest withMine(Integer row, Integer column) {
        tiles.add(Tile.withMine(row, column))
        return this
    }


}