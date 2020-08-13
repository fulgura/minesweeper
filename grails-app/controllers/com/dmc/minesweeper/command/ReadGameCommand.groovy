package com.dmc.minesweeper.command

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ReadGameCommand implements Validateable {

    String id
}