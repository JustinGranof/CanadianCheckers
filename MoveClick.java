/**
 * MoveClick.java
 * Justin Granofsky & Jerry Yu
 * 2018-05-24
 * Version 1.0
 * Handles when a move is done by the player or AI
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MoveClick implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
    	// Make sure that the component being clicked is a JButton
        if (e.getSource() instanceof JButton) {
        	// Cast the source to a JButton
            JButton button = (JButton) e.getSource();
            // Get the x value of the clicked position.
            int x = Integer.parseInt(button.getText().substring(0,
                    button.getText().indexOf(":")));
            // Get the y value of the clicked position.
            int y = Integer.parseInt(button.getText().substring(
                    button.getText().indexOf(":") + 1));
            // Save the previous state of if the piece was a king.
            boolean king = Board.selected.isKing();
            // Move the piece, and save if the piece jumped or not.
            boolean take = Board.selected.move(x, y);
            // If the piece did not jump...
            if (!take) {
            	// Add one to the count of moves without jumps.
                Checkers.count++;
            } else {
            	// Reset the count of moves without jumps.
                Checkers.count = 0;
            }
            // Check if the black piece won.
            if (Checkers.didWin(Color.black)) {
                // Show the user that the player has won the game.
                JOptionPane.showMessageDialog(null, "Player wins!");
                // Reset the entire game
                Checkers.setContent(new Menu());
                Checkers.initializeBoard();
                // Return to stop AI from playing a move.
                return;
            }
            // If the piece did not become a king check if it can continue to take.
            if (king == Board.selected.isKing()) {
            	// If the piece did take
                if (take) {
                	// If the piece can still take a piece
                    if (Board.selected.hasToTake(true) != 4
                            || Board.selected.hasToTake(false) != 4
                            && !Board.selected.getPossibleMoves().isEmpty()) {
                    	// Draw the possible moves for the piece.
                        Checkers.board.drawPossibleMoves(
                                Checkers.board.getGraphics(), Board.selected);
                        // Return so the AI does not make a move.
                        return;
                    }
                }
            }
            // Remove the selected piece.
            Board.selected = null;
            // Get the best set of moves that the AI should use.
            // NOTE: Having the parameter to true will tell the AI to try and dodge being taken.
            String aiMoves = Checkers.getAIMoves(true);
            // If the AI has no more possible moves, so the player wins.
            if (aiMoves.equalsIgnoreCase("LOSE")) {
                // Display that the player won.
                JOptionPane.showMessageDialog(null, "Player wins!\nNo more possible moves.");
                // Set the user to see the main menu again.
                Checkers.setContent(new Menu());
                // Reset the entire game board.
                Checkers.initializeBoard();
                return;
            }

            // Get the piece that the AI is going to move.
            if (!aiMoves.isEmpty() && Checkers.count < 20) {
            	// Get the piece that is going to be moved.
                String movePiece = aiMoves.substring(aiMoves.indexOf("[") + 1,
                        aiMoves.indexOf("]"));
                // Get the x of the piece being moved.
                int moveX = Integer.parseInt(movePiece.substring(0,
                        movePiece.indexOf(",")));
                // Get the y of the piece being moved.
                int moveY = Integer.parseInt(movePiece.substring(movePiece
                        .indexOf(",") + 2));
                // Get the checker piece object from the coordinates.
                CheckerPiece aiPiece = Checkers.getPiece(moveX, moveY);
                // If the piece takes substring differently.
                if (aiMoves.indexOf("}") != -1) {
                    aiMoves = aiMoves.substring(aiMoves.indexOf("}") + 2);
                } else {
                    aiMoves = aiMoves.substring(aiMoves.indexOf("]") + 2);
                }
                // Print out the piece that the computer will be moving.
                System.out.println("Computer Moves (" + aiPiece.getX() + ", "
                        + aiPiece.getY() + ") to:");
                // Make a variable to store if the computer should still be moving.
                boolean look = true;
                // Loop through all the coordinates that the piece must move.
                for (String coord : aiMoves.split(" ")) {
                	// If the AI should move, get the next position.
                    if (look) {
                    	// Get the x of the new position.
                        int newX = Integer.parseInt(coord.substring(0,
                                coord.indexOf(":")));
                        // Get the y of the new position.
                        int newY = Integer.parseInt(coord.substring(coord
                                .indexOf(":") + 1));
                        // Save whether or not the AI took a piece.
                        boolean aiTake = aiPiece.move(newX, newY);
                        // If the AI did not take a piece...
                        if (!aiTake) {
                        	// Add to the count of moves without jumps.
                            Checkers.count++;
                            if(Checkers.count == 20){
                            	// Tell the AI to stop moving since the game has ended.
                                look = false;
                            }
                        } else {
                        	// The AI did take a piece, so reset the count.
                            Checkers.count = 0;
                        }
                        // Print out the coordinate that the piece will move to.
                        System.out.println("> (" + newX + ", " + newY + ")");
                    }
                }
                // Print out an empty line to separate moves.
                System.out.println();
                // Check if the AI has won the game.
                if (Checkers.didWin(Color.white)) {
                    // Display that the computer won the game.
                    JOptionPane.showMessageDialog(null, "Computer wins!");
                    // Reset the game board.
                    Checkers.setContent(new Menu());
                    Checkers.initializeBoard();
                }
            }
            // Checks if the count with no moves reachs 20.
            if(Checkers.count == 20){
                if(Checkers.computerPoints > Checkers.playerPoints){
                    // Computer wins.
                    JOptionPane.showMessageDialog(null, "Computer Wins!\n20 moves happened\n without any taking.\nComputer has more points.");
                }else if(Checkers.computerPoints < Checkers.playerPoints){
                    // Player wins
                    JOptionPane.showMessageDialog(null, "Player Wins!\n20 moves happened\n without any taking.\nPlayer has more points.");
                }else{
                    // Tie game
                    JOptionPane.showMessageDialog(null, "Tie Game!\n20 moves happened\n without any taking.\nBoth sides had the same points.");
                }
                // Reset the game
                Checkers.setContent(new Menu());
                // Reset the game board
                Checkers.initializeBoard();
            }
        }
    }

}
