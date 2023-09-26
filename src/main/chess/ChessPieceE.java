package chess;
import java.util.Collection;

public class ChessPieceE implements chess.ChessPiece {
    private ChessGame.TeamColor teamColor;
    private PieceType pieceType;

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = null;
        return validMoves;
    }
}