package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ChessGameE implements ChessGame {
    private TeamColor teamTurn;
    private ChessBoardE board;

    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //FIXME
        //IS THIS ALL THIS METHOD NEEDS?
        if (board.getPiece(startPosition) == null) {
            return null;
        }

        Collection<ChessMove> moves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        //if (isInCheck(board.getPiece(startPosition).getTeamColor())) {

        //}
        return moves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());

        //move not in valid moves
        if (!moves.contains(move) | moves == null) {
            InvalidMoveException InvalidMoveexception = new InvalidMoveException();
            throw InvalidMoveexception;

        //it's not your turn
        } else if (teamTurn != board.getPiece(move.getStartPosition()).getTeamColor()) {
            InvalidMoveException InvalidMoveexception = new InvalidMoveException();
            throw InvalidMoveexception;
        } else if (isInCheck(teamTurn)) {
            ChessPosition currPos = move.getStartPosition();
            ChessPiece currPiece = board.getPiece(currPos);
            ChessPosition finalPos = move.getEndPosition();
            ChessPiece finalPiece = board.getPiece(finalPos);
            board.makeMove(move);
            if (isInCheck(teamTurn)) {
                board.addPiece(finalPos, finalPiece);
                board.addPiece(currPos, currPiece);

                InvalidMoveException InvalidMoveexception = new InvalidMoveException();
                throw InvalidMoveexception;
            }

        } else {
            board.makeMove(move);
            if (move.getPromotionPiece() != null & (move.getEndPosition().getRow() == 0 | move.getEndPosition().getRow() == 7)) {
                ChessPieceE p = new Pawn(teamTurn);
                if (move.getPromotionPiece() == ChessPiece.PieceType.KING) {
                    p = new King(teamTurn);
                }
                else if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                    p = new Queen(teamTurn);
                }
                else if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    p = new Rook(teamTurn);
                }
                else if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    p = new Bishop(teamTurn);
                }
                else if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    p = new Knight(teamTurn);
                }
                board.addPiece(move.getEndPosition(), p);
                //board[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = move.getPromotionPiece();
            }
        }

        //update turn color
        if (teamTurn == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        } else {
            teamTurn = TeamColor.BLACK;
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = board.getPosition(ChessPiece.PieceType.KING, teamColor);
        //Collection<ChessMove> moves = new ArrayList<ChessMove>();
        Collection<ChessPosition> endPositions = new ArrayList<ChessPosition>();
//        for (int i = 0; i < 8; ++i) {
//            for (int j = 0; j < 8; ++j) {
//                //opposite team pieces
//                ChessPositionE p = new ChessPositionE();
//                p.setRow(i);
//                p.setCol(j);
//                if (board.getPiece(p) != null) {
//                    if (board.getPiece(p).getTeamColor() != teamColor) {
//                        ChessPositionE o = new ChessPositionE();
//                        o.setRow(i);
//                        o.setCol(j);
//                        moves.addAll(validMoves(o));
//                    }
//                }
//            }
//        }
        Collection<ChessMove> moves = getMoves(teamColor);
        for (Iterator<ChessMove> iterator = moves.iterator(); iterator.hasNext(); ) {
            endPositions.add(iterator.next().getEndPosition());
        }
        if (endPositions.contains(kingPos)) {
            return true;
        }
        return false;
    }

    public Collection<ChessMove> getMoves(ChessGame.TeamColor teamColor) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                //opposite team pieces
                ChessPositionE p = new ChessPositionE();
                p.setRow(i);
                p.setCol(j);
                if (board.getPiece(p) != null) {
                    if (board.getPiece(p).getTeamColor() != teamColor) {
                        ChessPositionE o = new ChessPositionE();
                        o.setRow(i);
                        o.setCol(j);
                        moves.addAll(validMoves(o));
                    }
                }
            }
        }
        return moves;

    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        Collection<ChessPosition> endPositions = new ArrayList<ChessPosition>();
        ChessPosition kingPos = board.getPosition(ChessPiece.PieceType.KING, teamColor);
        Collection<ChessMove> possibleMoves = validMoves(kingPos);
        Collection<ChessMove> moves = getMoves(teamColor);

        for (Iterator<ChessMove> iterator = moves.iterator(); iterator.hasNext(); ) {
            endPositions.add(iterator.next().getEndPosition());
        }
        if (endPositions.contains(kingPos)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        if (getMoves(teamColor) == null & teamColor == teamTurn) {
            return true;
        }
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = (ChessBoardE)board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}