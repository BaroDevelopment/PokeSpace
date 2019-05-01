import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


public class ShowDisplayModes {

	public static void main(String[] args) {
		
		/**
		 * zeigt an welche Bitdepth and Freshrate erlaubt sind für das jeweilige Bildschirm
		 */
		
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		
		DisplayMode[] dm = device.getDisplayModes();
		
		for(int i = 0; i < dm.length; i++){
			System.out.println(dm[i].getWidth() + " " + dm[i].getHeight() + " " + dm[i].getBitDepth() + " " + dm[i].getRefreshRate());
		}
	}
}
