import java.awt.Image;

public class Queen extends ChessPiece {
	
	private static Image white = ChessPiece.loadImage("res/white_queen.png");
	private static Image black = ChessPiece.loadImage("res/black_queen.png");
	
	public Queen(Team team, int row, int col) {
		super(team, row, col, white, black);
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		if (!super.moveIsValid(board, nextPos))
			return false;
		int colDiff = nextPos.getCol() - getPos().getCol();
		int rowDiff = nextPos.getRow() - getPos().getRow();
		if ((rowDiff != 0 && colDiff != 0) && (Math.abs(rowDiff) != Math.abs(colDiff)))
			return false;
		int pieceAtEnd = board.indexAtPos(nextPos);
		if (pieceAtEnd != -1 && board.getPiece(pieceAtEnd).getTeam() == getTeam())
			return false;
		boolean pieceInWay = false;
		if (rowDiff == 0) {
			int sign = Math.abs(colDiff) / colDiff;
			colDiff -= sign;
			while (colDiff != 0 && !pieceInWay) {
				pieceInWay = board.indexAtPos(getPos().getRow(), getPos().getCol() + colDiff) != -1;
				colDiff -= sign;
			}
		} else if (colDiff == 0) {
			int sign = Math.abs(rowDiff) / rowDiff;
			rowDiff -= sign;
			while (rowDiff != 0 && !pieceInWay) {
				pieceInWay  = board.indexAtPos(getPos().getRow() + rowDiff, getPos().getCol()) != -1;
				rowDiff -= sign;
			}
		} else {
			int colSign = Math.abs(colDiff) / colDiff;
			int rowSign = Math.abs(rowDiff) / rowDiff;
			colDiff -= colSign;
			rowDiff -= rowSign;
			while (colDiff != 0 && !pieceInWay) {
				pieceInWay = board.indexAtPos(getPos().getRow() + rowDiff, getPos().getCol() + colDiff) != -1;
				colDiff -= colSign;
				rowDiff -= rowSign;
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
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				calcPossibleMove(getPos(), i, j, board);
			}
		}
	}

}