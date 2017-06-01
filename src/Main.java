import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setTitle("Chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(50 * 8 + 16, 50 * 9 - 11);
		window.add(new BoardComponent(ChessBoard.initGame()));
		window.setVisible(true);
	}

}
