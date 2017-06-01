import java.awt.Image;

public class King extends ChessPiece {
	
	private static Image white = ChessPiece.loadImage("res/white_king.png");
	private static Image black = ChessPiece.loadImage("res/black_king.png");
	
	private ChessPiece rookMoved = null;
	
	public King(Team team, int row, int col) {
		super(team, row, col, white, black);
	}
	
	public boolean moveIsValid(ChessBoard board, Pos nextPos) {
		if (!super.moveIsValid(board, nextPos))
			return false;
		int colDiff = nextPos.getCol() - getPos().getCol();
		int rowDiff = nextPos.getRow() - getPos().getRow();
		if (!hasMoved() && Math.abs(colDiff) == 2 && rowDiff == 0) {
			int kingDir = colDiff / 2;
			int rookCol = (7 + (7 * kingDir)) / 2;
			int rookIndex = board.indexAtPos(getPos().getRow(), rookCol);
			if (rookIndex > -1 && !board.getPiece(rookIndex).hasMoved()) {
				rookCol += -kingDir;
				int pieceSum = 0;
				while (rookCol != getPos().getCol()) {
					pieceSum += board.indexAtPos(getPos().getRow(), rookCol);
					rookCol += -kingDir;
				}
				if ((kingDir < 0 && pieceSum == -3) || (kingDir > 0 && pieceSum == -2)) {
					if (!board.inCheck(getTeam(), getPos())) {
						Pos midPos = new Pos(getPos().getRow(), getPos().getCol() + kingDir);
						Pos oldPos = getPos();
						setPos(midPos, board);
						if (!board.inCheck(getTeam(), getPos())) {
							setPos(oldPos, board);
							return true;
						}
						setPos(oldPos, board);
					}
				}
			}
		}
		if (Math.abs(rowDiff) > 1 || Math.abs(colDiff) > 1)
			return false;
		int pieceAtEnd = board.indexAtPos(nextPos);
		return pieceAtEnd == -1 || board.getPiece(pieceAtEnd).getTeam() != getTeam();
	}
	
	public int move(Pos newPos, ChessBoard board) {
		int colDiff = newPos.getCol() - getPos().getCol();
		int oldPiece = super.move(newPos, board);
		rookMoved = null;
		if (Math.abs(colDiff) > 1) {
			ChessPiece rook = board.getPiece(board.indexAtPos(getPos().getRow(), (14 + (7 * colDiff)) / 4));
			rook.move(new Pos(getPos().getRow(), newPos.getCol() - colDiff / 2), board);
			rookMoved = rook;
		}
		return oldPiece;
	}
	
	public void unmove(ChessBoard board) {
		super.unmove(board);
		if (rookMoved != null) {
			rookMoved.unmove(board);
		}
	}
	
	public void calcPossibleMove(Pos nextPos, ChessBoard board) {
		if (moveIsValid(board, nextPos))
			getPossibleMoves().add(nextPos);
	}
	
	public void calcPossibleMoves(ChessBoard board) {
		int row = getPos().getRow();
		int col = getPos().getCol();
		getPossibleMoves().clear();
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				calcPossibleMove(new Pos(r, c), board);
			}
		}
		calcPossibleMove(new Pos(row, col + 2), board);
		calcPossibleMove(new Pos(row, col - 2), board);
	}
}