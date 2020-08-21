package com.dmc.minesweeper.security

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class SecuredApiInterceptorSpec extends Specification implements InterceptorUnitTest<SecuredApiInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test secured interceptor matching"() {
        when:"A request matches the interceptor"
        withRequest(controller:"game")

        then:"The interceptor does match"
        interceptor.doesMatch()
    }
}
