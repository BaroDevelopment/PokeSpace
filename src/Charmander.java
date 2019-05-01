import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Charmander {
	private static BufferedImage[] enemyImage = new BufferedImage[4];
	private static BufferedImage enemyDeadImage;
	private final static float NEEDEDANITIME = 0.5f;
	private float aniTime = 0;
	private float posX;
	private float posY;
	private Rectangle bounding;
	private List<Pokeball> bullets;
	private boolean alive = true;
	private final static Random r = new Random();
	private Player player;

	static {
		try {
			enemyImage[0] = ImageIO.read(Pokeball.class.getClassLoader().getResourceAsStream("GFX/charmander_1.png"));
			enemyImage[1] = ImageIO.read(Pokeball.class.getClassLoader().getResourceAsStream("GFX/charmander_2.png"));
			enemyImage[2] = ImageIO.read(Pokeball.class.getClassLoader().getResourceAsStream("GFX/charmander_3.png"));
			enemyImage[3] = enemyImage[1];
			enemyDeadImage = ImageIO.read(Pokeball.class.getClassLoader().getResourceAsStream("GFX/dead.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Charmander(float x, float y, List<Pokeball> bullets, Player player) {
		this.posX = x;
		this.posY = y;
		bounding = new Rectangle((int) x, (int) y, enemyImage[0].getWidth(), enemyImage[0].getHeight());
		this.bullets = bullets;
		this.player = player;
	}

	public void update(float timeSinceLastFrame) {
		aniTime += timeSinceLastFrame;
		if (aniTime > NEEDEDANITIME)
			aniTime = 0;


		// Bewegungsmuster: Achsenanpassung

//		if(alive){
//			//Oben
//			if(player.getY() - posY < -10){
//				posY -= 100 * timeSinceLastFrame;
//			}
//			//Unten
//			else if(player.getY() - posY > 10){
//				posY += 100 * timeSinceLastFrame;
//			}
//
//			//Links
//			if(player.getX() < posX){
//				posX -= 100 * timeSinceLastFrame;
//			}
//			//Rechts
//			else if(player.getX() > posX){
//				posX += 100 * timeSinceLastFrame;
//			}
//		}
//
//		if(posX <=-getEnemyImage().getWidth()){
//			posX = 1920;
//			posY = r.nextInt(1080 - getEnemyImage().getHeight());
//			alive = true;
//		}

		//Bewegungsmuster: Vektorbasierend

		if(alive){

			float speedX = player.getX() - posX;
			float speedY = player.getY() - posY;

			float speed = (float) Math.sqrt(speedX * speedX + speedY * speedY);

			if(speed != 0){
				speedX /= speed;
				speedY /= speed;

				posX += speedX;
				posY += speedY;
			}

		}


		for(int i = 0; i < bullets.size(); i++){
			Pokeball b = bullets.get(i);

			if(bounding.intersects(b.getBounding())){
				alive = false;
			}
		}
		bounding.x = (int) posX;
		bounding.y = (int) posY;
	}

	public Rectangle getBounding() {
		return bounding;
	}

	public BufferedImage getEnemyImage() {
		if (!alive)
			return enemyDeadImage;
		else {
			if (enemyImage.length == 0)
				return null;
			for (int i = 0; i < enemyImage.length; i++) {

				if (aniTime < (float) (NEEDEDANITIME / enemyImage.length * (i + 1))) {
					return enemyImage[i];
				}
			}
			return enemyImage[enemyImage.length - 1];
		}
	}

	public static int getWidth(){
		return enemyImage[0].getWidth();
	}
	public static int getHeight(){
		return enemyImage[0].getHeight();
	}

	public boolean isAlive() {
		return alive;
	}
}
