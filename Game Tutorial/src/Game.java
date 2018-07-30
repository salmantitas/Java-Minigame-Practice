import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 140840600533728354L;

    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int OFFSET = 32;
    public static final String TITLE = "Tutorial";

    private Thread thread;
    private boolean running = false;
    public int diff = 0;

    // 0 = normal
    // 1 = hard

    public static boolean paused = false;

    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawn spawn;
    private Menu menu;
    private Shop shop;

    public enum STATE {
        Menu,
        Select,
        Help,
        Shop,
        Game,
        End
    };

    public static STATE gameState = STATE.Menu;

    public static BufferedImage sprite_sheet;

    public Game() {
        BufferedImageLoader loader = new BufferedImageLoader();

//        try {
            sprite_sheet = loader.loadImage("/sprite_sheet.png");
//        }   catch (IOException e) {
//            e.printStackTrace();
//        }

        handler = new Handler();
        hud = new HUD();
        shop = new Shop(handler, hud);
        menu = new Menu(this, handler, hud);
        addKeyListener(new KeyInput(this, handler));
        addMouseListener(menu);
        addMouseListener(shop);

        AudioPlayer.load();
        AudioPlayer.getMusic("music").loop();

        new Window(WIDTH, HEIGHT, TITLE, this);



        spawn = new Spawn(handler, hud, this);
        r = new Random();
        if (gameState == STATE.Game) {
            handler.addObject(new Player(WIDTH/2 - OFFSET, HEIGHT/2 - OFFSET, ID.Player, handler));
            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
        } else {
            for (int i = 0; i < 20; i++)
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try{
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // we need a loop that performs 2 things: it checks whether enough time has passed (1/60 sec) to refresh the game,
    // and checks whether enough time has passed (1 sec) to refresh the FPS counter; while 'running' it adds the time
    // it took to go through one iteration of the loop it self and adds it to delta (which is simplified to 1) so once
    // it reaches 1 delta it means enough time has passed to go forward one tick.

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        if (gameState == STATE.Game) {
            if (!paused) {
                hud.tick();
                spawn.tick();
                handler.tick();

                if (hud.HEALTH <= 0) {
                    hud.HEALTH = 100;
                    gameState = STATE.End;
                    handler.object.clear();
                    for (int i = 0; i < 20; i++)
                        handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
                }
            }
        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select) {
            menu.tick();
            handler.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT );

        if (paused) {
            g.setColor(Color.white);
            g.drawString("Paused", 100, 100);
        }

        if (gameState == STATE.Game) {
            hud.render(g);
            handler.render(g);
        } else if (gameState == STATE.Shop) {
            shop.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
            menu.render(g);
            handler.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, float max) {
        if (var >= max)
            return var = max;
        else if (var <= min)
                return var = min;
        else
            return var;
    }

    public static void main(String args[])
    {
        new Game();
    }

}
