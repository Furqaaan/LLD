package tic_tac_toe;

import tic_tac_toe.strategy.HumanPlayerStrategy;
import tic_tac_toe.strategy.PlayerStrategy;
import tic_tac_toe.controller.TicTacToeGame;
import tic_tac_toe.factory.SimplePlayerFactory;

public class Main {
    public static void main(String[] args) {
        PlayerStrategy playerXStrategy = new HumanPlayerStrategy("Player X");
        PlayerStrategy playerOStrategy = new HumanPlayerStrategy("Player O");

        TicTacToeGame game = new TicTacToeGame(3, playerXStrategy, playerOStrategy, new SimplePlayerFactory());

        game.play();
    }
}

