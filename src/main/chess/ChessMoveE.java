package chess;
public class ChessMoveE implements ChessMove {
    private ChessPosition startPosition;
    private ChessPosition endPosition;

    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    public void setStartPosition(ChessPosition position) {startPosition = position;}
    public void setEndPosition(ChessPosition position) {endPosition = position;}

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public ChessMoveE(ChessPosition startPosition, ChessPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        //FIXME
        return null;
    }
}