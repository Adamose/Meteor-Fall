package shooter;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

//Class for ship instance which is controlled by user
public class Player {

	private int x;
	private int y;
	private int velocity;
	private Image spaceship;

	public Player() {

		//Initializing variables
		x = 285;
		y = 480;
		velocity = 0;

		//Loading image
		URL url = Main.class.getResource("/resources/SpaceShip.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		spaceship = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	}

	//Method to paint spaceship
	public void paint(Graphics g) {
		g.drawImage(spaceship, x, y, null);
	}

	//Method to update ship's location
	public void update() {

		//Moving ship by current velocity
		x += velocity;

		//Making sure ship doesn't go off screen on the left
		if (x <= 0) {
			x = 0;
		}

		//Making sure ship doesn't go off screen on the right
		if (x >= 550) {
			x = 550;
		}
	}

	//Getter for x location (+21 to return center of ship)
	public int getCenterX() {
		return x + 21;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setVelocity(int v) {
		velocity = v;
	}

	public void setX(int x) {
		this.x = x;
	}
}