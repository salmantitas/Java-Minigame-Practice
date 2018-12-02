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

        if (keyIsPressed(KeyEvent.VK_G))
            gameController.worldspace.buildStateGrass();

        if (keyIsPressed(KeyEvent.VK_F))
            gameController.worldspace.buildStateFloor();

        if (keyIsPressed(KeyEvent.VK_D))
            gameController.worldspace.demolish();

        if (keyIsPressed(KeyEvent.VK_W))
            gameController.worldspace.buildWall();

    }

    public void updateReleased() {
        /*************
         * Game Code *
         *************/
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
