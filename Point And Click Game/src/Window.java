import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    // Creates a window for the game with the given width and height. The string title is used as the window's title.
    // Locks the screen at the specified dimension so that it cannot be resized. The game is added to the screen and
    // is started.
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        Dimension dimension = new Dimension(width, height);

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.run();

    }

}
