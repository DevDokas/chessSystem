package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rainha extends ChessPiece {
    public Rainha(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
