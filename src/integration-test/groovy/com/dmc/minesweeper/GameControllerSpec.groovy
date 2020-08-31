package com.dmc.minesweeper

import com.dmc.minesweeper.test.GameResponse
import grails.testing.mixin.integration.Integration
import io.micronaut.http.HttpStatus
import spock.lang.Specification

import static com.dmc.minesweeper.Game.GameStatus.PLAYING

@SuppressWarnings(['MethodName', 'DuplicateNumberLiteral', 'Instanceof'])
@Integration
class GameControllerSpec extends Specification implements GameControllerDataTest {

    void 'test that can create a game for an user'() {

        when:
            post '/api/game', {
                body rows: 4, columns: 4, mines: 3
            }

        then:
            response.status == HttpStatus.CREATED

        and:
            ((GameResponse) response.body()).status == PLAYING
    }

    void 'test that can read a Game created by its id'() {

        given:
            aGame {
                with rows: 4 and columns: 4
                withMine 1, 1
                withMine 2, 4
            }

        when:
            get "/api/game/$game.id"

        then:
            response.status == HttpStatus.OK

        and:
            ((GameResponse) response.body()).status == PLAYING
    }


}
