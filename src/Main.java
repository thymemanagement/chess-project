import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main {
	
	public static BoardComponent board;
	public static JLabel status;
	
	public static void main(String[] args) {
		
		class BoardListener implements MouseListener {

			public void mouseClicked(MouseEvent arg0) {
				board.processClick(arg0.getX(), arg0.getY());
				status.setText(board.getMessage());
			}
			
			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {}
			
		}
		
		class KeyBoardListener implements KeyListener {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_R) {
					board.reset();
				} else if (e.getKeyCode() == KeyEvent.VK_U) {
					board.undo();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
			
		}
		
		class RestartListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				board.reset();
				status.setText(board.getMessage());
			}
		}
		
		class UndoListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				board.undo();
				status.setText(board.getMessage());
			}
		}
		
		class QuitListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		}
		
		JFrame window = new JFrame();
		window.setTitle("Chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.addKeyListener(new KeyBoardListener());
		window.setFocusable(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(255,210,145));
		panel.setOpaque(false);
		
		board = new BoardComponent(ChessBoard.initGame());
		board.addMouseListener(new BoardListener());
		status = new JLabel();
		status.setText(board.getMessage());
		status.setBorder(new EmptyBorder(10,10,10,10));
		
		JMenuBar bar = new JMenuBar();
		JMenu game = new JMenu("Game");
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(new RestartListener());
		game.add(restart);
		JMenuItem undo = new JMenuItem ("Undo");
		undo.addActionListener(new UndoListener());
		game.add(undo);
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new QuitListener());
		game.add(quit);
		bar.add(game);
		
		panel.add(bar, BorderLayout.NORTH);
		panel.add(status, BorderLayout.SOUTH);
		panel.add(board, BorderLayout.WEST);
		window.add(panel);
		window.pack();
		window.requestFocus();
		window.setVisible(true);
	}

}
