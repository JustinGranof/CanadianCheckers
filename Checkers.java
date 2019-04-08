/**
 * Checkers.java
 * Justin Granofsky, Jerry Yu, Corey, Bill 
 * 2018-05-23
 * Version 1.4
 * A checkers game played against a AI
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Checkers extends JFrame {

    /* Static Variables */
    public static Checkers frame;
    public static Board board;
    public static ArrayList<CheckerPiece> pieces;
    public static int count = 0;
    public static int playerPoints = 0;
    public static int computerPoints = 0;
    /* ---------------- */

    /**
     * Method to change the content on the JFrame
     * @param panel the panel that users will see.
     */
    public static void setContent(JPanel panel) {
        // Set the content pane of the panel.
        frame.setContentPane(panel);
        // Repaint the entire frame to avoid overlapping of panels.
        frame.repaint();
        // Refresh the JFrame.
        frame.revalidate();
    }

    /**
     * Method to get a piece on the board.
     * @param x the horizontal location of the piece.
     * @param y the vertical location of the piece.
     * @return the checker piece at the given location.
     */
    public static CheckerPiece getPiece(int x, int y) {
        // Loop through all the checker pieces
        for (CheckerPiece piece : pieces) {
            // Check if the pieces share the same coordinates.
            if (piece.getX() == x && piece.getY() == y) {
                // If the coordinates are the same, return this piece.
                return piece;
            }
        }
        // If the piece does not exist, return null.
        return null;
    }

    /**
     * Method to create/initialize the game board.
     */
    public static void initializeBoard() {
        // Clear any pieces from the previous game.
        pieces.clear();
        // Loop through the top 5 rows
        for (int i = 0; i < 5; i++) {
            // Loop through the 12 columns
            for (int j = 0; j < 12; j += 2) {
                // Check to see if the row is even
                if (i % 2 == 0 && j == 0) {
                    // If the row is even, start placing pieces one square over.
                    j = 1;
                }
                // Add the checker piece with the proper coordinates to the data array.
                pieces.add(new CheckerPiece(j, i, Color.white));
            }
        }
        // Loop through the bottom 5 rows
        for (int i = 11; i > 6; i--) {
            // Loop through the 12 columns
            for (int j = 0; j < 12; j += 2) {
                // Check to see if the row is even
                if (i % 2 == 0 && j == 0) {
                    // If even, start the pieces one square over.
                    j = 1;
                }
                // Add the pieces to the data array.
                pieces.add(new CheckerPiece(j, i, Color.black));
            }
        }
    }

    /**
     * Method to determine if a certain colour has won.
     * @param color the colour that will be checked for a win.
     * @return true if the colour wins, false if the colour has not won.
     */
    public static boolean didWin(Color color) {
        // Loop through all the checker pieces
        for (CheckerPiece piece : pieces) {
            // Check to see if the colours are not equal
            if (piece.getColor() != color) {
                // If so, a piece exists of the opposite team, therefore they have not won.
                return false;
            }
        }
        // Return true if no opposing piece was found.
        return true;
    }

    /**
     * Constructor for the JFrame, creates size & title.
     */
    public Checkers() {
        // Make the JFrame visible.
        this.setVisible(true);
        // Set the size of the JFrame.
        this.setSize(600, 600);
        // Disable resizing of the screen.
        this.setResizable(false);
        // Tell the program to exit when the JFrame is closed.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create checkers game in the center of the screen.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
                / 2 - this.getSize().height / 2);
        // Set the title of the JFrame.
        this.setTitle("Canadian Checkers");
    }

    /**
     * Method to determine the move the AI should make.
     * @return an array of the moves it can/should make.
     */
    public static String getAIMoves(boolean dodge) {
        // Create a hashmap to relate the checker pieces to the set of moves it has.
        HashMap<CheckerPiece, String> moves = getMoves(Color.white);
        // If there is a move that can take pieces, determine the best move.
        if (moves.size() > 0) {
            // Start from the beginning of the array, and save a variable to hold the best move.
            CheckerPiece max = (CheckerPiece) moves.keySet().toArray()[0];
            // Loop through the entire keyset of checker pieces in the hashmap.
            for (CheckerPiece piece : moves.keySet()) {
                // Check to see if the number of "takes" that a move set has is
                // greater than the max.
                if (count(':', moves.get(piece)) > count(':', moves.get(max))) {
                    // If greater, set the max to be this piece.
                    max = piece;
                }
            }
            // Return the current piece that will be moved, along with the
            // entire move set.
            return "[" + max.getX() + ", " + max.getY() + "] " + moves.get(max);
        } else {
            if(dodge) {
                // Find the best move for black, and try to avoid it.
                HashMap<CheckerPiece, String> blackMoves = getMoves(Color.black);
                if (blackMoves.size() > 0) {
                    // There is a route that black can take to jump pieces.
                    // Start from the beginning of the array, and save a variable to hold the best move.
                    CheckerPiece max = (CheckerPiece) blackMoves.keySet().toArray()[0];
                    // Loop through the entire keyset of checker pieces in the hashmap.
                    for (CheckerPiece piece : blackMoves.keySet()) {
                        // Check to see if the number of "takes" that a move set has is greater than the max.
                        if (count(':', blackMoves.get(piece)) > count(':', blackMoves.get(max))) {
                            // If greater, set the max to be this piece.
                            max = piece;
                        }
                    }
                    // max is the best possible move for black.
                    // Get the piece that will be taken first.
                    String move = blackMoves.get(max);
                    move = move.substring(move.indexOf("{") + 1, move.indexOf("}"));
                    int x = Integer.parseInt(move.substring(0, move.indexOf(",")));
                    int y = Integer.parseInt(move.substring(move.indexOf(",") + 2));
                    // Get the white piece that would be taken.
                    CheckerPiece avoider = getPiece(x, y);
                    // Avoid null pointer exception
                    if (avoider != null) {
                        // Check to see if the piece is able to move.
                        if (!avoider.getPossibleMoves().isEmpty()) {
                            // Get the first possible move that this piece can make.
                            String possibleMove = avoider.getPossibleMoves().get(0);
                            // Return the move that will allow the piece to avoid being taken by the best route.
                            return "[" + avoider.getX() + ", " + avoider.getY() + "] " + possibleMove;
                        }
                    }
                }
            }
            // If no move is available to take, determine the best move to avoid loss.
            ArrayList<CheckerPiece> white = new ArrayList<>();
            // Loop through all the pieces.
            for (CheckerPiece piece : pieces) {
                // Add all the white pieces to an array for sorting.
                if (piece.getColor() == Color.white) {
                    white.add(piece);
                }
            }
            // Create a variable to hold the piece we will start at.
            CheckerPiece piece;
            int noMoves = 0;
            boolean leave = false;
            int size = white.size();
            do {
                // Create a random number to choose which piece the search will start at.
                int num = (int) (Math.random() * white.size());
                // Get the piece at that index in the array.
                piece = white.get(num);
                // Loop while there are no possible moves for that piece.
                if (piece.getPossibleMoves().isEmpty()) {
                    noMoves++;
                    white.remove(num);
                }
                if (noMoves == size) {
                    leave = true;
                }


            } while (piece.getPossibleMoves().isEmpty() && !leave);
            if (noMoves == size) {
                return "LOSE";
            } else {
                // Return that piece and it's most optimal move from the
                // possible moves it has. (index 0).
                return "[" + piece.getX() + ", " + piece.getY() + "] "
                        + piece.getPossibleMoves().get(0);
            }
        }
    }

    /**
     * Method to determine the best route a piece can take to maximize points.
     * @param piece the piece that would like to find a route for.
     * @return a string containing a set of coordinates that the piece must
     * follow to maximize it's points.
     */
    public static String getBestRoute(CheckerPiece piece, int count) {
        // Create a variable to store the piece's previous state of king or not.
        boolean king = piece.isKing();
        // Create a variable to store the set of left route coordinates.
        String leftRoute = "";
        // Create a variable to store the set of right route coordinates.
        String rightRoute = "";
        // Check to see if the piece has to take on the left side.
        if (piece.hasToTake(true) != 4) {
            // Create a variable to store the piece that will be jumped/taken.
            CheckerPiece jump;
            // If the piece that must be taken is on the top left...
            if (piece.hasToTake(true) == 0) {
                // Get the piece that will be jumped.
                jump = getPiece(piece.getX() - 1, piece.getY() - 1);
                // Set the position of the new piece.
                piece.setPosition(piece.getX() - 2, piece.getY() - 2);
            } else {
                // The piece that will be taken is on the bottom left...
                // Get the piece that will be jumped
                jump = getPiece(piece.getX() - 1, piece.getY() + 1);
                // Set the position of the piece to the new position.
                piece.setPosition(piece.getX() - 2, piece.getY() + 2);
            }
            // Ensure that the piece being jumped does exist.
            if (jump != null) {
                // Set the piece to be temporarily removed to avoid infinite recursive calls.
                jump.setRemoved(true);
                if (count == 1) {
                    leftRoute += "{" + jump.getX() + ", " + jump.getY() + "} ";
                }
            }
            // Add the coordinate of the move to the left route.
            leftRoute += piece.getX() + ":" + piece.getY() + " ";
            // Check to see if the king state has changed due to it's new move.
            // This prevents the piece from taking more after becoming the king.
            if (piece.isKing() == king) {
                // If the king state has not changed, continue looking for moves.
                leftRoute += getBestRoute(piece, count + 1);
            }else{
             piece.setKing(false);
            }
        } else if (piece.hasToTake(false) != 4) {
            // If the piece has to take from the right...
            // Create a variable to store the jumped piece.
            CheckerPiece jump;
            // If the jump occurs on the top right...
            if (piece.hasToTake(false) == 1) {
                // Get the jumped piece.
                jump = getPiece(piece.getX() + 1, piece.getY() - 1);
                // Set the position of the moving piece to it's new coordinates.
                piece.setPosition(piece.getX() + 2, piece.getY() - 2);
            } else {
                // If the jump occurs on the bottom right...
                // Get the jumped piece
                jump = getPiece(piece.getX() + 1, piece.getY() + 1);
                // Set the new position of the piece being moved.
                piece.setPosition(piece.getX() + 2, piece.getY() + 2);
            }
            // Ensure that the piece being jumped has been defined, and is on the board.
            if (jump != null) {
                // Temporarily remove the jumped piece to avoid infinite recursive calls.
                jump.setRemoved(true);
                if (count == 1) {
                    rightRoute += "{" + jump.getX() + ", " + jump.getY() + "} ";
                }
            }
            // Add the coordinate to the right route.
            rightRoute += piece.getX() + ":" + piece.getY() + " ";
            // Check to see if the king state has changed after this move.
            if (piece.isKing() == king) {
                // If no king state has changed, continue to search for more
                // possible jumps.
                rightRoute += getBestRoute(piece, count + 1);
            }else{
             piece.setKing(false);
            }
        } else {
            // If no more jumps are possible, return no coordinate.
            return "";
        }
        // Compare the left route vs the right route to determine which has more jumps.
        if (count(':', leftRoute) > count(':', rightRoute)) {
            // If left has more jumps than right, return left route as the best route.
            return leftRoute;
        } else {
            // If right has more jumps than left, return right route as the best route.
            return rightRoute;
        }
    }

    /**
     * Method to get all the possible moves that a certain colour has to take.
     * @param color the colour that will be checked.
     * @return a hash map of checker pieces and strings (move sets)
     */
    public static HashMap<CheckerPiece, String> getMoves(Color color) {
        // Create a hash map to hold all the different move sets.
        HashMap<CheckerPiece, String> moves = new HashMap<>();
        // Loop through all the checker pieces
        for (CheckerPiece piece : pieces) {
            // Ensure that the piece is the colour from parameters
            if (piece.getColor() == color) {
                // Check if the piece has to take or not.
                if (piece.hasToTake(true) != 4 || piece.hasToTake(false) != 4) {
                    // Save the initial coordinates of the piece, since they will be altered during theoretical gameplay.
                    int x = piece.getX();
                    int y = piece.getY();
                    // Enter the piece along with the best route for that piece into the hash map.
                    moves.put(piece, getBestRoute(piece, 1));
                    // Reset the position of the piece to it's initial position.
                    piece.setPosition(x, y);
                    // Loop through all the pieces
                    for (CheckerPiece p : pieces) {
                        // Set the pieces to be not removed.
                        p.setRemoved(false);
                    }
                }
            }
        }
        // Return the hash map of moves.
        return moves;
    }

    /**
     * Method to count the number of occurrences a character has in a string.
     * @param key the character that will be searched and counted.
     * @param str the string that will be looped through
     * @return the number of times the key was found in the string given.
     */
    public static int count(char key, String str) {
        // Create a variable to store the count.
        int count = 0;
        // Loop through the entire string
        for (int i = 0; i < str.length(); i++) {
            // Get the character at the index, and compare to the key.
            if (str.charAt(i) == key) {
                // If the characters match, add one to the count.
                count++;
            }
        }
        // Return the count of the key.
        return count;
    }

    public static void main(String[] args) {
        // Create an instance of the JFrame to be accessed from other classes.
        frame = new Checkers();
        // Create the array to hold all the data for checker pieces.
        pieces = new ArrayList<>();
        // Create the game board.
        initializeBoard();
        // Show users to the main menu screen.
        setContent(new Menu());
    }

}
