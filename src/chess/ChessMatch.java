package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.*;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8,8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private void initialSetup() {

        // White Pieces

            // Torre
        board.placePiece(new Torre(board, Color.WHITE), new Position(0,0));
        board.placePiece(new Torre(board, Color.WHITE), new Position(0, 7));
            // Cavalo
        board.placePiece(new Cavalo(board, Color.WHITE), new Position(0, 1));
        board.placePiece(new Cavalo(board, Color.WHITE), new Position(0, 6));
            // Bispo
        board.placePiece(new Bispo(board, Color.WHITE), new Position(0, 2));
        board.placePiece(new Bispo(board, Color.WHITE), new Position(0, 5));
            // Rei
        board.placePiece(new Rei(board, Color.WHITE), new Position(0, 4));
            // Rainha
        board.placePiece(new Rainha(board, Color.WHITE), new Position(0, 3));
            // Peão
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 0));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 1));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 2));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 3));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 4));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 5));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 6));
        board.placePiece(new Peao(board, Color.WHITE), new Position(1, 7));

        // Black Pieces

            // Torre
        board.placePiece(new Torre(board, Color.BLACK), new Position(7,0));
        board.placePiece(new Torre(board, Color.BLACK), new Position(7, 7));
            // Cavalo
        board.placePiece(new Cavalo(board, Color.BLACK), new Position(7, 1));
        board.placePiece(new Cavalo(board, Color.BLACK), new Position(7, 6));
            // Bispo
        board.placePiece(new Bispo(board, Color.BLACK), new Position(7, 2));
        board.placePiece(new Bispo(board, Color.BLACK), new Position(7, 5));
            // Rei
        board.placePiece(new Rei(board, Color.BLACK), new Position(7, 3));
            // Rainha
        board.placePiece(new Rainha(board, Color.BLACK), new Position(7, 4));
            // Peão
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 0));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 1));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 2));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 3));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 4));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 5));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 6));
        board.placePiece(new Peao(board, Color.BLACK), new Position(6, 7));
    }
}
