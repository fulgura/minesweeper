package com.dmc.minesweeper

import com.dmc.minesweeper.command.ReadUserCommand
import com.dmc.minesweeper.security.User
import grails.gorm.transactions.Transactional

@Transactional
class UserController {
    static responseFormats = ['json', 'xml']

    def list(ReadUserCommand command) {
        User whoami = params.user
        respond([
                userename: whoami.username,
                games    : Game.where { user.id == whoami.id }.list()
        ])
    }
}
