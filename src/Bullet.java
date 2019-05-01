import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Bullet {

    private static BufferedImage bulletImage;
    private float posX;
    private float posY;
    private float speedX;
    private float speedY;
    private Rectangle bounding;
    private List<Bullet> bullets;
    private float timeAlive;

    private float TIMETOLIVE = 10;

    static {
        try {
            bulletImage = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("GFX/shoot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bullet(float x, float y, float speedx, float speedy, List<Bullet> bullets) {
        this.posX = x;
        this.posY = y;
        this.speedX = speedx;
        this.speedY = speedy;
        bounding = new Rectangle((int) x, (int) y, bulletImage.getWidth(), bulletImage.getHeight());
        this.bullets = bullets;
    }

    public void update(float timeSinceLastFrame) {
        timeAlive += timeSinceLastFrame;
        if (timeAlive > TIMETOLIVE) {
            bullets.remove(this);
        }

        posX += speedX * timeSinceLastFrame;
        posY += speedY * timeSinceLastFrame;
        bounding.x = (int) posX;
        bounding.y = (int) posY;
    }

    public Rectangle getBounding() {
        return bounding;
    }

    /**
     * @return the bulletImage
     */
    public static BufferedImage getBulletImage() {
        return bulletImage;
    }

}
