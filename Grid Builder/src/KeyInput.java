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
