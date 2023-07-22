package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Bispo extends ChessPiece {

    public Bispo(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }
}
