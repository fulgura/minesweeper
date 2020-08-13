package com.dmc.minesweeper.command

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class CreateGameCommand implements Validateable {

    Integer rows
    Integer columns
    Integer mines

    static constraints = {
        columns range: 0..100
        rows range: 0..100
        mines range: 0..50
    }

}