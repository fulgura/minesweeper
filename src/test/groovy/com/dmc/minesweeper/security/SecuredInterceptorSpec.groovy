package com.dmc.minesweeper.security

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class SecuredInterceptorSpec extends Specification implements InterceptorUnitTest<SecuredInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test secured interceptor matching"() {
        when:"A request matches the interceptor"
        withRequest(controller:"secured")

        then:"The interceptor does match"
        interceptor.doesMatch()
    }
}
