import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setTitle("Chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BoardComponent board = new BoardComponent(ChessBoard.initGame());
		board.setLocation(30,30);
		window.getContentPane().add(board);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}

}
