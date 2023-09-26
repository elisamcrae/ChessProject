package chess;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardE implements ChessBoard {
    private ArrayList[][] board;

    //private chess.ChessPiece pawns;

    public ChessBoardE() {
        board = new ArrayList[10][10];
        //board[0][0] = new ArrayList(); // add another ArrayList object to [0,0]
        //board[0][0].add(); // add object to that ArrayList;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //board[position] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        //return board[position];
        return null;
    }

    @Override
    public void resetBoard() {
        //board=new ArrayList<List<ChessPiece>>();
        board = new ArrayList[10][10];
    }

}