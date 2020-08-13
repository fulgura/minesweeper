package com.dmc.minesweeper

import com.dmc.minesweeper.command.CreateGameCommand
import com.dmc.minesweeper.command.ReadGameCommand
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

import static org.springframework.http.HttpStatus.CREATED

@CompileStatic
@Transactional
class GameController  {

    static responseFormats = ['json', 'xml']

    GameService gameService

    /**
     * curl --location --request POST 'http://localhost:8080/api/game' \
     * --header 'Content-Type: application/json' \
     * --header 'Authorization: Bearer XYZ' \
     * --data-raw '{ //
     *      "rows": 10,
     *      "columns": 10,
     *      "mines": 6
     * // }'
     * @param command
     * @return
     */
    def create(CreateGameCommand command) {

        if (!command.validate()) {
            respond command.errors, view: 'create' // STATUS CODE 422
            return

        } else {
            Game game = gameService.createGame(params.user, command.rows, command.columns, command.mines)

            if (game.hasErrors()) {
                respond game.errors, view: 'create' // STATUS CODE 422
            } else {
                respond game, [status: CREATED, view: 'show']
            }
        }
    }

    /**
     * curl --location --request POST 'http://localhost:8080/api/game' \
     * --header 'Content-Type: application/json' \
     * --header 'Authorization: Bearer XXXX' \
     * --data-raw '{//
     *      "rows": 10,
     *      "columns": 10,
     *      "mines": 6
     *  // }'
     * @param gameId
     */

    def show(ReadGameCommand command) {
        //TODO: Check permission for resource by user request
        respond Game.get(command.id)
    }


//    /**
//     * Saves a resource
//     */
//    @Transactional
//    def save() {
//
//        if (handleReadOnly()) {
//            return
//        }
//        def instance = createResource()
//
//        instance.validate()
//        if (instance.hasErrors()) {
//            transactionStatus.setRollbackOnly()
//            respond instance.errors, view: 'create' // STATUS CODE 422
//            return
//        }
//
//        saveResource instance
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [classMessageArg, instance.id])
//                redirect instance
//            }
//            '*' {
//                response.addHeader(HttpHeaders.LOCATION,
//                        grailsLinkGenerator.link(resource: this.controllerName, action: 'show', id: instance.id, absolute: true,
//                                namespace: hasProperty('namespace') ? this.namespace : null))
//                respond instance, [status: CREATED, view: 'show']
//            }
//        }
//    }

}


