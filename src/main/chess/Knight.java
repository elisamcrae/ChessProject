package chess;

import java.util.Collection;

public class Knight extends ChessPieceE{
    public Knight(ChessGame.TeamColor teamColor, PieceType pieceType) {
        super(teamColor, pieceType);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = null;

        ChessPositionE newPos = new ChessPositionE(myPosition.getRow() + 2, myPosition.getColumn() - 1);
        if (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            ChessMoveE move = new ChessMoveE(myPosition, newPos);
            validMoves.add(move);
        }

        ChessPositionE newPos1 = new ChessPositionE(myPosition.getRow() + 1, myPosition.getColumn() - 2);
        if (board.getPiece(newPos1).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            ChessMoveE move = new ChessMoveE(myPosition, newPos1);
            validMoves.add(move);
        }

        ChessPositionE newPos2 = new ChessPositionE(myPosition.getRow() - 2, myPosition.getColumn() - 1);
        if (board.getPiece(newPos2).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            ChessMoveE move = new ChessMoveE(myPosition, newPos2);
            validMoves.add(move);
        }

        ChessPositionE newPos3 = new ChessPositionE(myPosition.getRow() - 1, myPosition.getColumn() - 2);
        if (board.getPiece(newPos3).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            ChessMoveE move = new ChessMoveE(myPosition, newPos3);
            validMoves.add(move);
        }
        return validMoves;

    }
}
