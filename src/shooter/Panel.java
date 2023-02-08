package shooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

//Class containing game logic and game loop (also acts as main canvas for drawing and contains key listeners)
public class Panel extends JPanel implements KeyListener {

	private Player player;
	private Background gameBackground;
	private Image menuBackground;
	private boolean inGame ;
	private boolean first;
	private int score;
	private int highscore;

	//Game entitie arrays
	private ArrayList<Rock> rocks;
	private ArrayList<Bullet> bullets;
	private ArrayList<Explosion> explosions;

	//Fonts
	private Font font;
	private Font font1;
	private Font font2;
	private Font font3;

	//Timer for main game loop (runs at 60hz to get 60 fps)
	private Timer timer = new Timer(15, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			update();
		}
	});

	//Timer to create new rock instance (runs at 1hz to get one new rock per second)
	private Timer timer2 = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			rocks.add(new Rock());
		}
	});

	public Panel() {
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(600, 600));

		//Initlizing variables
		player = new Player();
		gameBackground = new Background();
		rocks = new ArrayList<Rock>();
		bullets = new ArrayList<Bullet>();
		explosions = new ArrayList<Explosion>();
		inGame = false;
		first = true;
		score = 0;
		highscore = 0;
		font = new Font("ArialBlack", Font.PLAIN, 30);
		font1 = new Font("ArialBlack", Font.PLAIN, 100);
		font2 = new Font("ArialBlack", Font.PLAIN, 50);
		font3 = new Font("ComicSansMS", Font.PLAIN, 30);

		//Loading menu background image
		URL url = Main.class.getResource("/resources/back.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		menuBackground = img.getScaledInstance(600, 600, Image.SCALE_SMOOTH);

		//Event handler for W keypress
		InputMap i = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "w");
		ActionMap a = this.getActionMap();
		a.put("w", new AbstractAction() {

			//Creates new bullet at center of player's current location
			public void actionPerformed(ActionEvent e) {
				bullets.add(new Bullet(player.getCenterX()));
			}
		});

		//Event handler for ENTER keypress
		InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "enter");
		ActionMap am = this.getActionMap();
		am.put("enter", new AbstractAction() {

			//If user is in menu, starting new round
			public void actionPerformed(ActionEvent e) {
				if (inGame == false) {
					rocks.clear();
					bullets.clear();
					explosions.clear();
					inGame = true;
					first = false;
					timer2.start();
					score = 0;
					player.setX(285);
				}
			}
		});

		//Starting game loop
		timer.start();
	}

	//Method to paint background and all game entities to screen
	public void paint(Graphics g) {

		//Paint for when user is in game
		if (inGame == true) {

			//Painting background
			gameBackground.paint(g);

			//Painting spaceship
			player.paint(g);

			//Painting game entities
			for (Explosion e : explosions) {
				e.paint(g);
			}

			for (Bullet b : bullets) {
				b.paint(g);
			}

			for (Rock r : rocks) {
				r.paint(g);
			}

			//Painting score
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("" + score, 540, 30);
		}

		//Paint for when user is in the menu for the first time
		if (inGame == false && first == true) {

			//Painting menu background
			g.drawImage(menuBackground, 0, 0, null);

			//Painting title
			g.setColor(Color.gray);
			g.setFont(font1);
			g.drawString("Meteor", 135, 80);
			g.drawString("Fall", 230, 180);

			//Painting message
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Press ENTER To Play", 70, 450);

			//Painting keybinds
			g.setFont(font3);
			g.setColor(Color.blue);
			g.drawString("Keys To Move", 30, 260);
			g.drawString("Key To Shoot", 380, 260);
			g.drawString("A - D", 90, 300);
			g.drawString("W", 460, 300);
		}

		//Paint for when user is in the menu after a round
		if (inGame == false && first == false) {

			//Painting menu background
			g.drawImage(menuBackground, 0, 0, null);

			//Painting title
			g.setColor(Color.gray);
			g.setFont(font1);
			g.drawString("Meteor", 135, 80);
			g.drawString("Fall", 230, 180);

			//Painting messages
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Press ENTER To Play", 70, 450);
			g.drawString("Again", 230, 500);
			g.setColor(Color.orange);
			g.drawString("Game", 235, 240);
			g.drawString("Over", 250, 290);

			//Painting scores
			g.setFont(font3);
			g.setColor(Color.blue);
			g.drawString("Score", 100, 260);
			g.drawString("Highscore", 400, 260);
			g.drawString("" + score, 130, 300);
			g.drawString("" + highscore, 470, 300);
		}
	}

	//Method to update game entities
	public void update() {

		//Updating game entities
		player.update();

		for (Bullet b : bullets) {
			b.update();
			checkBulletCollission(b);
		}

		for (Explosion e : explosions) {
			e.update();
		}

		for (Rock r : rocks) {
			r.update();
			checkRockCollision(r);
		}

		//Removing entities that are not needed anymore
		bullets.removeIf(bullet -> (bullet.getY() < -20));
		explosions.removeIf(explosion -> (explosion.getTime() >= 100));
		rocks.removeIf(rock -> rock.getBlown());
		bullets.removeIf(bullet -> bullet.getDead());

		//Updating screen (calls paint method)
		repaint();
	}

	//Method to check if a rock collided with earth or spaceship (checks if player lost)
	public void checkRockCollision(Rock r) {
		
		//Checking if rock collided with earth (y >= 520) or if it's rectangle intersects the spaceship's rectangle 
		if (r.getY() >= 520 || new Rectangle(player.getX(), player.getY(), 50, 50).intersects(new Rectangle(r.getX(), r.getY(), 40, 30))) {

			//Stopping game
			inGame = false;
			timer2.stop();

			//Checking if highscore needs to be updated
			if (score > highscore) {
				highscore = score;
			}
		}
	}

	//Method to check if a bullet collided with a rock
	public void checkBulletCollission(Bullet b) {

		//Looping through all rocks
		for (Rock r : rocks) {

			//Checking if bullet's rectangle intersects with rock's rectangle
			if (new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeigth()).intersects(new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeigth()))) {
				explosions.add(new Explosion(b.getX(), b.getY()));
				b.setDead(true);
				r.setBlown(true);
				score += 1;
			}
		}
	}

	//Event handler for key pressed events
	public void keyPressed(KeyEvent e) {

		//Move spaceship when user presses A or D
		if (e.getKeyCode() == KeyEvent.VK_A) {
			player.setVelocity(-8);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			player.setVelocity(8);
		}
	}

	//Event handler for key released events
	public void keyReleased(KeyEvent e) {

		//Stop moving spaceship when players releases A or D
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
			player.setVelocity(0);
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}