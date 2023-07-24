package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.BRANCO;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source  = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("Não existe nenhuma peça na posição inicial indicada.");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
            throw new ChessException("A peça escolhida não é sua.");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("Não existe movimentos disponíveis para a peça escolhida.");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("A Peça escolhida não pode se mover para a posição de destino.");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.BRANCO) ? Color.PRETO :  Color.BRANCO;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {

        // White Pieces

            // Torre
        placeNewPiece('a', 8, new Torre(board, Color.BRANCO));
        placeNewPiece('h', 8, new Torre(board, Color.BRANCO));
            // Cavalo
        placeNewPiece('b', 8, new Cavalo(board, Color.BRANCO));
        placeNewPiece('g', 8, new Cavalo(board, Color.BRANCO));
            // Bispo
        placeNewPiece('c', 8, new Bispo(board, Color.BRANCO));
        placeNewPiece('f', 8, new Bispo(board, Color.BRANCO));
            // Rei
        placeNewPiece('e', 8, new Rei(board, Color.BRANCO));
            // Rainha
        placeNewPiece('d', 8, new Rainha(board, Color.BRANCO));
            // Peão
        placeNewPiece('a', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('b', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('c', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('d', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('e', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('f', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('g', 7, new Peao(board, Color.BRANCO));
        placeNewPiece('h', 7, new Peao(board, Color.BRANCO));

        // Black Pieces

            // Torre
        placeNewPiece('a', 1, new Torre(board, Color.PRETO));
        placeNewPiece('h', 1, new Torre(board, Color.PRETO));
            // Cavalo
        placeNewPiece('b', 1, new Cavalo(board, Color.PRETO));
        placeNewPiece('g', 1, new Cavalo(board, Color.PRETO));
            // Bispo
        placeNewPiece('c', 1, new Bispo(board, Color.PRETO));
        placeNewPiece('f', 1, new Bispo(board, Color.PRETO));
            // Rei
        placeNewPiece('d', 1, new Rei(board, Color.PRETO));
            // Rainha
        placeNewPiece('e', 1, new Rainha(board, Color.PRETO));
            // Peão
        placeNewPiece('a', 2, new Peao(board, Color.PRETO));
        placeNewPiece('b', 2, new Peao(board, Color.PRETO));
        placeNewPiece('c', 2, new Peao(board, Color.PRETO));
        placeNewPiece('d', 2, new Peao(board, Color.PRETO));
        placeNewPiece('e', 2, new Peao(board, Color.PRETO));
        placeNewPiece('f', 2, new Peao(board, Color.PRETO));
        placeNewPiece('g', 2, new Peao(board, Color.PRETO));
        placeNewPiece('h', 2, new Peao(board, Color.PRETO));
    }
}
