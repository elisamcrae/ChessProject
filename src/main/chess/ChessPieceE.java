package chess;
import java.util.Collection;

public abstract class ChessPieceE implements ChessPiece {

    private ChessGame.TeamColor teamColor;
    private PieceType pieceType;

    public ChessPieceE(ChessGame.TeamColor teamColor, PieceType pieceType) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

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
        return null;
    }
}