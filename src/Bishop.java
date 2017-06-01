import java.awt.Image;

public class Bishop extends ChessPiece {
	
	private static Image white = ChessPiece.loadImage("res/white_bishop.png");
	private static Image black = ChessPiece.loadImage("res/black_bishop.png");
	
	public Bishop(Team team, int row, int col) {
		super(team, row, col, white, black);
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		if (!super.moveIsValid(board, nextPos))
			return false;
		int colDiff = nextPos.getCol() - getPos().getCol();
		int rowDiff = nextPos.getRow() - getPos().getRow();
		if (Math.abs(rowDiff) != Math.abs(colDiff))
			return false;
		int pieceAtEnd = board.indexAtPos(nextPos);
		if (pieceAtEnd != -1 && board.getPiece(pieceAtEnd).getTeam() == getTeam())
			return false;
		boolean pieceInWay = false;
		int colSign = Math.abs(colDiff) / colDiff;
		int rowSign = Math.abs(rowDiff) / rowDiff;
		colDiff -= colSign;
		rowDiff -= rowSign;
		while (colDiff != 0 && !pieceInWay) {
			pieceInWay = board.indexAtPos(getPos().getRow() + rowDiff, getPos().getCol() + colDiff) != -1;
			colDiff -= colSign;
			rowDiff -= rowSign;
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
		calcPossibleMove(getPos(), 1, 1, board);
		calcPossibleMove(getPos(), -1, 1, board);
		calcPossibleMove(getPos(), 1, -1, board);
		calcPossibleMove(getPos(), -1, -1, board);
	}

}