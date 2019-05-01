import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Application {

    static List<Pokeball> pokeballs = new LinkedList<>();
    static List<Charmander> charmanders = new LinkedList<Charmander>();
    static Player player = new Player(300, 300, 1920, 1080, pokeballs, charmanders);
    static Background background = new Background(50);
    static Random r = new Random();

    public static void main(String[] args) {

        spawnCharmander();

        Frame frame = new Frame(player, background, pokeballs, charmanders);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);

        DisplayMode displayMode = new DisplayMode(1920, 1080, 32, 60);
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();

        // Start in full screen
        device.setFullScreenWindow(frame);

        frame.makeStrategy();

        long lastFrame = System.currentTimeMillis();

        final float CHARMANDA_SPAWNTIME = 1f;
        float timeSinceLastCharmanderSpawn = 0;

        while (true) {
            if (Keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
                System.exit(0);
            long thisFrame = System.currentTimeMillis();
            float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
            lastFrame = thisFrame;

            timeSinceLastCharmanderSpawn += timeSinceLastFrame;
            if (timeSinceLastCharmanderSpawn > CHARMANDA_SPAWNTIME) {
                timeSinceLastCharmanderSpawn -= CHARMANDA_SPAWNTIME;
                spawnCharmander();
            }

            player.update(timeSinceLastFrame);
            background.update(timeSinceLastFrame);
            frame.repaintScreen();

            for (int i = 0; i < pokeballs.size(); i++) {
                pokeballs.get(i).update(timeSinceLastFrame);
            }

            for (int i = 0; i < charmanders.size(); i++) {
                charmanders.get(i).update(timeSinceLastFrame);
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static void spawnCharmander() {
        charmanders.add(new Charmander(1920, r.nextInt(1080 - Charmander.getHeight()), pokeballs, player));
    }
}
