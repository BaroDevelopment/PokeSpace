import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


public class Player {

    private Rectangle bounding;
    private float posX;
    private float posY;
    private int worldsizeX;
    private int worldsizeY;

    private final float SHOTFREQUENZY = 0.1f;

    private float timeSinceLastShot = 0;

    private List<Pokeball> bullets;
    private List<Charmander> enemys;
    private BufferedImage playerImage;
    private BufferedImage playerDeadImage;
    private boolean alive = true;

    public Player(int x, int y, int worldsizeX, int worldsizeY, List<Pokeball> bullets, List<Charmander> enemys) {
        try {
            playerImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("GFX/ash.png"));
            playerDeadImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("GFX/ash_dead.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bounding = new Rectangle(x, y, playerImage.getWidth(), playerImage.getHeight());
        posX = x;
        posY = y;
        this.worldsizeX = worldsizeX;
        this.worldsizeY = worldsizeY;
        this.bullets = bullets;
        this.enemys = enemys;
    }

    public void update(float timeSinceLastFrame) {

        if (!alive)
            return;

        timeSinceLastShot += timeSinceLastFrame;

        // wenn wir nur 300 nehmen würden, dann würde er nur 300 pixel pro frame machen.
        // bei langsamen PC's würde er sich dann auch langsam bewegen !
        // deswegen setzen wir das ins verhältnis damit sich das auf jeden PC gleich schnell bewegt
        if (Keyboard.isKeyDown(KeyEvent.VK_W) || Keyboard.isKeyDown(KeyEvent.VK_UP))
            posY -= 300 * timeSinceLastFrame;
        if (Keyboard.isKeyDown(KeyEvent.VK_S) || Keyboard.isKeyDown(KeyEvent.VK_DOWN))
            posY += 300 * timeSinceLastFrame;
        if (Keyboard.isKeyDown(KeyEvent.VK_A) || Keyboard.isKeyDown(KeyEvent.VK_LEFT))
            posX -= 300 * timeSinceLastFrame;
        if (Keyboard.isKeyDown(KeyEvent.VK_D) || Keyboard.isKeyDown(KeyEvent.VK_RIGHT))
            posX += 300 * timeSinceLastFrame;

        if (timeSinceLastShot > SHOTFREQUENZY && Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
            timeSinceLastShot = 0;
            bullets.add(new Pokeball(posX + playerImage.getWidth() / 2 - Pokeball.getBulletImage().getWidth() / 2, posY + playerImage.getHeight() / 2 - Pokeball.getBulletImage().getHeight() / 2, 500, 0, bullets));
        }

        for (int i = 0; i < enemys.size(); i++) {
            Charmander e = enemys.get(i);

            if (e.isAlive() && bounding.intersects(e.getBounding())) {
                alive = false;
            }
        }

        bounding.x = (int) posX;
        bounding.y = (int) posY;

        if (posX < 0)
            posX = 0;
        if (posY < 0)
            posY = 0;
        if (posX > worldsizeX - bounding.width)
            posX = worldsizeX - bounding.width;
        if (posY > worldsizeY - bounding.height)
            posY = worldsizeY - bounding.height;
    }

    public Rectangle getBounding() {
        return bounding;
    }

    /**
     * @return the playerImage
     */
    public BufferedImage getPlayerImage() {
        if (alive)
            return playerImage;
        else
            return playerDeadImage;
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

}
