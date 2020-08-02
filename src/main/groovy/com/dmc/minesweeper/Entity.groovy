package com.dmc.minesweeper

trait Entity implements Serializable{

    private static final long serialVersionUID = 1

    String id

    Date dateCreated
    Date lastUpdated

    static mapping = {
        id generator: 'uuid2'
    }
}