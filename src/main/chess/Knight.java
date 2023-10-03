package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends ChessPieceE{
    public Knight(ChessGame.TeamColor teamColor) {
        super(teamColor, ChessPieceE.PieceType.KNIGHT);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        //ChessPositionE myPos = new ChessPositionE(myPosition.getRow()-1, myPosition.getColumn()-1);

        ChessPositionE newPos = new ChessPositionE();
        newPos.setRow(myPosition.getRow() + 2);
        newPos.setCol(myPosition.getColumn() + 1);
        //ChessPositionE newPos = new ChessPositionE(myPosition.getRow() + 2, myPosition.getColumn() + 1);

        if (newPos.getColumn() >= 0 & newPos.getRow() >= 0) {
            if (board.getPiece(newPos) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos);
                validMoves.add(move);
            }
            else if (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos);
                validMoves.add(move);
            }
        }

        ChessPositionE newPos1 = new ChessPositionE(myPosition.getRow() + 1, myPosition.getColumn() + 2);
        if (newPos.getColumn() >= 0 & newPos.getRow() >= 0) {
            if (board.getPiece(newPos) == null) {
                ChessMoveE move1 = new ChessMoveE(myPosition, newPos1);
                validMoves.add(move1);
            }
            else if (board.getPiece(newPos1).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move1 = new ChessMoveE(myPosition, newPos1);
                validMoves.add(move1);
            }
        }

        ChessPositionE newPos2 = new ChessPositionE(myPosition.getRow() - 2, myPosition.getColumn() + 1);
        if (newPos.getColumn() >= 0 & newPos.getRow() >= 0) {
            if (board.getPiece(newPos) == null) {
                ChessMoveE move2 = new ChessMoveE(myPosition, newPos2);
                validMoves.add(move2);
            }
            else if (board.getPiece(newPos2).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move2 = new ChessMoveE(myPosition, newPos2);
                validMoves.add(move2);
            }
        }

        ChessPositionE newPos3 = new ChessPositionE(myPosition.getRow() - 1, myPosition.getColumn() + 2);
        if (newPos.getColumn() >= 0 & newPos.getRow() >= 0) {
            if (board.getPiece(newPos) == null) {
                ChessMoveE move3 = new ChessMoveE(myPosition, newPos3);
                validMoves.add(move3);
            }
            else if (board.getPiece(newPos3).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move3 = new ChessMoveE(myPosition, newPos3);
                validMoves.add(move3);
            }
        }
        return validMoves;

    }
}
