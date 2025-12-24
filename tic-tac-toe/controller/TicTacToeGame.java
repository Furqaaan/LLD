package controller;

import models.Board;
import models.Player;
import state.GameContext;
import models.Symbol;
import state.XTurnState;
import strategy.PlayerStrategy;
import models.Position;
import state.GameState;
import state.XWonState;
import state.OWonState;
import factory.PlayerFactory;

public class TicTacToeGame implements BoardGames {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameContext gameContext;

    public TicTacToeGame(int boardSize, PlayerStrategy strategyX, PlayerStrategy strategyO,
            PlayerFactory playerFactory) {
        this.board = new Board(boardSize, boardSize);
        this.playerX = playerFactory.createPlayer(Symbol.X, strategyX);
        this.playerO = playerFactory.createPlayer(Symbol.O, strategyO);

        this.currentPlayer = playerX;

        this.gameContext = new GameContext(new XTurnState());
    }

    @Override
    public void play() {
        do {
            board.printBoard();

            Position move = currentPlayer.getPlayerStrategy().makeMove(board);
            board.makeMove(move, currentPlayer.getSymbol());

            board.checkGameState(gameContext, currentPlayer);

            switchPlayer();
        } while (!gameContext.getState().isGameOver());

        announceResult();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    private void announceResult() {
        GameState state = gameContext.getState();

        if (state instanceof XWonState) {
            System.out.println("Player X wins!");
        } else if (state instanceof OWonState) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}