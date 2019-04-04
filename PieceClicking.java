/**
 * PieceClicking.java
 * Justin Granofsky & Jerry Yu
 * 2018-05-24
 * Version 1.0
 * Handles displaying possible moves
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieceClicking implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// Make sure that the component being clicked is a JButton
		if (e.getSource() instanceof JButton) {
			// Cast the source to a JButton
			JButton button = (JButton) e.getSource();
			// Get the x coordinate from the button's text.
			int x = Integer.parseInt(button.getText().substring(0,
					button.getText().indexOf(":")));
			// Get the y coordinate from the button's text.
			int y = Integer.parseInt(button.getText().substring(
					button.getText().indexOf(":") + 1));
			// Get the piece that was clicked.
			CheckerPiece piece = Checkers.getPiece(x, y);
			// Make sure the piece exists.
			if (piece == null) {
				return;
			}
			// If the piece is already selected, don't repaint possible moves.
			if (piece.equals(Board.selected)) {
				return;
			}
			// Set the selected piece to be the clicked piece.
			Board.selected = piece;
			// If the possible moves aren't empty...
			if (!piece.getPossibleMoves().isEmpty())
				// Draw all possible moves for the given checker piece.
				Checkers.board.drawPossibleMoves(Checkers.board.getGraphics(),
						piece);
		}
	}

}
