package com.dmc.minesweeper.util

import com.dmc.minesweeper.Tile

class GameUtil {

    static Random random = new Random()

    static Tile randomTile(Integer rows, Integer columns) {
        return Tile.withMine(random.nextInt(rows), random.nextInt(columns))
    }

}
