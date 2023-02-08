package shooter;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;

//Class for rock instances
public class Rock {

	private int heigth;
	private int width;
	private int x;
	private int y;
	private int gravity;
	private boolean blown;
	private Image rock;
	private Random random;

	public Rock() {

		//Initializing variables
		random = new Random();
		heigth = 30;
		width = 40;
		x = random.nextInt(560) + 1;
		y = -30;
		gravity = 2;
		blown = false;

		//Loading image (bad design to have each instance load the same resource)
		URL url = Main.class.getResource("/resources/Rock.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		rock = img.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
	}

	//Method to paint rock
	public void paint(Graphics g) {
		g.drawImage(rock, x, y, null);
	}

	//Method to update rock's location
	public void update() {
		y += gravity;
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

	public boolean getBlown() {
		return blown;
	}

	public void setBlown(boolean blown) {
		this.blown = blown;
	}
}