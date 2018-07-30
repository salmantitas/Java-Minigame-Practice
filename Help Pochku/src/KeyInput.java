import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

public class KeyInput extends KeyAdapter{

    private EngineKeyboard keyboard;
    private GameController gameController;

    public KeyInput(EngineKeyboard engineKeyboard, GameController gameController) {
        this.gameController = gameController;
        keyboard = engineKeyboard;
    }

    public void updatePressed() {

        /*************
         * Game Code *
         *************/

        if (Engine.currentState != GameState.Pause) {
            if (keyIsPressed(KeyEvent.VK_LEFT))
                gameController.movePlayer('l');

            if (keyIsPressed(KeyEvent.VK_RIGHT))
                gameController.movePlayer('r');
        }

            if (keyIsPressed(KeyEvent.VK_ESCAPE))
                System.exit(1);

            if (keyIsPressed(KeyEvent.VK_SPACE)) {
                if (Engine.currentState == GameState.Game)
                    Engine.setState(GameState.Pause);
                else if (Engine.currentState == GameState.Pause || Engine.currentState == GameState.GameOver)
                    Engine.setState(GameState.Game);
            }
    }

    public void updateReleased() {

        /*************
         * Game Code *
         *************/

        if (keyIsReleased(KeyEvent.VK_LEFT))
            gameController.stopMovePlayer('l');

        if (keyIsReleased(KeyEvent.VK_RIGHT))
            gameController.stopMovePlayer('r');

    }

    private boolean keyIsPressed(int key) {
        return keyboard.keyIsPressed(key);
    }

    private boolean keyIsReleased(int key) {
        return keyboard.keyIsReleased(key);
    }

    /******************
     * User functions *
     ******************/

}
