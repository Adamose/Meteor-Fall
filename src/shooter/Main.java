package shooter;

import javax.swing.JFrame;

//Main class which acts as JFrame and contains program's entry point
public class Main extends JFrame {

	//Setting JFrame's proprities
	public Main() {
		this.setResizable(false);
		this.setTitle("Meteor Fall");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new Panel());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}