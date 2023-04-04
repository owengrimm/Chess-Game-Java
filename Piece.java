package chessgame;

import java.util.LinkedList;

import static java.lang.System.exit;

public class Piece {

    int x, y; // original coordinates of piece (0-7)
    int newX, newY; // new coordinates of piece
    boolean isWhite; // color of panel
    LinkedList<Piece> piece; // linked list of pieces
    String type;

    // class function
    // linked list allows for easy management of all pieces
    public Piece(int x, int y, boolean isWhite, String type, LinkedList<Piece> piece) {
        this.x = x;
        this.y = y;
        newX = x*64;
        newY = y*64;
        this.isWhite = isWhite;
        this.type = type;
        this.piece = piece;
        // adds piece to linked list of pieces when it is created
        piece.add(this);
    }

    // function used to move piece
    public void move(int x, int y) {

        Piece newPiece = Board.getPiece(x * 64, y * 64);

        // checks if piece exists at new location
        if (newPiece != null) {
            // checks if piece is the same color
            if (newPiece.isWhite != isWhite) {
                // removes piece from location
                newPiece.kill();

                // prints which piece captured another piece
                if (this.isWhite) {
                    System.out.println("White " + this.type + " captured a Black " + newPiece.type);
                } else {
                    System.out.println("Black " + this.type + " captured a White " + newPiece.type);
                }

                // prints winner and terminates program if king taken
                if (newPiece.type == "King") {
                    if (newPiece.isWhite) {
                        System.out.println("Game Over: Black Wins");
                    } else {
                            System.out.println("Game Over: White Wins");
                    }
                    exit(0);
                }
            } else {
                // returns piece to original location if dropped on same color piece
                newX = this.x * 64;
                newY = this.y * 64;
                return;
            }
        }

        // changes x and y coordinates to new x and y parameters
        this.x = x;
        this.y = y;
        newX = x*64;
        newY = y*64;
    }

    // method moves king piece
    public void moveKing(int x, int y) {
        if ((this.y == y+1 && (this.x == x+1 || this.x == x || this.x == x-1))
                || (this.y == y-1 && (this.x == x+1 || this.x == x || this.x == x-1))
                || (this.y == y && (this.x == x+1 || this.x == x-1))) {
            move(x, y);
        } else {
            // returns piece to original location if invalid move attempted
            newX = this.x * 64;
            newY = this.y * 64;
        }
    }

    // method moves queen piece
    public void moveQueen(int x, int y) {
        if ((Math.abs(this.x - x) == Math.abs(this.y - y)) || (this.x == x || this.y == y)) {
            move(x, y);
        } else {
            // returns piece to original location if invalid move attempted
            newX = this.x * 64;
            newY = this.y * 64;
        }
    }

    // method moves bishop piece
    public void moveBishop(int x, int y) {
        if (Math.abs(this.x - x) == Math.abs(this.y - y)) {
            move(x, y);
        } else {
            // returns piece to original location if invalid move attempted
            newX = this.x * 64;
            newY = this.y * 64;
        }
    }

    // method moves knight piece
    public void moveKnight(int x, int y) {
        if ((this.y == y-2 && (this.x == x+1 || this.x == x-1))
                || (this.y == y+2 && (this.x == x+1 || this.x == x-1))
                || (this.x == x-2 && (this.y == y+1 || this.y == y-1))
                || (this.x == x+2 && (this.y == y+1 || this.y == y-1))) {
            move(x, y);
        } else {
            // returns piece to original location if invalid move attempted
            newX = this.x * 64;
            newY = this.y * 64;
        }
    }

    // method moves rook piece
    public void moveRook(int x, int y) {
        if (this.x == x || this.y == y) {
            move(x, y);
        } else {
            // returns piece to original location if invalid move attempted
            newX = this.x * 64;
            newY = this.y * 64;
        }
    }

    // method moves pawn piece
    public void movePawn(int x, int y) {
        // checks if white pawn
        if (this.isWhite && this.y == y+1 && (this.x == x || this.x == x-1 || this.x == x+1)) {
            move(x, y);
        // checks if black pawn
        } else if (!this.isWhite && this.y == y-1 && (this.x == x || this.x == x-1
                || this.x == x+1)) {
            move(x, y);
        } else {
            // returns piece to original location if invalid move attempted
            newX = this.x * 64;
            newY = this.y * 64;
        }
    }

    // method moves piece based on type of piece
    public void movePiece(int x, int y) {
        if (this.type == "King") {
            this.moveKing(x, y);
        } else if (this.type == "Queen") {
            this.moveQueen(x, y);
        } else if (this.type == "Bishop") {
            this.moveBishop(x, y);
        } else if (this.type == "Knight") {
            this.moveKnight(x, y);
        } else if (this.type == "Rook") {
            this.moveRook(x, y);
        } else {
            this.movePawn(x, y);
        }
    }

    // method removes piece at given location
    public void kill() {
        piece.remove(this);
    }
}
