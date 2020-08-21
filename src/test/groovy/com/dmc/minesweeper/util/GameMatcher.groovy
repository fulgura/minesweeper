package com.dmc.minesweeper.util

import com.dmc.minesweeper.Game
import com.dmc.minesweeper.Position
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class GameMatcher {

    /**
     * Matcher for the amount of mines in a instance of {@link Game}
     *
     * @param mines
     * @return
     */
    static TypeSafeMatcher hasMines(Integer mines) {

        return new TypeSafeMatcher<Game>() {

            @Override
            protected boolean matchesSafely(Game game) {
                return game.board.getTilesWithMines().size() == mines
            }

            @Override
            void describeTo(Description description) {
                description.appendText("show should contain ${mines} mines")
            }

            @Override
            protected void describeMismatchSafely(Game game, Description mismatchDescription) {
                mismatchDescription.appendValue("show has ${game.board.getTilesWithMines().size()}")
            }
        }
    }

    /**
     * Matcher for the amount of columns in a instance of {@link Game}
     *
     * @param mines
     * @return
     */
    static TypeSafeMatcher hasColumns(Integer columns) {

        return new TypeSafeMatcher<Game>() {

            @Override
            protected boolean matchesSafely(Game game) {
                return game.board.columns == columns
            }

            @Override
            void describeTo(Description description) {
                description.appendText("show should contain ${columns} columns")
            }

            @Override
            protected void describeMismatchSafely(Game game, Description mismatchDescription) {
                mismatchDescription.appendValue("show has ${game.board.columns}")
            }
        }
    }

    /**
     * Matcher for the amount of columns in a instance of {@link Game}
     *
     * @param mines
     * @return
     */
    static TypeSafeMatcher hasRows(Integer rows) {

        return new TypeSafeMatcher<Game>() {

            @Override
            protected boolean matchesSafely(Game game) {
                return game.board.rows == rows
            }

            @Override
            void describeTo(Description description) {
                description.appendText("show should contain ${rows} rows")
            }

            @Override
            protected void describeMismatchSafely(Game game, Description mismatchDescription) {
                mismatchDescription.appendValue("show has ${game.board.rows}")
            }
        }
    }

    /**
     * Matcher for the status a instance of {@link Game}
     * using {@link Game#status}
     *
     * @param mines
     * @return
     */
    static TypeSafeMatcher isStatus(Game.GameStatus status) {

        return new TypeSafeMatcher<Game>() {

            @Override
            protected boolean matchesSafely(Game game) {
                return game.status == status
            }

            @Override
            void describeTo(Description description) {
                description.appendText("game should be ${status} status")
            }

            @Override
            protected void describeMismatchSafely(Game game, Description mismatchDescription) {
                mismatchDescription.appendValue("was ${game.status}")
            }
        }
    }

    /**
     * Matcher an instance of {@link Game} has a tile
     * in row and column arguments without neighbouring bombs
     *
     * @param row
     * @param column
     * @return an instance of {@link TypeSafeMatcher}
     */
    static TypeSafeMatcher hasZeroNeighbouringMinesIn(Integer row, Integer column) {

        return new TypeSafeMatcher<Game>() {

            @Override
            protected boolean matchesSafely(Game game) {
                return game.board.lookupTile(Position.For(row, column)).hasZeroNeighbouringBombs()
            }

            @Override
            void describeTo(Description description) {
                description.appendText("game should have zero neighbouring Bombs")
            }

            @Override
            protected void describeMismatchSafely(Game game, Description mismatchDescription) {
                mismatchDescription.appendValue("was ${game.board.lookupTile(Position.For(row, column)).numberOfMines()}")
            }
        }
    }

}
