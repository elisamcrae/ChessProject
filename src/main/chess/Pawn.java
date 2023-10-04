package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPieceE{
    public Pawn(ChessGame.TeamColor teamColor) {
        super(teamColor, ChessPieceE.PieceType.PAWN);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();


        //if first move, you can move two up
        ChessPositionE newPos = new ChessPositionE();
        newPos.setCol(myPosition.getColumn());
        newPos.setRow(myPosition.getRow() + 2);

        if ((myPosition.getRow() == 1 | myPosition.getRow() == 6) & newPos.getRow() >= 0 & newPos.getRow() < 8  & board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            ChessPositionE oldPos = new ChessPositionE();
            oldPos.setCol(myPosition.getColumn());
            oldPos.setRow(myPosition.getRow() + 1);
            if (board.getPiece(newPos) == null & board.getPiece(oldPos) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos);
                validMoves.add(move);
            }
        }

        ChessPositionE newPosb = new ChessPositionE();
        newPosb.setCol(myPosition.getColumn());
        newPosb.setRow(myPosition.getRow() - 2);

        if ((myPosition.getRow() == 1 | myPosition.getRow() == 6) & newPosb.getRow() >= 0 & newPosb.getRow() < 8  & board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            ChessPositionE oldPos = new ChessPositionE();
            oldPos.setRow(myPosition.getRow() - 1);
            oldPos.setCol(myPosition.getColumn());
            if (board.getPiece(newPosb) == null & board.getPiece(oldPos) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPosb);
                validMoves.add(move);
            }
        }

        //go up one for black
        ChessPositionE newPos1 = new ChessPositionE();
        newPos1.setCol(myPosition.getColumn());
        newPos1.setRow(myPosition.getRow() - 1);

        if (newPos1.getColumn() >= 0 & newPos1.getColumn() < 8 & board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (board.getPiece(newPos1) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos1);
                validMoves.add(move);
            }
        }

        //go up one for white
        ChessPositionE newPos1w = new ChessPositionE();
        newPos1w.setCol(myPosition.getColumn());
        newPos1w.setRow(myPosition.getRow() + 1);

        if (newPos1w.getRow() >= 0 & newPos1w.getRow() < 8 & board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (board.getPiece(newPos1w) == null) {
                ChessMoveE move = new ChessMoveE(myPosition, newPos1w);
                validMoves.add(move);
            }
        }

        //kill an opponent
        ChessPositionE newPos2 = new ChessPositionE();
        newPos2.setCol(myPosition.getColumn() + 1);
        newPos2.setRow(myPosition.getRow() + 1);
        if (newPos2.getRow() < 8 & newPos2.getColumn() < 8) {
            if (board.getPiece(newPos2) != null) {
                if (board.getPiece(newPos2).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMoveE move = new ChessMoveE(myPosition, newPos2);
                    validMoves.add(move);
                }
            }
        }

        //kill an opponent
        ChessPositionE newPos3 = new ChessPositionE();
        newPos3.setCol(myPosition.getColumn() + 1);
        newPos3.setRow(myPosition.getRow() - 1);
        if (newPos3.getRow() >= 0 & newPos3.getColumn() < 8) {
            if (board.getPiece(newPos3) != null) {
                if (board.getPiece(newPos3).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMoveE move = new ChessMoveE(myPosition, newPos3);
                    validMoves.add(move);
                }
            }
        }

        //kill an opponent
        ChessPositionE newPos4 = new ChessPositionE();
        newPos4.setCol(myPosition.getColumn() - 1);
        newPos4.setRow(myPosition.getRow() + 1);
        if (newPos4.getRow() < 8 & newPos4.getColumn() >= 0) {
            if (board.getPiece(newPos4) != null) {
                if (board.getPiece(newPos4).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMoveE move = new ChessMoveE(myPosition, newPos4);
                    validMoves.add(move);
                }
            }
        }

        //kill an opponent
        ChessPositionE newPos5 = new ChessPositionE();
        newPos5.setCol(myPosition.getColumn() - 1);
        newPos5.setRow(myPosition.getRow() - 1);
        if (newPos5.getRow() >= 0 & newPos5.getColumn() >= 0) {
            if (board.getPiece(newPos5) != null) {
                if (board.getPiece(newPos5).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMoveE move = new ChessMoveE(myPosition, newPos5);
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
    }
}
