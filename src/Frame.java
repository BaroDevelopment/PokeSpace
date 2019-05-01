import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;

public class Frame extends JFrame {

    private final Player player;
    private final Background background;
    private BufferStrategy strategy;
    private List<Bullet> bullets;
    private List<Enemy> enemys;


    public Frame(Player player, Background background, List<Bullet> bullets, List<Enemy> enemys) {
        super("blub");
        addKeyListener(new Keyboard());
        this.player = player;
        this.background = background;
        this.bullets = bullets;
        this.enemys = enemys;
    }

    public void makeStrategy() {
        // PageFlipping auf 2 Back Buffer
        createBufferStrategy(2);
        this.strategy = getBufferStrategy();
    }

    public void repaintScreen() {
        Graphics g = strategy.getDrawGraphics();
        draw(g);
        // schmei�en das Graphics Object weg, weil wir fertig sind und es nicht mehr brauchen
        g.dispose();
        //zeichne das Bildschirm
        strategy.show();
    }

    private void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawImage(background.getBackgroundImage(), background.getX(), 0, null);
        g.drawImage(background.getBackgroundImage(), background.getX() + background.getBackgroundImage().getWidth(), 0, null);

        for (int i = 0; i < enemys.size(); i++) {
            Enemy e = enemys.get(i);
            g.drawImage(e.getEnemyImage(), e.getBounding().x, e.getBounding().y, null);
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            g.drawImage(Bullet.getBulletImage(), b.getBounding().x, b.getBounding().y, null);
        }

        g.drawImage(player.getPlayerImage(), player.getBounding().x, player.getBounding().y, null);
    }
}
