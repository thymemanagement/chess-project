import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class BoardComponent extends JComponent {
	
	private ChessBoard board;
	private int boardXPos = 0;
	private int boardYPos = 0;
	private static final int IMAGE_SIZE = 50;
	private static final Color LIGHT_COLOR = new Color(255,222,133);
	private static final Color DARK_COLOR = new Color(232,117,0);
	private static final Color SELECT_COLOR = new Color(255, 255, 0, 56);
	
	public BoardComponent(ChessBoard boardIn) {
		
		class BoardListener implements MouseListener {

			public void mouseClicked(MouseEvent arg0) {
				if (!board.gameOver()) {
					board.processMove(7 - (arg0.getY() - boardYPos) / IMAGE_SIZE, (arg0.getX() - boardXPos) / IMAGE_SIZE);
					repaint();
				}
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
					reset();
					repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_U) {
					if (!board.gameOver())
						board.undoMove();
					repaint();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
			
		}
		
		addKeyListener(new KeyBoardListener());
		addMouseListener(new BoardListener());
		setPreferredSize(new Dimension(IMAGE_SIZE * 8, IMAGE_SIZE * 8));
		this.board = boardIn;
		setFocusable(true);
		requestFocusInWindow();
	}
	
	public void reset() {
		board = ChessBoard.initGame();
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.drawString("sup", 400, 400);
		g.setColor(LIGHT_COLOR);
		g.fillRect(boardXPos, boardYPos, IMAGE_SIZE * 8, IMAGE_SIZE * 8);
		g.setColor(DARK_COLOR);
		for (int i = 0; i < 8 * 9; i += 2) {
			if (i % 9 < 8)
				g.fillRect((i % 9) * IMAGE_SIZE + boardXPos, (i / 9) * IMAGE_SIZE + boardYPos, IMAGE_SIZE, IMAGE_SIZE);
		}
		ChessPiece currentPiece = board.getCurrentPiece();
		if (currentPiece != null) {
			g.setColor(SELECT_COLOR);
			for (Pos move : currentPiece.getPossibleMoves()) {
				g.fillRect(move.getCol() * IMAGE_SIZE + boardXPos, (7 - move.getRow()) * IMAGE_SIZE + boardYPos, IMAGE_SIZE, IMAGE_SIZE);
			}
			g.setColor(Color.BLUE);
			g.drawRect(currentPiece.getPos().getCol() * IMAGE_SIZE + boardXPos, (7 - currentPiece.getPos().getRow()) * IMAGE_SIZE + boardYPos, IMAGE_SIZE, IMAGE_SIZE);
		}
		g.setColor(SELECT_COLOR);
		for (int i = 0; i < board.size(); i++) {
			ChessPiece nextPiece = board.getPiece(i);
			g.drawImage(nextPiece.getImage(), nextPiece.getPos().getCol() * IMAGE_SIZE + boardXPos, IMAGE_SIZE * 7 - nextPiece.getPos().getRow() * IMAGE_SIZE + boardYPos, this);
		}
	}

}
