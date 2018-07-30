import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
            if (keyIsPressed(KeyEvent.VK_LEFT ) || keyIsPressed(KeyEvent.VK_A))
                gameController.movePlayer('l');

            if (keyIsPressed(KeyEvent.VK_RIGHT) || keyIsPressed(KeyEvent.VK_D))
                gameController.movePlayer('r');

            if (keyIsPressed(KeyEvent.VK_UP) || keyIsPressed(KeyEvent.VK_W))
                gameController.movePlayer('u');

            if (keyIsPressed(KeyEvent.VK_DOWN) || keyIsPressed(KeyEvent.VK_S))
                gameController.movePlayer('d');

            if (keyIsPressed(KeyEvent.VK_SPACE) || keyIsPressed(KeyEvent.VK_NUMPAD0))
                gameController.shootPlayer();
        }

        if (keyIsPressed(KeyEvent.VK_ESCAPE)) {
            if (Engine.currentState == GameState.Menu) {
                System.exit(1);
            }
            else {
                if (Engine.currentState == GameState.Game)
                    Engine.setState(GameState.Pause);
                else if (Engine.currentState == GameState.Pause)
                    Engine.setState(GameState.Game);
            }
        }
    }

    public void updateReleased() {

        /*************
         * Game Code *
         *************/

        if (keyIsReleased(KeyEvent.VK_LEFT) || keyIsReleased(KeyEvent.VK_A))
            gameController.stopMovePlayer('l');

        if (keyIsReleased(KeyEvent.VK_RIGHT)|| keyIsReleased(KeyEvent.VK_D))
            gameController.stopMovePlayer('r');

        if (keyIsReleased(KeyEvent.VK_UP) || keyIsReleased(KeyEvent.VK_W))
            gameController.stopMovePlayer('u');

        if (keyIsReleased(KeyEvent.VK_DOWN) || keyIsReleased(KeyEvent.VK_S))
            gameController.stopMovePlayer('d');

        if (keyIsReleased(KeyEvent.VK_SPACE) || keyIsReleased(KeyEvent.VK_NUMPAD0))
            gameController.stopShootPlayer();
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
