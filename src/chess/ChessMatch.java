package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

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

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
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

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode colocar a si mesmo em cheque.");
        }
        check = (testCheck(opponent(currentPlayer))) ? true: false;

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // Roque pequeno
        if (p instanceof Rei && target.getColumn() == source.getColumn() +2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() +3);
            Position targetT = new Position(source.getRow(), source.getColumn() +1);
            ChessPiece torre = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(torre, targetT);
            torre.increaseMoveCount();
        }

        // Roque grande
        if (p instanceof Rei && target.getColumn() == source.getColumn() -2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() -4);
            Position targetT = new Position(source.getRow(), source.getColumn() -1);
            ChessPiece torre = (ChessPiece) board.removePiece(sourceT);
            board.placePiece(torre, targetT);
            torre.increaseMoveCount();
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // Roque pequeno
        if (p instanceof Rei && target.getColumn() == source.getColumn() +2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() +3);
            Position targetT = new Position(source.getRow(), source.getColumn() +1);
            ChessPiece torre = (ChessPiece) board.removePiece(targetT);
            board.placePiece(torre, sourceT);
            torre.decreaseMoveCount();
        }

        // Roque grande
        if (p instanceof Rei && target.getColumn() == source.getColumn() -2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() -4);
            Position targetT = new Position(source.getRow(), source.getColumn() -1);
            ChessPiece torre = (ChessPiece) board.removePiece(targetT);
            board.placePiece(torre, sourceT);
            torre.decreaseMoveCount();
        }
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

    private Color opponent(Color color) {
        return (color == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() ==  color)
                .collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof Rei) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("Não existe o rei da cor " + color + " no tabuleiro.");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() ==  opponent(color))
                .collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() ==  opponent(color))
                .collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {

        // White Pieces

            // Torre
        placeNewPiece('a', 1, new Torre(board, Color.BRANCO));
        placeNewPiece('h', 1, new Torre(board, Color.BRANCO));
            // Cavalo
        placeNewPiece('b', 1, new Cavalo(board, Color.BRANCO));
        placeNewPiece('g', 1, new Cavalo(board, Color.BRANCO));
            // Bispo
        placeNewPiece('c', 1, new Bispo(board, Color.BRANCO));
        placeNewPiece('f', 1, new Bispo(board, Color.BRANCO));
            // Rei
        placeNewPiece('e', 1, new Rei(board, Color.BRANCO, this));
            // Rainha
        placeNewPiece('d', 1, new Rainha(board, Color.BRANCO));
            // Peão
        placeNewPiece('a', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('b', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('c', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('d', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('e', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('f', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('g', 2, new Peao(board, Color.BRANCO));
        placeNewPiece('h', 2, new Peao(board, Color.BRANCO));

        // Black Pieces

        // Torre
        placeNewPiece('a', 8, new Torre(board, Color.PRETO));
        placeNewPiece('h', 8, new Torre(board, Color.PRETO));
        // Cavalo
        placeNewPiece('b', 8, new Cavalo(board, Color.PRETO));
        placeNewPiece('g', 8, new Cavalo(board, Color.PRETO));
        // Bispo
        placeNewPiece('c', 8, new Bispo(board, Color.PRETO));
        placeNewPiece('f', 8, new Bispo(board, Color.PRETO));
        // Rei
        placeNewPiece('e', 8, new Rei(board, Color.PRETO, this));
        // Rainha
        placeNewPiece('d', 8, new Rainha(board, Color.PRETO));
        // Peão
        placeNewPiece('a', 7, new Peao(board, Color.PRETO));
        placeNewPiece('b', 7, new Peao(board, Color.PRETO));
        placeNewPiece('c', 7, new Peao(board, Color.PRETO));
        placeNewPiece('d', 7, new Peao(board, Color.PRETO));
        placeNewPiece('e', 7, new Peao(board, Color.PRETO));
        placeNewPiece('f', 7, new Peao(board, Color.PRETO));
        placeNewPiece('g', 7, new Peao(board, Color.PRETO));
        placeNewPiece('h', 7, new Peao(board, Color.PRETO));

    }
}
