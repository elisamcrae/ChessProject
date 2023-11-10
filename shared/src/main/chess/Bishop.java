package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends ChessPieceE{
    public Bishop(ChessGame.TeamColor teamColor) {
        super(teamColor, ChessPieceE.PieceType.BISHOP);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();

        ChessPosition currPos = new ChessPositionE();
        ((ChessPositionE) currPos).setRow(myPosition.getRow());
        ((ChessPositionE) currPos).setCol(myPosition.getColumn());
        ChessGame.TeamColor myTeamColor = board.getPiece(myPosition).getTeamColor();

        ChessPosition myPos = new ChessPositionE();
        ((ChessPositionE) myPos).setRow(myPosition.getRow()+1);
        ((ChessPositionE) myPos).setCol(myPosition.getColumn()+1);

        //diagonal up right
        while(currPos.getRow()!=7 & currPos.getColumn() !=7) {
            ((ChessPositionE) currPos).setRow(currPos.getRow()+1);
            ((ChessPositionE) currPos).setCol(currPos.getColumn()+1);

            if (board.getPiece(currPos)==null) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
            }

            //ran into your own piece
            else if (board.getPiece(currPos).getTeamColor()==myTeamColor) {
                break;
            }
            //capture other player's piece
            else if (board.getPiece(currPos).getTeamColor()!=myTeamColor) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
                break;
            }

        }
        ((ChessPositionE) currPos).setRow(myPosition.getRow());
        ((ChessPositionE) currPos).setCol(myPosition.getColumn());

        //diagonal up left
        while(currPos.getRow()!=0 & currPos.getColumn()!=7) {
            ((ChessPositionE) currPos).setRow(currPos.getRow()-1);
            ((ChessPositionE) currPos).setCol(currPos.getColumn()+1);

            if (board.getPiece(currPos)==null) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
            }

            //ran into your own piece
            else if (board.getPiece(currPos).getTeamColor()==myTeamColor) {
                break;
            }
            //capture other player's piece
            else if (board.getPiece(currPos).getTeamColor()!=myTeamColor) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
                break;
            }
        }
        ((ChessPositionE) currPos).setRow(myPosition.getRow());
        ((ChessPositionE) currPos).setCol(myPosition.getColumn());

        //diagonal down right
        while(currPos.getColumn()!=0 & currPos.getRow()!=7) {
            ((ChessPositionE) currPos).setCol(currPos.getColumn()-1);
            ((ChessPositionE) currPos).setRow(currPos.getRow()+1);

            if (board.getPiece(currPos)==null) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
            }

            //ran into your own piece
            else if (board.getPiece(currPos).getTeamColor()==myTeamColor) {
                break;
            }
            //capture other player's piece
            else if (board.getPiece(currPos).getTeamColor()!=myTeamColor) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
                break;
            }
        }
        ((ChessPositionE) currPos).setRow(myPosition.getRow());
        ((ChessPositionE) currPos).setCol(myPosition.getColumn());

        //diagonal down left
        while(currPos.getColumn()!=0 & currPos.getRow()!=0) {
            ((ChessPositionE) currPos).setCol(currPos.getColumn()-1);
            ((ChessPositionE) currPos).setRow(currPos.getRow()-1);

            if (board.getPiece(currPos)==null) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
            }

            //ran into your own piece
            else if (board.getPiece(currPos).getTeamColor()==myTeamColor) {
                break;
            }
            //capture other player's piece
            else if (board.getPiece(currPos).getTeamColor()!=myTeamColor) {
                ChessPosition x = new ChessPositionE();
                ((ChessPositionE) x).setRow(currPos.getRow());
                ((ChessPositionE) x).setCol(currPos.getColumn());
                //ChessMoveE move = new ChessMoveE(myPosition, new ChessPositionE(currPos.getRow(), currPos.getColumn()));
                ChessMoveE move = new ChessMoveE(myPosition, x);
                validMoves.add(move);
                x = null;
                break;
            }
        }

        return validMoves;
    }
}
