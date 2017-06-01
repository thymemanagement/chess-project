import java.awt.Image;

public class Knight extends ChessPiece {
	
	private static Image white = ChessPiece.loadImage("res/white_knight.png");
	private static Image black = ChessPiece.loadImage("res/black_knight.png");
	
	public Knight(Team team, int row, int col) {
		super(team, row, col, white, black);
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		if (!super.moveIsValid(board, nextPos))
			return false;
		int colDiff = nextPos.getCol() - getPos().getCol();
		int rowDiff = nextPos.getRow() - getPos().getRow();
		if (rowDiff == 0 || colDiff == 0 || Math.abs(rowDiff) + Math.abs(colDiff) != 3)
			return false;
		int pieceAtEnd = board.indexAtPos(nextPos);
		return pieceAtEnd == -1 || board.getPiece(pieceAtEnd).getTeam() != getTeam();
	}
	
	public void calcPossibleMove(Pos nextPos, ChessBoard board) {
		if (moveIsValid(board, nextPos))
			getPossibleMoves().add(nextPos);
	}
	
	public void calcPossibleMoves(ChessBoard board) {
		int row = getPos().getRow();
		int col = getPos().getCol();
		getPossibleMoves().clear();
		calcPossibleMove(new Pos(row + 2, col + 1), board);
		calcPossibleMove(new Pos(row + 2, col - 1), board);
		calcPossibleMove(new Pos(row + 1, col + 2), board);
		calcPossibleMove(new Pos(row + 1, col - 2), board);
		calcPossibleMove(new Pos(row - 1, col + 2), board);
		calcPossibleMove(new Pos(row - 1, col - 2), board);
		calcPossibleMove(new Pos(row - 2, col + 1), board);
		calcPossibleMove(new Pos(row - 2, col - 1), board);
	}
}
