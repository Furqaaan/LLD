package tic_tac_toe.models;

import tic_tac_toe.state.GameContext;
import java.util.List;
import tic_tac_toe.observer.GameEventListener;
import java.util.ArrayList;
import tic_tac_toe.state.GameState;
import tic_tac_toe.state.XWonState;
import tic_tac_toe.state.OWonState;
import tic_tac_toe.state.InProgressState;
import tic_tac_toe.state.DrawState;

public class Board {
    private int rows;
    private int columns;
    private Symbol[][] grid;
    private List<GameEventListener> listeners;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Symbol[rows][columns];
        this.listeners = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public boolean isValidMove(Position position) {
        return position.getX() >= 0 && position.getX() < rows && position.getY() >= 0 && position.getY() < columns
                && grid[position.getX()][position.getY()] == Symbol.EMPTY;
    }

    public void makeMove(Position position, Symbol symbol) {
        grid[position.getX()][position.getY()] = symbol;
        notifyMoveMade(position, symbol);
    }

    public void checkGameState(GameContext context, Player player) {
        for (int i = 0; i < rows; i++) {
            if (grid[i][0] != Symbol.EMPTY && isWinningLine(grid[i])) {
                GameState newState = grid[i][0] == Symbol.X ? new XWonState() : new XWonState();
                context.setState(newState);
                notifyGameStateChanged(newState);
                return;
            }
        }

        for (int i = 0; i < columns; i++) {
            Symbol[] column = new Symbol[rows];
            for (int j = 0; j < rows; j++) {
                column[j] = grid[j][i];
            }
            if (column[0] != Symbol.EMPTY && isWinningLine(column)) {
                GameState newState = column[0] == Symbol.X ? new XWonState() : new OWonState();
                context.setState(newState);
                notifyGameStateChanged(newState);
                return;
            }
        }

        Symbol[] diagonal1 = new Symbol[Math.min(rows, columns)];
        Symbol[] diagonal2 = new Symbol[Math.min(rows, columns)];
        for (int i = 0; i < Math.min(rows, columns); i++) {
            diagonal1[i] = grid[i][i];
            diagonal2[i] = grid[i][columns - 1 - i];
        }

        if (diagonal1[0] != Symbol.EMPTY && isWinningLine(diagonal1)) {
            GameState newState = diagonal1[0] == Symbol.X ? new XWonState() : new OWonState();
            context.setState(newState);
            notifyGameStateChanged(newState);
            return;
        }

        if (diagonal2[0] != Symbol.EMPTY && isWinningLine(diagonal2)) {
            GameState newState = diagonal2[0] == Symbol.X ? new XWonState() : new OWonState();
            context.setState(newState);
            notifyGameStateChanged(newState);
            return;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (grid[row][col] == Symbol.EMPTY) {
                    context.setState(new InProgressState());
                    return;
                }
            }
        }

        context.setState(new DrawState());
        notifyGameStateChanged(new DrawState());
        return;
    }

    private boolean isWinningLine(Symbol[] line) {
        Symbol first = line[0];

        if (first == Symbol.EMPTY) {
            return false;
        }

        for (Symbol s : line) {
            if (s != first) {
                return false;
            }
        }

        return true;
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Symbol symbol = grid[i][j];

                switch (symbol) {
                    case X:
                        System.out.print(" X ");
                        break;
                    case O:
                        System.out.print(" O ");
                        break;
                    case EMPTY:
                    default:
                        System.out.print(" . ");
                }

                if (j < columns - 1) {
                    System.out.print("|");
                }
            }

            System.out.println();

            if (i < rows - 1) {
                System.out.println("---+---+---");
            }
        }

        System.out.println();
    }

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void notifyMoveMade(Position position, Symbol symbol) {
        for (GameEventListener listener : listeners) {
            listener.onMoveMade(position, symbol);
        }
    }

    public void notifyGameStateChanged(GameState state) {
        for (GameEventListener listener : listeners) {
            listener.onGameStateChanged(state);
        }
    }
}

