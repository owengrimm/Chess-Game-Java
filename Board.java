package chessgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Board {

    // creates global linked list of pieces on board
    public static LinkedList<Piece> piece = new LinkedList<>();
    // creates global variable to represent piece selected by user
    public static Piece selectedPiece = null;

    public static void main(String[] args) throws IOException {

        // reads in ChessPieces image as BufferedImage
        BufferedImage chess = ImageIO.read(new File("/Users/owengrimm/Downloads/ChessPieces.png"));
        Image pieces[] = new Image[12]; // array storing image of each piece type

        int index = 0; // index of each chess piece
        // iterates through each chess piece in image
        for (int y = 0; y < 400; y+=200) {
            for (int x = 0; x < 1200; x += 200) {
                // extracts chess piece image from png and reformats to chess board square size
                // stares as index of array
                pieces[index] = chess.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        // places each piece in designated, initial location on chess board
        // white pieces
        Piece whiteKing = new Piece(4, 7, true, "King", piece);
        Piece whiteQueen = new Piece(3, 7, true, "Queen", piece);
        Piece whiteBishop1 = new Piece(2, 7, true, "Bishop", piece);
        Piece whiteBishop2 = new Piece(5, 7, true, "Bishop", piece);
        Piece whiteKnight1 = new Piece(1, 7, true, "Knight", piece);
        Piece whiteKnight2 = new Piece(6, 7, true, "Knight", piece);
        Piece whiteRook1 = new Piece(0, 7, true, "Rook", piece);
        Piece whiteRook2 = new Piece(7, 7, true, "Rook", piece);
        Piece whitePawn1 = new Piece(0, 6, true, "Pawn", piece);
        Piece whitePawn2 = new Piece(1, 6, true, "Pawn", piece);
        Piece whitePawn3 = new Piece(2, 6, true, "Pawn", piece);
        Piece whitePawn4 = new Piece(3, 6, true, "Pawn", piece);
        Piece whitePawn5 = new Piece(4, 6, true, "Pawn", piece);
        Piece whitePawn6 = new Piece(5, 6, true, "Pawn", piece);
        Piece whitePawn7 = new Piece(6, 6, true, "Pawn", piece);
        Piece whitePawn8 = new Piece(7, 6, true, "Pawn", piece);

        // black pieces
        Piece blackKing = new Piece(4, 0, false, "King", piece);
        Piece blackQueen = new Piece(3, 0, false, "Queen", piece);
        Piece blackBishop1 = new Piece(2, 0, false, "Bishop", piece);
        Piece blackBishop2 = new Piece(5, 0, false, "Bishop", piece);
        Piece blackKnight1 = new Piece(1, 0, false, "Knight", piece);
        Piece blackKnight2 = new Piece(6, 0, false, "Knight", piece);
        Piece blackRook1 = new Piece(0, 0, false, "Rook", piece);
        Piece blackRook2 = new Piece(7, 0, false, "Rook", piece);
        Piece blackPawn1 = new Piece(0, 1, false, "Pawn", piece);
        Piece blackPawn2 = new Piece(1, 1, false, "Pawn", piece);
        Piece blackPawn3 = new Piece(2, 1, false, "Pawn", piece);
        Piece blackPawn4 = new Piece(3, 1, false, "Pawn", piece);
        Piece blackPawn5 = new Piece(4, 1, false, "Pawn", piece);
        Piece blackPawn6 = new Piece(5, 1, false, "Pawn", piece);
        Piece blackPawn7 = new Piece(6, 1, false, "Pawn", piece);
        Piece blackPawn8 = new Piece(7, 1, false, "Pawn", piece);


        // jframe represents framed window for chess board
        JFrame boardFrame = new JFrame();
        boardFrame.setBounds(10, 10, 512, 512); // height and width of board set to 512
        // accounts for boarders when board is displayed on computer
        boardFrame.setUndecorated(true);

        // jpanel represents area in which controls and visuals can appear
        JPanel boardPanel = new JPanel() {
            // overrides paint class
            @Override
            // everything written in this space will appear on screen
            public void paint(Graphics g) {

                // creates squares in eight columns
                for (int y = 0; y < 8; y++) {
                    // creates squares in eight rows
                    for (int x = 0; x < 8; x++) {

                        // sets half squares beige and other half darkGray
                        if ((y % 2 == 0 && x % 2 == 0) || (y % 2 == 1 && x % 2 == 1)) {
                            g.setColor(new Color(235, 235, 200));
                        } else {
                            g.setColor(new Color(0, 70, 100));
                        }

                        // fills each square on board
                        // x & y are from for loops and represent coordinates
                        // height and width are of each square
                        g.fillRect(x*64, y*64, 64, 64);
                    }
                }

                // for loop iterates through linked list of pieces
                for (Piece p: piece) {
                    int index = 0; // int represents index of pieces in png

                    // assigns index to each piece type
                    if (p.type.equalsIgnoreCase("King")) {
                        index = 0;
                    }
                    if (p.type.equalsIgnoreCase("Queen")) {
                        index = 1;
                    }
                    if (p.type.equalsIgnoreCase("Bishop")) {
                        index = 2;
                    }
                    if (p.type.equalsIgnoreCase("Knight")) {
                        index = 3;
                    }
                    if (p.type.equalsIgnoreCase("Rook")) {
                        index = 4;
                    }
                    if (p.type.equalsIgnoreCase("Pawn")) {
                        index = 5;
                    }

                    //
                    if (!p.isWhite) {
                        index += 6;
                    }

                    // draws each piece in linked list
                    g.drawImage(pieces[index], p.newX, p.newY, this);
                }
            }
        };

        boardFrame.add(boardPanel); // moves board to visible region

        // creates mouse motion listener so that user can interact with chess game
        boardFrame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // assigns new coordinates of piece to location of dragged mouse
                if (selectedPiece != null) {
                    selectedPiece.newX = e.getX() - 32;
                    selectedPiece.newY = e.getY() - 32;
                    boardFrame.repaint();
                }
            }

            // irrelevant code
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        // creates mouse listener so that user can interact with chess game
        boardFrame.addMouseListener(new MouseListener() {
            // irrelevant code
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // prints color and type of piece if it exists at coordinated clicked
                // System.out.println((getPiece(e.getX(), e.getY()).isWhite? "White ":"Black ") + getPiece(e.getX(), e.getY()).type);
                selectedPiece = getPiece(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // moves piece to new coordinate when mouse is released
                // x and y values divided by 64 to get square coordinate values
                selectedPiece.movePiece(e.getX()/64, e.getY()/64);
                boardFrame.repaint();
            }

            // irrelevant code
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        boardFrame.setDefaultCloseOperation(3); // program exits when frame window is closed
        boardFrame.setVisible(true); // sets board visibility to true
    }

    // getPiece method returns piece at given coordinates
    public static Piece getPiece(int x, int y) {
        // converts actual mouse coordinates to square coordinates
        int newX = x/64;
        int newY = y/64;

        // returns piece at given coordinates if one exists there
        for (Piece p: piece) {
            if (p.x == newX && p.y == newY) {
                return p;
            }
        }
        return null;
    }
}