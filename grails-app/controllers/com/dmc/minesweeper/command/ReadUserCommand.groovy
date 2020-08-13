package com.dmc.minesweeper.command

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ReadUserCommand implements Validateable {

    Long id
}