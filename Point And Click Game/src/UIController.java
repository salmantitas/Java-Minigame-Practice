import java.awt.*;
import java.util.LinkedList;

public class UIController {

    private LinkedList<Button> buttons = new LinkedList<>();

//    String help = "Click on blocks to destroy them. The maximum score you can earn from each block reduces over time. You will have 1 minute.";
    public UIController() {
        addButton(new Button(Game.buttonSize, Game.HEIGHT * 1 / 4, Game.buttonSize, "Start", Game.BUTTON_TYPE.Start));
        addButton(new Button(Game.buttonSize, Game.HEIGHT * 2 / 4, Game.buttonSize, "Help", Game.BUTTON_TYPE.Help));
        addButton(new Button(Game.buttonSize, Game.HEIGHT * 3 / 4, Game.buttonSize, "Quit", Game.BUTTON_TYPE.Quit));
        addButton(new Button(Game.WIDTH* 8 / 9, Game.HEIGHT / 40, Game.buttonSize / 2, "Back", Game.BUTTON_TYPE.Back));
        addButton(new Button(Game.WIDTH * 83 / 100, Game.HEIGHT * 165 / 200, Game.buttonSize /2, "Pause", Game.BUTTON_TYPE.Pause));
    }

    public void update() {
//        for (Button button: buttons) {
//            button.update();
//        }
    }

    public void render(Graphics g) {
        for (Button button: buttons) {
            if (Game.gameState == Game.STATE.Menu && button.getType() != Game.BUTTON_TYPE.Back && button.getType() != Game.BUTTON_TYPE.Pause)
                button.render(g);

            if ((Game.gameState == Game.STATE.Help || Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.Over) && button.getType()  == Game.BUTTON_TYPE.Back) {
                button.render(g);
            }

            if ((Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.Pause) && button.getType() == Game.BUTTON_TYPE.Pause) {
                if (Game.gameState == Game.STATE.Pause)
                    button.setText("Resume");
                else button.setText("Pause");
                button.render(g);
            }
        }

        if (Game.gameState == Game.STATE.Help) {
            g.setColor(Color.WHITE);
            g.drawString("Click on blocks to destroy them. ", Game.WIDTH * 3 / 10, Game.HEIGHT * 50 / 100);
            g.drawString("The maximum score you can earn from each block reduces over time. ", Game.WIDTH * 15 / 100, Game.HEIGHT * 60 / 100);
            g.drawString("You will have 1 minute.", Game.WIDTH * 4 / 10, Game.HEIGHT * 70 / 100);
        }
        if (Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.Pause) {
            g.drawString("Time: " + Game.timer, Game.WIDTH/2, 100);

            g.setFont(new Font("arial", 1, Game.buttonSize / 2));
            g.setColor(Color.RED);
            g.drawString("Killed: " + Game.killed, Game.WIDTH / 40, Game.HEIGHT / 15);
            g.drawString("Score: " + Game.score, Game.WIDTH / 40, Game.HEIGHT * 2 / 15);
        }

        if (Game.gameState == Game.STATE.Pause) {
            g.setFont(new Font("arial", 1, 4 * Game.buttonSize));
            g.setColor(Color.WHITE);
            g.drawString("PAUSED", Game.WIDTH * 10 / 100, Game.HEIGHT / 2);
        }

        if (Game.gameState == Game.STATE.Over) {
            g.setFont(new Font("arial", 1, 3 * Game.buttonSize));
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", Game.WIDTH * 4 / 100, Game.HEIGHT / 2);

            g.setFont(new Font("arial", 1, Game.buttonSize));
            g.drawString("Your score is " + Game.score, Game.WIDTH * 32 / 100, Game.HEIGHT *2/3);
        }
    }

    public void addButton(Button b) {
        buttons.add(b);
    }

    public void checkOverlap(int mx, int my) {
        boolean overlap = false;

        for (Button button: buttons) {
            overlap = button.mouseOverlap(mx, my);

            if (overlap) {
                buttonAction(button.getType());
                break;
            }
        }
    }

    private void buttonAction(Game.BUTTON_TYPE type) {
        switch (type) {
            case Start:
                Game.gameState = Game.STATE.Game;
                System.out.println("Game is starting");
                break;
            case Help:
                if (Game.gameState == Game.STATE.Menu) {
                    Game.gameState = Game.STATE.Help;
                    System.out.println("You have opened the help menu");
                }
                break;
            case Back:
                if (Game.gameState == Game.STATE.Game || Game.gameState == Game.STATE.Over) {
                    Game.endGame();
                }
                Game.gameState = Game.STATE.Menu;
                System.out.println("You have returned to the main menu");
                break;
            case Pause:
                if (Game.gameState == Game.STATE.Game)
                    Game.gameState = Game.STATE.Pause;
                else if (Game.gameState == Game.STATE.Pause)
                    Game.gameState = Game.STATE.Game;
                break;
            case Quit:
                if (Game.gameState == Game.STATE.Menu) {
                    System.exit(1);
                }
                break;
        }
    }


}
