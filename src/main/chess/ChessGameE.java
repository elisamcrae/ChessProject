package chess;

import java.util.Collection;

public class ChessGameE implements ChessGame {
    private TeamColor teamTurn;
    private ChessBoardE board;
    @Override
    public TeamColor getTeamTurn() { return teamTurn; }

    @Override
    public void setTeamTurn(TeamColor team) { teamTurn = team; }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        return null;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = (ChessBoardE)board;
    }

    @Override
    public ChessPiece[][] getBoard() {
        return board.getBoard();
    }
}