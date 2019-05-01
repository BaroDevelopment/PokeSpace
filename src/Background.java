import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Background {

    private float posX;
    private float speed;
    private BufferedImage backgroundImage;

    public Background(float speed) {
        this.speed = speed;
        try {
            this.backgroundImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("GFX/bg.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void update(float timeSinceLastFrame) {
        posX -= speed * timeSinceLastFrame;

        if (posX < -backgroundImage.getWidth())
            posX += backgroundImage.getWidth();
    }

    public int getX() {
        return (int) posX;
    }

    /**
     * @return the backgroundImage
     */
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

}
