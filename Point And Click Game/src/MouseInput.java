import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private UIController uiController;
    private GameController gameController;

    private int mxP, myP; // mouse x and y position when pressed
    private int mxR, myR; // mouse x and y position when released

    public MouseInput(UIController uiController, GameController gameController) {
        this.uiController = uiController;
        this.gameController = gameController;
    }

    public void mousePressed(MouseEvent e) {
        mxP = e.getX();
        myP = e.getY();

        System.out.println("Mouse Pressed at (" + mxP + ", " + myP + ")");

        uiController.checkOverlap(mxP, myP);
    }

    public void mouseReleased(MouseEvent e) {
        mxR = e.getX();
        myR = e.getY();

        System.out.println("Mouse Released at (" + mxR + ", " + myR + ")");

        if (mxP == mxR && myP == myR && Game.gameState == Game.STATE.Game)
            gameController.checkOverlap(mxP, myR);

    }
}
