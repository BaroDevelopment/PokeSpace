import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;

public class Frame extends JFrame {

    private final Player player;
    private final Background background;
    private BufferStrategy strategy;
    private List<Pokeball> bullets;
    private List<Charmander> enemys;


    public Frame(Player player, Background background, List<Pokeball> bullets, List<Charmander> enemys) {
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
        g.dispose();
        strategy.show();
    }

    private void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawImage(background.getBackgroundImage(), background.getX(), 0, null);
        g.drawImage(background.getBackgroundImage(), background.getX() + background.getBackgroundImage().getWidth(), 0, null);

        for (int i = 0; i < enemys.size(); i++) {
            Charmander e = enemys.get(i);
            g.drawImage(e.getEnemyImage(), e.getBounding().x, e.getBounding().y, null);
        }

        for (int i = 0; i < bullets.size(); i++) {
            Pokeball b = bullets.get(i);
            g.drawImage(Pokeball.getBulletImage(), b.getBounding().x, b.getBounding().y, null);
        }

        g.drawImage(player.getPlayerImage(), player.getBounding().x, player.getBounding().y, null);
    }
}
