/**
 * Board.java
 * Justin Granofsky & Jerry Yu
 * 2018-05-24
 * Version 1.0
 * Creates the board and all the pieces.
 */

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel {

	// Variable to hold the currently selected checker piece.
	public static CheckerPiece selected;
	// List to hold all the possible moves.
	public static ArrayList<JButton> moves = new ArrayList<>();

	/**
	 * Constructor for the board panel.
	 */
	public Board() {
		// Set a null layout so that absolute values can be used.
		setLayout(null);
		// Loop through all the pieces
		for (CheckerPiece piece : Checkers.pieces) {
			// If the piece is black...
			if (piece.getColor() == Color.black) {
				// Add the pieces button to the board panel.
				add(piece.getButton());
			}
		}
	}

	/**
	 * Method to call the draw board method.
	 * @param g the graphics of the panel.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		drawBoard(g);
	}

	/**
	 * Method to draw all the pieces to the board.
	 * @param g the graphics of the board
	 */
	public void drawBoard(Graphics g) {
		// Loop through all 12 rows of the board.
		for (int row = 0; row < 12; row++) {
			// Loop through all 12 columns
			for (int col = 0; col < 12; col++) {
				// If the column and row are even...
				if (col % 2 == row % 2) {
					// Set the colour to be a light colour.
					g.setColor(new Color(119, 95, 62));
				} else {
					// Set the colour to be a dark colour.
					g.setColor(new Color(66, 50, 27));
				}
				// Fill a rectangle at the coordinate with the proper colour.
				g.fillRect(0 + 49 * col, 0 + 47 * row, 65, 65);
			}
		}
		// Loop through all the checker pieces
		for (CheckerPiece piece : Checkers.pieces) {
			// Set the colour of the graphics to be the colour of the piece.
			g.setColor(piece.getColor());
			// Find the x and y position on the panel for the piece to go.
			int x = 13 + 49 * piece.getX();
			int y = 12 + 47 * piece.getY();
			// Draw an oval at the position found.
			g.fillOval(x, y, 25, 25);
			// Set the location of the button to be the location of the piece.
			piece.getButton().setLocation(x, y);
			// If the piece is a king, draw the crown.
			if(piece.isKing()){
				try{
					// Get the image from the file.
					BufferedImage img = ImageIO.read(Board.class.getResource("crown.png"));
					// Draw the crown on top of the piece.
					g.drawImage(img, x+3, y+5, 20, 15, null);
				}catch(IOException e){}
			}
		}
	}

	/**
	 * Method to draw all possible moves for a given piece to the board panel.
	 * @param g the graphics of the board
	 * @param piece the piece that will have possible moves draw
	 */
	public void drawPossibleMoves(Graphics g, CheckerPiece piece) {
		// Clear any old possible moves from the board.
		clearMoves();
		// Draw the board again.
		drawBoard(g);
		// Loop through all the possible moves for the piece.
		for (String move : piece.getPossibleMoves()) {
			// Get the x and y coordinates of the possible move.
			int x = Integer.parseInt(move.substring(0, move.indexOf(":")));
			int y = Integer.parseInt(move.substring(move.indexOf(":") + 1));
			// Create variables to save where the possible move will be drawn to.
			int xNew = 13 + 49 * x;
			int yNew = 12 + 47 * y;
			// Create a JButton to show the move. Set the text to be the coordinates.
			JButton moveButton = new JButton(x + ":" + y);
			// Add the button to the board.
			Checkers.board.add(moveButton);
			// Set the button to be red.
			moveButton.setBackground(Color.red);
			moveButton.setForeground(Color.red);
			// Add the action listener for move clicking to the button.
			moveButton.addActionListener(new MoveClick());
			// Set the size of the button.
			moveButton.setSize(22, 22);
			/* Remove button formatting */
			moveButton.setBorderPainted(false);
			moveButton.setFocusable(false);
			moveButton.setFocusPainted(false);
			/* ------------------------ */
			// Set the location of the button.
			moveButton.setLocation(xNew, yNew);
			// Add the button to the list of possible moves.
			moves.add(moveButton);
		}
	}

	/**
	 * Method to remove all possible moves from the board.
	 */
	public static void clearMoves() {
		// Loop through all possible move buttons.
		for (JButton jb : moves) {
			// Remove the button from the board panel.
			Checkers.board.remove(jb);
		}
	}

}
