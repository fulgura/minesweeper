package com.dmc.minesweeper.security

import grails.plugin.springsecurity.SpringSecurityService

trait SecuredController {

    SpringSecurityService springSecurityService

    User currentUser() {
        return (User) springSecurityService.currentUser
    }
}