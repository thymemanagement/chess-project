import java.util.ArrayList;

public class ChessBoard {
	
	private static final int BOARD_SIZE = 8;
	
	private ArrayList<ChessPiece> pieces;
	private ChessPiece currentPiece;
	private ChessPiece lastPiece;
	private ChessPiece lastRemoved;
	private boolean getPiece;
	private boolean gameOver;
	private Team currentTurn;
	private King whiteKing;
	private King blackKing;
	private int turnCount;
	
	public ChessBoard(King white, King black) {
		pieces = new ArrayList<ChessPiece>();
		lastRemoved = null;
		lastPiece = null;
		whiteKing = white;
		blackKing = black;
		getPiece = true;
		gameOver = false;
		currentTurn = Team.WHITE;
		turnCount = 1;
	}
	
	public static ChessBoard initGame() {
		King white = new King(Team.WHITE, 0, 4);
		King black = new King(Team.BLACK, 7, 4);
		ChessBoard newBoard = new ChessBoard(white, black);
		newBoard.pieces.add(white);
		newBoard.pieces.add(black);
		newBoard.pieces.add(new Queen(Team.WHITE, 0, 3));
		newBoard.pieces.add(new Queen(Team.BLACK, 7, 3));
		newBoard.pieces.add(new Bishop(Team.WHITE, 0, 2));
		newBoard.pieces.add(new Bishop(Team.WHITE, 0, 5));
		newBoard.pieces.add(new Bishop(Team.BLACK, 7, 2));
		newBoard.pieces.add(new Bishop(Team.BLACK, 7, 5));
		newBoard.pieces.add(new Knight(Team.WHITE, 0, 1));
		newBoard.pieces.add(new Knight(Team.WHITE, 0, 6));
		newBoard.pieces.add(new Knight(Team.BLACK, 7, 1));
		newBoard.pieces.add(new Knight(Team.BLACK, 7, 6));
		newBoard.pieces.add(new Rook(Team.WHITE, 0, 0));
		newBoard.pieces.add(new Rook(Team.WHITE, 0, 7));
		newBoard.pieces.add(new Rook(Team.BLACK, 7, 0));
		newBoard.pieces.add(new Rook(Team.BLACK, 7, 7));
		for (int i = 0; i < 8; i++) {
			newBoard.pieces.add(new Pawn(Team.WHITE, 1, i));
			newBoard.pieces.add(new Pawn(Team.BLACK, 6, i));
		}
		newBoard.calcAllMoves();
		return newBoard;
	}
	
	public void processMove(int row, int col) {
		if (row < BOARD_SIZE && col < BOARD_SIZE) {
			Pos pos = new Pos(row, col);
			if (getPiece) {
				int foundPiece = indexAtPos(pos);
				if (foundPiece > -1 && getPiece(foundPiece).getTeam() == currentTurn) {
					currentPiece = getPiece(foundPiece);
					getPiece = false;
				}
			} else {
				if (currentPiece.moveIsPossible(pos)) {
					int oldPiece = currentPiece.move(pos, this);
					if (oldPiece > -1) {
						lastRemoved = pieces.remove(oldPiece);
					} else {
						lastRemoved = null;
					}
					if (currentTurn == Team.WHITE) 
						currentTurn = Team.BLACK;
					else
						currentTurn = Team.WHITE;
					calcAllMoves();
					turnCount++;
					if (noPossibleMoves()) {
						gameOver = true;
						if (inCheck(currentTurn, getKing(currentTurn).getPos())) {
							if (currentTurn == Team.WHITE) {
								System.out.println("Checkmate. Black Wins!");
							} else {
								System.out.println("Checkmate. White Wins!");
							}
						} else {
							System.out.println("It's a Stalemate!");
						}
					}
					lastPiece = currentPiece;
					getPiece = true;
				} else if (currentPiece.getPos().equals(pos)) {
					getPiece = true;
				}
			}
		}
	}
	
	public void undoMove() {
		if (lastPiece != null) {
			lastPiece.unmove(this);
			if (lastRemoved != null) {
				pieces.add(lastRemoved);
				lastRemoved = null;
			}
			currentTurn = lastPiece.getTeam();
		}
		getPiece = true;
	}
	
	public boolean noPossibleMoves() {
		for (ChessPiece piece : pieces) {
			if (piece.getTeam() == currentTurn && !piece.getPossibleMoves().isEmpty()) 
				return false;
		}
		return true;
	}
	
	public boolean gameOver() {
		return gameOver;
	}
	
	public void calcAllMoves() {
		King currentKing = getKing(currentTurn);
		for (int p = 0; p < pieces.size(); p++) {
			ChessPiece piece = pieces.get(p);
			if (piece.getTeam() == currentTurn) {
				piece.calcPossibleMoves(this);
				int i = 0;
				ArrayList<Pos> moves = piece.getPossibleMoves();
				while (i < moves.size()) {
					Pos oldPos = piece.getPos();
					int oldPiece = piece.setPos(moves.get(i), this);
					ChessPiece justRemoved = null;
					if (oldPiece > -1)
						justRemoved = pieces.remove(oldPiece);
					boolean found = false;
					for (ChessPiece otherPiece : pieces) {
						if (otherPiece.getTeam() != currentTurn && otherPiece.moveIsValid(this, currentKing.getPos()) && !found) {
							moves.remove(i);
							found = true;
						} 
					}
					if (!found)
						i++;
					piece.setPos(oldPos, this);
					if (justRemoved != null) 
						pieces.add(justRemoved);
				}
			}
		}
	}
	
	public void addPiece(ChessPiece piece) {
		pieces.add(piece);
	}
	
	public boolean inCheck(Team team, Pos kingPos) {
		for (ChessPiece piece : pieces) {
			if (piece.getTeam() != team && piece.moveIsValid(this, kingPos))
				return true;
		}
		return false;
	}
	
	public ChessPiece getPiece(int index) {
		return pieces.get(index);
	}
	
	public ChessPiece removePiece(Pos piecePos) {
		int removeIndex = indexAtPos(piecePos);
		if (removeIndex > -1) 
			return pieces.remove(removeIndex);
		return null;
	}
	
	public int size() {
		return pieces.size();
	}
	
	public int indexAtPos(Pos pos) {
		for (int i = 0; i < pieces.size(); i++)
			if (pieces.get(i).getPos().equals(pos))
				return i;
		return -1;
	}
	
	public int indexAtPos(int row, int col) {
		for (int i = 0; i < pieces.size(); i++) {
			Pos nextPos = pieces.get(i).getPos();
			if (nextPos.getRow() == row && nextPos.getCol() == col)
				return i;
		}
		return -1;
	}
	
	public King getKing(Team team) {
		if (team == Team.WHITE)
			return whiteKing;
		return blackKing;
	}
	
	public int getSide(Team team) {
		if (team == Team.WHITE)
			return 0;
		return 7;
	}
	
	public ChessPiece getCurrentPiece() {
		if (getPiece)
			return null;
		return currentPiece;
	}
	
	public boolean posInBounds(Pos pos) {
		return pos.getRow() < BOARD_SIZE && pos.getRow() >= 0 && pos.getCol() < BOARD_SIZE && pos.getCol() >= 0;
	}
	
	public int getTurn() {
		return turnCount;
	}
	
	public ChessPiece getLastRemoved() {
		return lastRemoved;
	}

}
