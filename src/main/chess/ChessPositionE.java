package chess;
public class ChessPositionE implements ChessPosition {
    private int row;
    private int col;
    public ChessPositionE(int row, int col) {
        this.row = row-1;
        this.col = col-1;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int getColumn() {
        return col;
    }
}
