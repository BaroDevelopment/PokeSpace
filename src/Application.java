import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Application {

    static List<Bullet> bullets = new LinkedList<Bullet>();
    static List<Enemy> enemys = new LinkedList<Enemy>();
    static Player player = new Player(300, 300, 1920, 1080, bullets, enemys);
    static Background background = new Background(50);
    static Random r = new Random();

    public static void main(String[] args) {

        spawnEnemy();

        Frame frame = new Frame(player, background, bullets, enemys);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setUndecorated(true);

        // wird von device erledigt
        // frame.setVisible(true);

        frame.setResizable(false);
        // 16 Bitdepth (Farbtiefe) , 75 = Refreshrate
        DisplayMode displayMode = new DisplayMode(1920, 1080, 32, 60);
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        // JFrame erbt von Window deswegen können wir hier statt windows zu übergeben, einfach unseren Frame übergeben
        device.setFullScreenWindow(frame);

        frame.makeStrategy();

        long lastFrame = System.currentTimeMillis();

        final float ENEMYSPAWNTIME = 1f;
        float timeSinceLastEnemySpawn = 0;

        while (true) {
            if (Keyboard.isKeyDown(KeyEvent.VK_ESCAPE))
                System.exit(0);

            long thisFrame = System.currentTimeMillis();
            // (thisFrame - lastFrame) / 1000 --> Umrechnung von Millisekunden in Sekunden
            // 1000f => 1000 soll nicht als ganze Zahl interpretiert werden, sondern als Kommazahl
            float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
            lastFrame = thisFrame;

            timeSinceLastEnemySpawn += timeSinceLastFrame;
            if (timeSinceLastEnemySpawn > ENEMYSPAWNTIME) {
                timeSinceLastEnemySpawn -= ENEMYSPAWNTIME;
                spawnEnemy();
            }

            player.update(timeSinceLastFrame);
            background.update(timeSinceLastFrame);
            frame.repaintScreen();

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).update(timeSinceLastFrame);
            }

            for (int i = 0; i < enemys.size(); i++) {
                enemys.get(i).update(timeSinceLastFrame);
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void spawnEnemy() {
        enemys.add(new Enemy(1920, r.nextInt(1080 - Enemy.getHeight()), bullets, player));
    }
}
