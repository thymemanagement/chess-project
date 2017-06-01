import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class ChessPiece {
	
	private Image image;
	private Team team;
	private Pos pos;
	private Pos lastPos;
	private int lastMoved;
	private int prevMoved;
	private ArrayList<Pos> possibleMoves;
	public boolean wasPawnDoubleMove;
	
	public ChessPiece(Team color, Pos position, boolean moved, Image whiteImage, Image blackImage) {
		team = color;
		pos = position;
		lastPos = position;
		lastMoved = 0;
		prevMoved = lastMoved;
		wasPawnDoubleMove = false;
		possibleMoves = new ArrayList<Pos>();
		if (team == Team.WHITE) 
			image = whiteImage;
		else
			image = blackImage;
	}
	
	public ChessPiece(Team color, Pos position, Image whiteImage, Image blackImage) {
		this(color, position, false, whiteImage, blackImage);
	}
	
	public ChessPiece(Team color, int row, int col, Image whiteImage, Image blackImage) {
		this(color, new Pos(row, col), whiteImage, blackImage);
	}
	
	public void calcPossibleMoves(ChessBoard board) {}
	
	public int move(Pos newPos, ChessBoard board) {
		prevMoved = lastMoved;
		lastMoved = board.getTurn();
		lastPos = pos;
		return setPos(newPos, board);
	}
	
	public void unmove(ChessBoard board) {
		lastMoved = prevMoved;
		pos = lastPos;
	}
	
	public int setPos(Pos newPos, ChessBoard board) {
		int oldPiece = board.indexAtPos(newPos);
		pos = newPos;
		return oldPiece;
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		return board.posInBounds(nextPos) && !(pos.equals(nextPos));
	}
	
	public boolean moveIsPossible(Pos nextPos) {
		return possibleMoves.contains(nextPos);
	}
	
	public ArrayList<Pos> getPossibleMoves() {
		return possibleMoves;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public Pos getPos() {
		return pos;
	}
	
	public boolean hasMoved() {
		return lastMoved > 0;
	}
	
	public int getLastMove() {
		return lastMoved;
	}
	
	public static Image loadImage(String fileName) {
		try 
		{
			File load = new File(fileName);
		    return ImageIO.read(load);
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		    return null;
		}
	}
	
}
