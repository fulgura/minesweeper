package com.dmc.minesweeper.security

import grails.plugin.springsecurity.SpringSecurityService

/**
 * <p>Interceptor used for manipulating request params
 * adding current instance of {@link User}.</p>
 * <p> Every request for the API layer is going to be altered
 * adding current user to the request.params fields.</p>
 * <p>After that, every API controller could access to the current user
 * using request params.</p>
 */
class SecuredApiInterceptor {

    SpringSecurityService springSecurityService

    SecuredApiInterceptor() {
        matchAll()
                .excludes(controller: 'login')
    }


    boolean before() {
        params.user = springSecurityService.currentUser
        return params.user != null
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
