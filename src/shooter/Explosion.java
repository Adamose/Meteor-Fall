package shooter;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

//Class for explosion instances
public class Explosion {

	private int time;
	private int x;
	private int y;
	private Image explosion;

	//Constructor takes explosion's initial x and y locations
	public Explosion(int X, int Y) {

		//Initializing variables
		time = 0;
		x = X;
		y = Y;

		//Loading image (bad design to have each instance load the same resource)
		URL url = Main.class.getResource("/resources/Explosion.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		explosion = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	}

	//Method to update explosion's time variable (lifespan of explosion)
	public void update() {
		time += 10;
	}

	//Method to paint explosion
	public void paint(Graphics g) {
		g.drawImage(explosion, x - 15, y - 15, null);
	}

	public int getTime() {
		return time;
	}
}