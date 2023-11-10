package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends ChessPieceE{
    public Knight(ChessGame.TeamColor teamColor) {
        super(teamColor, ChessPieceE.PieceType.KNIGHT);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();

        ChessPositionE newPos = new ChessPositionE();
        newPos.setRow(myPosition.getRow() + 2);
        newPos.setCol(myPosition.getColumn() + 1);
        ChessPositionE newPosB = new ChessPositionE(myPosition.getRow() + 2, myPosition.getColumn() + 1);

        if (newPos.getColumn() >= 0 & newPos.getColumn() < 8 & newPos.getRow() < 8 & newPos.getRow() >= 0) {
            if (board.getPiece(newPosB) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos);
                validMoves.add(move);
            }
            else if (board.getPiece(newPosB).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos);
                validMoves.add(move);
            }
        }

        ChessPositionE newPos1 = new ChessPositionE();
        newPos1.setRow(myPosition.getRow() + 1);
        newPos1.setCol(myPosition.getColumn() + 2);
        ChessPositionE newPos1B = new ChessPositionE(myPosition.getRow() + 1, myPosition.getColumn() + 2);

        if (newPos1.getColumn() >= 0 & newPos1.getColumn() < 8 & newPos1.getRow() < 8 & newPos1.getRow() >= 0) {
            if (board.getPiece(newPos1) == null) {
                ChessMoveE move1 = new ChessMoveE(myPosition, newPos1);
                validMoves.add(move1);
            }
            else if (board.getPiece(newPos1).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move1 = new ChessMoveE(myPosition, newPos1);
                validMoves.add(move1);
            }
        }

        ChessPositionE newPos2 = new ChessPositionE();
        newPos2.setRow(myPosition.getRow() - 2);
        newPos2.setCol(myPosition.getColumn() + 1);
        ChessPositionE newPos2B = new ChessPositionE(myPosition.getRow() - 2, myPosition.getColumn() + 1);

        if (newPos2.getColumn() >= 0 & newPos2.getColumn() < 8 & newPos2.getRow() < 8 & newPos2.getRow() >= 0) {
            if (board.getPiece(newPos2) == null) {
                ChessMoveE move2 = new ChessMoveE(myPosition, newPos2);
                validMoves.add(move2);
            }
            else if (board.getPiece(newPos2).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move2 = new ChessMoveE(myPosition, newPos2);
                validMoves.add(move2);
            }
        }

        ChessPositionE newPos3 = new ChessPositionE();
        newPos3.setRow(myPosition.getRow() - 1);
        newPos3.setCol(myPosition.getColumn() + 2);
        ChessPositionE newPos3B = new ChessPositionE(myPosition.getRow() - 1, myPosition.getColumn() + 2);

        if (newPos3.getColumn() >= 0 & newPos3.getColumn() < 8 & newPos3.getRow() < 8 & newPos3.getRow() >= 0) {
            if (board.getPiece(newPos3) == null) {
                ChessMoveE move3 = new ChessMoveE(myPosition, newPos3);
                validMoves.add(move3);
            }
            else if (board.getPiece(newPos3).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move3 = new ChessMoveE(myPosition, newPos3);
                validMoves.add(move3);
            }
        }
        ChessPositionE newPos4 = new ChessPositionE();
        newPos4.setRow(myPosition.getRow() - 1);
        newPos4.setCol(myPosition.getColumn() - 2);
        ChessPositionE newPosB4 = new ChessPositionE(myPosition.getRow() - 1, myPosition.getColumn() - 2);

        if (newPos4.getColumn() >= 0 & newPos4.getColumn() < 8 & newPos4.getRow() < 8 & newPos4.getRow() >= 0) {
            if (board.getPiece(newPos4) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos4);
                validMoves.add(move);
            }
            else if (board.getPiece(newPos4).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos4);
                validMoves.add(move);
            }
        }
        ChessPositionE newPos5 = new ChessPositionE();
        newPos5.setRow(myPosition.getRow() - 2);
        newPos5.setCol(myPosition.getColumn() - 1);
        ChessPositionE newPos5B = new ChessPositionE(myPosition.getRow() - 2, myPosition.getColumn() - 1);

        if (newPos5.getColumn() >= 0 & newPos5.getColumn() < 8 & newPos5.getRow() < 8 & newPos5.getRow() >= 0) {
            if (board.getPiece(newPos5) == null) {
                ChessMoveE move1 = new ChessMoveE(myPosition, newPos5);
                validMoves.add(move1);
            }
            else if (board.getPiece(newPos5).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move1 = new ChessMoveE(myPosition, newPos5);
                validMoves.add(move1);
            }
        }
        ChessPositionE newPos6 = new ChessPositionE();
        newPos6.setRow(myPosition.getRow() + 2);
        newPos6.setCol(myPosition.getColumn() - 1);
        ChessPositionE newPos6B = new ChessPositionE(myPosition.getRow() + 2, myPosition.getColumn() - 1);

        if (newPos6.getColumn() >= 0 & newPos6.getColumn() < 8 & newPos6.getRow() < 8 & newPos6.getRow() >= 0) {
            if (board.getPiece(newPos6) == null) {
                ChessMoveE move2 = new ChessMoveE(myPosition, newPos6);
                validMoves.add(move2);
            }
            else if (board.getPiece(newPos6).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move6 = new ChessMoveE(myPosition, newPos6);
                validMoves.add(move6);
            }
        }
        ChessPositionE newPos7 = new ChessPositionE();
        newPos7.setRow(myPosition.getRow() + 1);
        newPos7.setCol(myPosition.getColumn() - 2);
        ChessPositionE newPos7B = new ChessPositionE(myPosition.getRow() + 1, myPosition.getColumn() - 2);

        if (newPos7.getColumn() >= 0 & newPos7.getColumn() < 8 & newPos7.getRow() < 8 & newPos7.getRow() >= 0) {
            if (board.getPiece(newPos7) == null) {
                ChessMoveE move3 = new ChessMoveE(myPosition, newPos7);
                validMoves.add(move3);
            }
            else if (board.getPiece(newPos7).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                ChessMoveE move3 = new ChessMoveE(myPosition, newPos7);
                validMoves.add(move3);
            }
        }
        return validMoves;
    }
}
