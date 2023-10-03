package chess;

import java.util.Objects;

public class ChessPositionE implements ChessPosition {
    private int row;
    private int col;
    public ChessPositionE(int row, int col) {
        //this.row = row-1;
       // this.col = col-1;
        this.row = row;
        this.col = col;
    }
    public ChessPositionE() {
        row = 0;
        col = 0;
    }
    @Override
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPositionE that = (ChessPositionE) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public int getColumn() {
        return col;
    }
}
