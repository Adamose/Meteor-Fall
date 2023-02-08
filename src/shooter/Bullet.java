package shooter;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

//Class used for bullet instances
public class Bullet {

	private int heigth;
	private int width;
	private int x;
	private int y;
	private int velocity;
	private boolean dead;
	private Image bullet;

	//Constructor takes bullet initial x location
	public Bullet(int X) {
		
		//Initializing variables
		heigth = 15;
		width = 10;
		x = X;
		y = 480;
		velocity = 4;
		dead = false;

		//Loading image (bad design to have each instance load the same resource)
		URL url = Main.class.getResource("/resources/Bullet.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		bullet = img.getScaledInstance(10, 15, Image.SCALE_SMOOTH);
	}

	//Method to paint bullet
	public void paint(Graphics g) {
		g.drawImage(bullet, x, y, null);
	}

	//Method to update bullet's location
	public void update() {
		y -= velocity;
	}

	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

	public int getY() {
		return y;
	}

	public boolean getDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
