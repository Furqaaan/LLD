package tic_tac_toe.models;

import tic_tac_toe.strategy.PlayerStrategy;

public class Player {
    Symbol symbol;
    PlayerStrategy playerStrategy;
    
    public Player(Symbol symbol, PlayerStrategy playerStrategy) {
        this.symbol = symbol;
        this.playerStrategy = playerStrategy;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public PlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }
}

