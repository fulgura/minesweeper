package com.dmc.minesweeper

import com.dmc.minesweeper.security.User
import com.dmc.minesweeper.test.BearerToken
import com.dmc.minesweeper.test.GameResponse
import com.dmc.minesweeper.test.UserCredentials
import groovy.transform.NamedParam
import groovy.transform.NamedParams
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared

import static groovy.lang.Closure.DELEGATE_ONLY

trait GameControllerDataTest {

    @Shared
    @AutoCleanup
    HttpClient client

    String uriRequest
    String accessToken
    Map<String, ?> bodyContent
    HttpResponse response

    Game game
    Integer amountRows = 2
    Integer amountColumns = 2
    Integer amountMines = 0
    List<Tile> tiles = []


    void setup() {
        client = HttpClient.create(new URL("http://localhost:$serverPort"))

        UserCredentials credentials = new UserCredentials(username: 'diego', password: 'diego')
        HttpRequest request = HttpRequest.POST('/api/login', credentials)
        HttpResponse<BearerToken> accessTokenResponse = client.toBlocking().exchange(request, BearerToken)

        assert accessTokenResponse.status.code == 200
        assert accessTokenResponse.body().roles.find { it == 'ROLE_PLAYER' }
        accessToken = accessTokenResponse.body().accessToken
    }

    GameControllerDataTest aGame(
            @DelegatesTo(value = GameControllerDataTest,
                    strategy = DELEGATE_ONLY)
                    Closure closure
    ) {
        closure.delegate = this
        closure.resolveStrategy = DELEGATE_ONLY
        closure()

        buildData()
        return this
    }

    void buildData() {
        Game.withNewTransaction {
            game = new Game(
                    user: new User(username: 'test', password: 'test'),
                    board: new Board(amountColumns, amountRows, amountMines)
            ).save(failOnError: true, flush: true)

            tiles.each { Tile tile ->
                game.board.addToTiles(tile.save(failOnError: true))
            }
        }
    }

    GameControllerDataTest with(
            @NamedParams([
                    @NamedParam(value = 'rows', type = Integer.class),
                    @NamedParam(value = 'columns', type = Integer.class),
                    @NamedParam(value = 'mines', type = Integer.class)
            ]) Map<String, Integer> map
    ) {
        if (map.rows) this.amountRows = map.rows
        if (map.columns) this.amountColumns = map.columns
        if (map.mines) this.amountMines = map.mines
        return this
    }

    GameControllerDataTest and(
            @NamedParams([
                    @NamedParam(value = 'rows', type = Integer.class),
                    @NamedParam(value = 'columns', type = Integer.class),
                    @NamedParam(value = 'mines', type = Integer.class)
            ]) Map<String, Integer> map
    ) {
        if (map.rows) this.amountRows = map.rows
        if (map.columns) this.amountColumns = map.columns
        if (map.mines) this.amountMines = map.mines
        return this
    }

    void withMine(Integer row, Integer column) {
        tiles.add(Tile.withMine(row, column))
    }


    void get(String uri) {
        uriRequest = uri
        response = client.toBlocking().exchange(
                authenticatedRequestGET(),
                GameResponse
        )
    }

    void post(
            String uri,
            @DelegatesTo(
                    value = GameControllerDataTest,
                    strategy = DELEGATE_ONLY
            ) Closure configuration) {

        uriRequest = uri
        configuration.delegate = this
        configuration.resolveStrategy = DELEGATE_ONLY
        configuration()

        response = client.toBlocking()
                .exchange(
                        authenticatedRequestPOST(),
                        GameResponse
                )
    }

    void body(Map<String, ?> content) {
        bodyContent = content
    }


    HttpRequest authenticatedRequestPOST() {
        return HttpRequest.POST(uriRequest, bodyContent)
                .header('Authorization', "Bearer ${accessToken}")
    }

    HttpRequest authenticatedRequestGET() {
        return HttpRequest.GET(uriRequest)
                .header('Authorization', "Bearer ${accessToken}")
    }

}