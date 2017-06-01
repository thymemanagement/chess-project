import java.awt.Image;

public class Rook extends ChessPiece {
	
	private static Image white = ChessPiece.loadImage("res/white_rook.png");
	private static Image black = ChessPiece.loadImage("res/black_rook.png");
	
	public Rook(Team team, int row, int col) {
		super(team, row, col, white, black);
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		if (!super.moveIsValid(board, nextPos))
			return false;
		int colDiff = nextPos.getCol() - getPos().getCol();
		int rowDiff = nextPos.getRow() - getPos().getRow();
		if (rowDiff != 0 && colDiff != 0)
			return false;
		int pieceAtEnd = board.indexAtPos(nextPos);
		if (pieceAtEnd != -1 && board.getPiece(pieceAtEnd).getTeam() == getTeam())
			return false;
		boolean pieceInWay = false;
		int sign;
		if (rowDiff == 0) {
			sign = Math.abs(colDiff) / colDiff;
			colDiff -= sign;
			while (colDiff != 0 && !pieceInWay) {
				pieceInWay = board.indexAtPos(getPos().getRow(), getPos().getCol() + colDiff) != -1;
				colDiff -= sign;
			}
		} else {
			sign = Math.abs(rowDiff) / rowDiff;
			rowDiff -= sign;
			while (rowDiff != 0 && !pieceInWay) {
				pieceInWay  = board.indexAtPos(getPos().getRow() + rowDiff, getPos().getCol()) != -1;
				rowDiff -= sign;
			}
		}
		return !pieceInWay;
	}
	
	public void calcPossibleMove(Pos lastPos, int rowDelta, int colDelta, ChessBoard board) {
		Pos nextPos = new Pos(lastPos.getRow() + rowDelta, lastPos.getCol() + colDelta);
		if (moveIsValid(board, nextPos)) {
			getPossibleMoves().add(nextPos);
			calcPossibleMove(nextPos, rowDelta, colDelta, board);
		}
	}
	
	public void calcPossibleMoves(ChessBoard board) {
		getPossibleMoves().clear();
		calcPossibleMove(getPos(), 1, 0, board);
		calcPossibleMove(getPos(), -1, 0, board);
		calcPossibleMove(getPos(), 0, 1, board);
		calcPossibleMove(getPos(), 0, -1, board);
	}

}
