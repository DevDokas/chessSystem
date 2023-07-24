package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Cavalo extends ChessPiece {
    public Cavalo(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "C";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
