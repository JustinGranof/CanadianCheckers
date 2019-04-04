/**
 * CheckerPiece.java
 * Justin Granofsky & Jerry Yu
 * 2018-05-23
 * Version 1.0
 * Checker piece object
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CheckerPiece {

    /* Properties for each checker piece */
    private JButton button;
    private Color color;
    private boolean king;
    private boolean removed;
    private int x;
    private int y;

    /**
     * Creates a checker piece of a certain colour.
     * @param x the horizontal position of the checker piece
     * @param y the vertical position of the checker piece
     * @param color the colour of the piece
     */
    public CheckerPiece(int x, int y, Color color) {
        // Set all the variables of the checker piece.
        this.x = x;
        this.king = false;
        this.color = color;
        this.removed = false;
        this.y = y;
        // Create a JButton, and make it hold the coordinates of the piece.
        button = new JButton(x + ":" + y);
        // Make the text too small for users to see.
        button.setFont(new Font("TimesRowman", Font.PLAIN, 1));
        // Format the button - get rid of the background of the button.
        button.setContentAreaFilled(false);
        // Get rid of the border on the button.
        button.setBorderPainted(false);
        // Display focus painting when the button is clicked.
        button.setFocusPainted(false);
        // Set the size of the button to be similar to the piece that will be painted.
        button.setSize(22, 22);
        // Add the action listener to catch when a user clicks a piece.
        button.addActionListener(new PieceClicking());
    }

    /**
     * Method to get the horizontal position of the checker piece.
     * @return the x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Method to get the vertical position of the checker piece.
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Method to get the colour of the checker piece
     * @return the colour of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method to get the button that is connected to the checker piece
     * @return the button
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Method to determine all the possible moves that a piece can take.
     * @return an array list of coordinates that the piece can move to.
     */
    public ArrayList<String> getPossibleMoves() {
        // Create an array list to store the moves.
        ArrayList<String> moves = new ArrayList<>();
        // If the piece is a king, or the colour is black check for moving upwards.
        if (isKing() || getColor() == Color.black) {

            /* [Checks Upper Right Corner] */
            int x = getX() + 1;
            int y = getY() - 1;
            // Ensure that the piece on the top right is in range of the board.
            if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                // Get the piece at the upper right corner.
                if (Checkers.getPiece(x, y) == null) {
                    // If the piece is open, add this as a possible move.
                    moves.add(x + ":" + y);
                } else {
                    // If there is a piece there, get that piece.
                    CheckerPiece piece = Checkers.getPiece(x, y);
                    // If the piece is the opposite colour, check for jumping.
                    if (piece.getColor() != getColor()) {
                        // move to upper right corner of the piece that it could take.
                        x += 1;
                        y -= 1;
                        // Check if the piece is on the board.
                        if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                            if (Checkers.getPiece(x, y) == null) {
                                moves.add(x + ":" + y);
                            }
                        }
                    }
                }
            }
            /* --------------------------- */

            /* [Checks Upper Left Corner] */
            x = getX() - 1;
            y = getY() - 1;
            // Make sure that the piece is within the board.
            if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                // Check to see if the spot on the board is empty
                if (Checkers.getPiece(x, y) == null) {
                    // If the spot is empty, add this spot as a possible move.
                    moves.add(x + ":" + y);
                } else {
                    // Get the piece at that location if it is not empty.
                    CheckerPiece piece = Checkers.getPiece(x, y);
                    // Check if the colour is opposite to this current piece.
                    if (piece.getColor() != getColor()) {
                        // If opposite, check if the piece can be stolen.
                        x -= 1;
                        y -= 1;
                        // Make sure that the piece is within the bounds of the board.
                        if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                            // Check to see if the spot across from the piece is empty.
                            if (Checkers.getPiece(x, y) == null) {
                                // If it's not empty, add that spot as a coordinate for possible moves.
                                moves.add(x + ":" + y);
                            }
                        }
                    }
                }
            }
            /* --------------------------- */
        }

        /* [Checks Bottom Left Corner] */
        if (isKing() || getColor() == Color.white) {
            // Move to the bottom left position from the current piece.
            int x = getX() - 1;
            int y = getY() + 1;
            // Make sure the piece is within the bounds of the board.
            if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                // Check if the spot is empty.
                if (Checkers.getPiece(x, y) == null) {
                    // If empty, add this spot as a possible move.
                    moves.add(x + ":" + y);
                } else {
                    // If the spot is not empty, check if it's the opposing team's piece.
                    CheckerPiece piece = Checkers.getPiece(x, y);
                    if (piece.getColor() != getColor()) {
                        // Move to the next position to check if it can take.
                        x -= 1;
                        y += 1;
                        // Make sure that the new position is on the board.
                        if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                            // Check to see if the new position has an empty spot.
                            if (Checkers.getPiece(x, y) == null) {
                                // If the spot is empty, add the spot as a possible move.
                                moves.add(x + ":" + y);
                            }
                        }
                    }
                }
            }
            /* -------------------- */


            /* [Checks Bottom Right Corner] */
            x = getX() + 1;
            y = getY() + 1;
            // Make sure the new position is within bounds of the board.
            if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                // Check to see if the position is empty.
                if (Checkers.getPiece(x, y) == null) {
                    // If empty, add this position as a possible move.
                    moves.add(x + ":" + y);
                } else {
                    // if the piece is not empty, check if it's the opposing team's piece.
                    CheckerPiece piece = Checkers.getPiece(x, y);
                    if (piece.getColor() != getColor()) {
                        // Move to the new position.
                        x += 1;
                        y += 1;
                        // Ensure position is within bounds of the board.
                        if (x < 12 && x >= 0 && y < 12 && y >= 0) {
                            // Check if the spot is empty.
                            if (Checkers.getPiece(x, y) == null) {
                                // If empty, add the move as a possible move.
                                moves.add(x + ":" + y);
                            }
                        }
                    }
                }
            }
            /* -------------------- */
        }

        // Return all the possible moves.
        return moves;
    }

    /**
     * Method to change the position of the checker piece.
     * @param x the horizontal location of the checker piece
     * @param y the vertical locaation of the checker piece.
     */
    public void setPosition(int x, int y) {
        // The current x and y positions
        this.x = x;
        this.y = y;
        // Check to see if the piece has reached the other side.
        if (getColor() == Color.white && y == 11) {
            // Set the piece to become a king.
            setKing(true);
        }
        // Check to see if the piece has reached the other side of the board.
        if (getColor() == Color.black && y == 0) {
            // Set the piece to be a king.
            setKing(true);
        }
    }

    /**
     * Method to move this checker piece to a new location on the board.
     * @param x the new horizontal position.
     * @param y the new vertical position.
     * @return a boolean of whether or not a piece was taken.
     */
    public boolean move(int x, int y) {
        // Create a variable to store whether a take occurred.
        boolean didTake = false;
        // Save the x and y before the piece is moved.
        int oldX = this.x;
        int oldY = this.y;
        // Set the coordinate of the piece to the new coordinates.
        this.x = x;
        this.y = y;
        // Change the text on the button.
        this.button.setText(x + ":" + y);
        // If the piece moves to the end of the board, set the piece to be king.
        if (getColor() == Color.white && y == 11) {
            // Make the piece a king.
            setKing(true);
        }
        // If the piece moves to the end of the board, set the piece to be king.
        if (getColor() == Color.black && y == 0) {
            // Make the piece a king.
            setKing(true);
        }

        // Check to see if the player took a piece (upwards)
        if ((oldY - y) == 2) {
            // Create a variable to store the piece that was taken.
            CheckerPiece take;
            // If the x is greater than the previous x, then the player took from the right.
            if (x > oldX) {
                // Get the piece on the top right of the old position
                take = Checkers.getPiece(oldX + 1, oldY - 1);
            } else {
                // Get the piece on the top left of the old position
                take = Checkers.getPiece(oldX - 1, oldY - 1);
            }
            // Avoid null pointer exceptions.
            if (take != null && take.getButton() != null) {
                // Remove the taken piece's button from the panel.
                Checkers.board.remove(take.getButton());
                // Remove the taken piece from the board.
                Checkers.pieces.remove(take);
                // Add the points to the proper team.
                int pointAmount = 1;
                // Check if the piece was a king
                if(take.isKing()){
                	// Add points if it was a king.
                	pointAmount = 5;
                }
                // if the white team took a piece
                if(getColor() == Color.white){
                	// Add the points to the computer
                	Checkers.computerPoints += pointAmount;
                }else{
                	// Add the points to the player
                	Checkers.playerPoints += pointAmount;
                }
            }
            // Save that a piece was taken.
            didTake = true;
        } else if ((oldY - y) == -2) {
            // Create a variable to store the piece that was taken.
            CheckerPiece take;
            // If the x is greater than the new x, then the player took from the bottom right.
            if (x > oldX) {
                // Get the piece on the bottom right.
                take = Checkers.getPiece(oldX + 1, oldY + 1);
            } else {
                // Get the piece on the bottom left.
                take = Checkers.getPiece(oldX - 1, oldY + 1);
            }
            // Avoid null pointer exceptions
            if (take != null && take.getButton() != null) {
                // Remove the taken piece's button from the panel
                Checkers.board.remove(take.getButton());
                // Remove the checker piece from the board.
                Checkers.pieces.remove(take);
             // Add the points to the proper team.
                int pointAmount = 1;
                // Check if the piece was a king
                if(take.isKing()){
                	// Add points if it was a king.
                	pointAmount = 5;
                }
                // if the white team took a piece
                if(getColor() == Color.white){
                	// Add the points to the computer
                	Checkers.computerPoints += pointAmount;
                }else{
                	// Add the points to the player
                	Checkers.playerPoints += pointAmount;
                }
            }
            // Save that a piece was taken.
            didTake = true;
        }
        // Clear all the possible moves on the screen.
        Checkers.board.clearMoves();
        // Repaint the board with the updated checker pieces.
        Checkers.board.repaint();
        // Return whether or not a piece was taken.
        return didTake;
    }

    /**
     * Determines if a piece has to take or not.
     * @return true if the piece can take, false if not.
     */
    public int hasToTake(boolean left) {
        // Check if the piece is black or a king
        if (getColor() == Color.black || isKing()) {
            // Check top right take ability.
            // If the method needs to check the left, skip checking the right.
            if (!left) {
                // Make sure that the piece on the top right is within the bounds of the board.
                if ((getX() + 1) < 12 && (getY() - 1) >= 0) {
                    // Get the piece at the top right.
                    CheckerPiece piece = Checkers.getPiece((getX() + 1), (getY() - 1));
                    // Make sure that the piece is a different colour, and is not already removed.
                    if (piece != null && piece.getColor() != getColor() && !piece.isRemoved()) {
                        // Make sure the position on the top right of the piece being jumped is not taken.
                        if ((piece.getX() + 1) < 12 && (piece.getY() - 1) >= 0) {
                            // Make sure the spot is empty.
                            if (Checkers.getPiece((piece.getX() + 1), (piece.getY() - 1)) == null) {
                                // Return 1, meaning that the piece has to take the top right piece.
                                return 1; // TOP RIGHT
                            }
                        }
                    }
                }
            }
            // Checks if the method needs to check the left ability.
            if (left) {
                // Check top left take ability
                // Makes sure the piece on the top left is within the bounds of the board.
                if ((getX() - 1) >= 0 && (getY() - 1) >= 0) {
                    // Get the piece at the top left of this piece.
                    CheckerPiece piece = Checkers.getPiece((getX() - 1), (getY() - 1));
                    // Check if the piece is of different colour, and not already removed.
                    if (piece != null && piece.getColor() != getColor() && !piece.isRemoved()) {
                        // Check to see if the piece is within the bounds of the board.
                        if ((piece.getX() - 1) >= 0 && (piece.getY() - 1) >= 0) {
                            // Make sure there is no piece, and that this piece can jump.
                            if (Checkers.getPiece((piece.getX() - 1), (piece.getY() - 1)) == null) {
                                // Return 0, indicating that this piece can take the top left piece.
                                return 0; // TOP LEFT
                            }
                        }
                    }
                }
            }
        }
        // Check to see if the piece is white, or is a king.
        if (getColor() == Color.white || isKing()) {
            // Checks bottom left ability
            // Check if the method needs to look at right side takes or not.
            if (left) {
                // Ensure that the new position is within the bounds of the board.
                if ((getX() - 1) >= 0 && (getY() + 1) < 12) {
                    // Get the piece that could be jumped.
                    CheckerPiece piece = Checkers.getPiece((getX() - 1), (getY() + 1));
                    // Make sure the piece being jumped is of opposite colour and not already jumped.
                    if (piece != null && piece.getColor() != getColor() && !piece.isRemoved()) {
                        // Make sure the piece across from the one being jumped is within bounds of the board.
                        if ((piece.getX() - 1) >= 0 && (piece.getY() + 1) < 12) {
                            // Make sure that the piece being jumped has a slot open for this piece to move to.
                            if (Checkers.getPiece((piece.getX() - 1), (piece.getY() + 1)) == null) {
                                // Return 2 indicating that the piece can take the bottom left.
                                return 2; // BOTTOM LEFT
                            }
                        }
                    }
                }
            }

            // Checks bottom right take ability
            // Checks if the method needs to check the left side.
            if (!left) {
                // Ensures that the new position is within bounds of the board.
                if ((getX() + 1) < 12 && (getY() + 1) < 12) {
                    // Get the piece that could be jumped.
                    CheckerPiece piece = Checkers.getPiece(getX() + 1, getY() + 1);
                    // Make sure the jumped piece is of opposite colour and not already removed.
                    if (piece != null && piece.getColor() != getColor() && !piece.isRemoved()) {
                        // Make sure next position is within bounds.
                        if ((piece.getX() + 1) < 12 && (piece.getY() + 1) < 12) {
                            // Check if there is a spot open for this piece to jump to.
                            if (Checkers.getPiece((piece.getX() + 1), (piece.getY() + 1)) == null) {
                                // Return 3 indicating that it can jump the bottom right piece.
                                return 3; // BOTTOM RIGHT
                            }
                        }

                    }
                }
            }
        }
        // Return 4 indicating that the piece cannot jump/take at all.
        return 4; // CANNOT TAKE
    }

    /**
     * Method to check if a piece is king or not.
     * @return true if the piece is a king, false if not.
     */
    public boolean isKing() {
        return this.king;
    }

    /**
     * Method to set the piece to be or not to be a king.
     * @param king true if the piece should be a king, false if not.
     */
    public void setKing(boolean king) {
        this.king = king;
    }

    /**
     * Method to check if the piece was temporarily removed.
     * @return true if the piece is removed, false if it's not.
     */
    public boolean isRemoved() {
        return this.removed;
    }

    /**
     * Method to set a piece to be removed or not.
     * @param removed whether the piece is removed or not.
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

}
