package chess;

public class ChessBoardE implements ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }
    public ChessPiece[][] getBoard(){return board;}

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    public ChessPosition getPosition(ChessPiece.PieceType type, ChessGame.TeamColor color) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] != null) {
                    if (board[i][j].getPieceType() == ChessPiece.PieceType.KING & board[i][j].getTeamColor() == color) {
                        ChessPositionE p = new ChessPositionE();
                        p.setRow(i);
                        p.setCol(j);
                        return p;
                    }
                }
            }
        }
        return null;
    }

    public void makeMove(ChessMove move) {
        ChessPiece.PieceType type = getPiece(move.getStartPosition()).getPieceType();
        ChessGame.TeamColor color = getPiece(move.getStartPosition()).getTeamColor();
        ChessPosition startingPos = move.getStartPosition();
        ChessPosition endingPos = move.getEndPosition();
        board[startingPos.getRow()][startingPos.getColumn()] = null;
        if (type == ChessPiece.PieceType.PAWN) {
            board[endingPos.getRow()][endingPos.getColumn()] = new Pawn(color);
        }
        else if (type == ChessPiece.PieceType.ROOK) {
            board[endingPos.getRow()][endingPos.getColumn()] = new Rook(color);
        }
        else if (type == ChessPiece.PieceType.BISHOP) {
            board[endingPos.getRow()][endingPos.getColumn()] = new Bishop(color);
        }
        else if (type == ChessPiece.PieceType.QUEEN) {
            board[endingPos.getRow()][endingPos.getColumn()] = new Queen(color);
        }
        else if (type == ChessPiece.PieceType.KNIGHT) {
            board[endingPos.getRow()][endingPos.getColumn()] = new Knight(color);
        }
        else if (type == ChessPiece.PieceType.KING) {
            board[endingPos.getRow()][endingPos.getColumn()] = new King(color);
        }
    }

    @Override
    public void resetBoard() {
        board = new ChessPiece[8][8];
        board[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        board[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        board[0][4] = new King(ChessGame.TeamColor.WHITE);
        board[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][7] = new Rook(ChessGame.TeamColor.WHITE);
        //board[0][0] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        //board[0][1] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        //board[0][2] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        //board[0][3] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        //board[0][4] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        //board[0][5] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        //board[0][6] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        //board[0][7] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; ++i) {
            //board[1][i] = new ChessPieceE(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            board[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }
        board[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        board[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][3] = new Queen(ChessGame.TeamColor.BLACK);
        board[7][4] = new King(ChessGame.TeamColor.BLACK);
        board[7][5] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][6] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][7] = new Rook(ChessGame.TeamColor.BLACK);
        //board[7][0] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        //board[7][1] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        //board[7][2] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        //board[7][3] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        //board[7][4] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        //board[7][5] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        //board[7][6] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        //board[7][7] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; ++i) {
            //board[6][i] = new ChessPieceE(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            board[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }
    }

}