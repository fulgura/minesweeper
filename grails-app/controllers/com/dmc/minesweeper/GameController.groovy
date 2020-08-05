package com.dmc.minesweeper

import com.dmc.minesweeper.command.NewGameCommand
import com.dmc.minesweeper.security.SecuredController
import com.dmc.minesweeper.security.User
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

import static org.springframework.http.HttpStatus.CREATED

@CompileStatic
@Transactional
class GameController implements SecuredController {

    static responseFormats = ['json', 'xml']

    GameService gameService

    /**
     * POST /api/game
     * body { //
     *     rows: 10,
     *     columns: 12,
     *     mines: 20
     * // } //
     * * @param commandObject
     */
    def newGame(NewGameCommand command) {

        if (!command.validate()) {
            respond command.errors, view: 'create' // STATUS CODE 422
            return

        } else {

            Game game = gameService.createGame((User) currentUser(), command.rows, command.columns, command.mines)

            if (game.hasErrors()) {
                respond game.errors, view: 'create' // STATUS CODE 422
            } else {
                respond game, [status: CREATED, view: 'show']
            }
        }
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


