package shooter;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

//Class used to paint background image while in game
public class Background {

	private Image background;

	//Constructor loads background image
	public Background() {
		URL url = Main.class.getResource("/resources/SpaceBackground.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		background = img.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
	}

	//Method to paint background
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
}