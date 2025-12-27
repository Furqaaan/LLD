package chess;

import chess.player.Player;
import chess.game.ChessGame;
import chess.observer.ConsoleGameEventListener;
import chess.player.HumanPlayerStrategy;
import chess.player.AiPlayerStrategy;

class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player1", true, new HumanPlayerStrategy());
        Player player2 = new Player("Player2", false, new AiPlayerStrategy());

        ChessGame chessGame = new ChessGame(player1, player2);
        chessGame.setObserver(new ConsoleGameEventListener(chessGame.getBoard()));
        chessGame.start();
    }
}