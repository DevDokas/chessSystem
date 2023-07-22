package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Peao extends ChessPiece {
    public Peao(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }
}
