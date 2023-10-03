package chess;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMoveE that = (ChessMoveE) o;
        return Objects.equals(startPosition, that.startPosition) && Objects.equals(endPosition, that.endPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition);
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