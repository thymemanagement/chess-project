import java.awt.Image;
import java.util.ArrayList;

public class Pawn extends ChessPiece {
	
	private static Image white = ChessPiece.loadImage("res/white_pawn.png");
	private static Image black = ChessPiece.loadImage("res/black_pawn.png");
	
	private ChessPiece newPiece = null;
	
	public Pawn(Team team, int row, int col) {
		super(team, row, col, white, black);
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		if (!super.moveIsValid(board, nextPos))
			return false;
		int pawnDir = getPos().getRow() - board.getSide(getTeam());
		pawnDir = Math.abs(pawnDir) / pawnDir;
		int colDiff = nextPos.getCol() - getPos().getCol();
		int rowDiff = nextPos.getRow() - getPos().getRow();
		if (Math.abs(colDiff) > 1 || pawnDir * rowDiff < 1 || (!hasMoved() && pawnDir * rowDiff > 2) || (hasMoved() && rowDiff * pawnDir > 1))
			return false;
		int pieceAtEnd = board.indexAtPos(nextPos);
		if (colDiff == 0 && pieceAtEnd != -1) {
			return false;
		}
		if (pawnDir * rowDiff > 1 && board.indexAtPos(nextPos.getRow() - pawnDir, nextPos.getCol()) != -1) {
			return false;
		}
		else if (colDiff != 0) {
			if (pieceAtEnd == -1) {
				int sidePiece = board.indexAtPos(getPos().getRow(), nextPos.getCol());
				if (sidePiece != -1 && board.getPiece(sidePiece).wasPawnDoubleMove && board.getPiece(sidePiece).getLastMove() == board.getTurn())
					return true;
				else
					return false;
			} else if (board.getPiece(pieceAtEnd).getTeam() == getTeam() || rowDiff * pawnDir > 1)
				return false;
		}
		return true;
	}
	
	public int move(Pos newPos, ChessBoard board) {
		int rowDiff = newPos.getRow() - getPos().getRow();
		int oldPiece = super.move(newPos, board);
		if (newPos.getRow() == 0 || newPos.getRow() == 7) {
			board.removePiece(getPos());
			newPiece = new Queen(getTeam(), 0, 0);
			newPiece.move(newPos, board);
			board.addPiece(newPiece);
		}
		wasPawnDoubleMove = Math.abs(rowDiff) > 1;
		return oldPiece;
	}
	
	public void unmove(ChessBoard board) {
		super.unmove(board);
		if (newPiece != null) {
			board.addPiece(this);
			board.removePiece(newPiece.getPos());
			newPiece = null;
		}
	}
	
	public int setPos(Pos newPos, ChessBoard board) {
		int pawnDir = getPos().getRow() - board.getSide(getTeam());
		pawnDir = Math.abs(pawnDir) / pawnDir;
		int colDiff = newPos.getCol() - getPos().getCol();
		int rowDiff = newPos.getRow() - getPos().getRow();
		int oldPiece = super.setPos(newPos, board);
		if (oldPiece == -1 && Math.abs(colDiff) == 1 && rowDiff == pawnDir) {
			oldPiece = board.indexAtPos(new Pos(newPos.getRow() - pawnDir, newPos.getCol()));
		}
		return oldPiece;
	}
	
	public void calcPossibleMove(Pos nextPos, ChessBoard board) {
		if (moveIsValid(board, nextPos))
			getPossibleMoves().add(nextPos);
	}
	
	public void calcPossibleMoves(ChessBoard board) {
		int pawnDir = getPos().getRow() - board.getSide(getTeam());
		pawnDir = Math.abs(pawnDir) / pawnDir;
		int row = getPos().getRow();
		int col = getPos().getCol();
		getPossibleMoves().clear();
		calcPossibleMove(new Pos(row + pawnDir, col), board);
		calcPossibleMove(new Pos(row + 2 * pawnDir, col), board);
		calcPossibleMove(new Pos(row + pawnDir, col + 1), board);
		calcPossibleMove(new Pos(row + pawnDir, col - 1), board);
	}
}