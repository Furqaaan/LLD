import strategy.HumanPlayerStrategy;
import strategy.PlayerStrategy;
import controller.TicTacToeGame;
import factory.SimplePlayerFactory;

public class Main {
    public static void main(String[] args) {
        PlayerStrategy playerXStrategy = new HumanPlayerStrategy("Player X");
        PlayerStrategy playerOStrategy = new HumanPlayerStrategy("Player O");

        TicTacToeGame game = new TicTacToeGame(3, playerXStrategy, playerOStrategy, new SimplePlayerFactory());

        game.play();
    }
}
