/**
 * Menu.java
 * Justin Granofsky & Jerry Yu
 * 2018-05-24
 * Version 1.0
 * Handles creating the menu panel.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel implements ActionListener, MouseListener {

    /**
     * Main menu constructor to make the panel and add components to it.
     */
    public Menu(){
        // Set the layout of the main menu
        setLayout(new GridBagLayout());
        // Create constraints to handle position of the components.
        GridBagConstraints gc = new GridBagConstraints();
        // Set the starting constraints.
        gc.gridx = 0;
        gc.gridy = 0;
        // Create insets to separate components.
        gc.insets = new Insets(10, 0, 10, 0);
        // Create a welcome message for the users.
        JLabel msg = new JLabel("<html><center><div style=\"padding: 10px; border-bottom: 3px white solid; color: white;\"><h1 style=\"font-variant: small-caps;\">CANADIAN<br>CHECKERS</h1></div></center></html>");
        // Add the message to the panel.
        add(msg, gc);
        // Create a button for playing the game.
        JButton play = new JButton("<html><div style=\"border-bottom: 3px white solid; color: white;\">Play</div></html>");
        // Set the font of the text on the button and size of text.
        play.setFont(new Font("NewTimesRoman", Font.HANGING_BASELINE, 16));
        /* Remove button formatting */
        play.setBorderPainted(false);
        play.setContentAreaFilled(false);
        play.setFocusPainted(false);
        /* ------------------------ */
        // Add a mouse listener to listen for mouse hovering events.
        play.addMouseListener(this);
        // Add an action listener to listen for clicking.
        play.addActionListener(this);
        // Set the constraints for this component.
        gc.gridy = 1;
        // Add the button to the main menu panel.
        add(play, gc);
        // Create a button for users to close the game.
        JButton exit = new JButton("<html><div style=\"border-bottom: 3px white solid; color: white;\">Exit</div></html>");
        // Set the font of the text on the button
        exit.setFont(new Font("NewTimesRoman", Font.HANGING_BASELINE, 16));
        /* Remove button formatting */
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        /* ------------------------ */
        // Add a mouse listen to check for hover events.
        exit.addMouseListener(this);
        // Add an action listener to check for click events.
        exit.addActionListener(this);
        // Set the constraints to position the button lower on the screen.
        gc.gridy = 2;
        // Add the button the main menu panel.
        add(exit, gc);
    }

    /**
     * Method to paint the background to the screen.
     */
    @Override
    protected void paintComponent(Graphics g){
        try {
            // Try to get the image from the file.
            BufferedImage img = ImageIO.read(new File("back.jpg"));
            // Draw the image to the screen.
            g.drawImage(img, 0, 0, 600, 600, Color.white, null);
        }catch(IOException e){}
    }

    /**
     * Method to get the clicked buttons and act on the clicks.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Make sure that the source/component being clicked is a JButton.
        if(e.getSource() instanceof JButton){
            // Cast the source to a JButton.
            JButton button = (JButton) e.getSource();
            // Check if the button is the play button.
            if(button.getText().contains("Play")){
                // Create a variable to store the new game board.
                Checkers.board = new Board();
                // Set the users to see the checker board.
                Checkers.setContent(Checkers.board);
            }else{
                // If the button is the exit, close the game.
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Method to change the colour of the button when hovered over.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Make sure the source is a JButton
        if(e.getSource() instanceof JButton){
            // Cast the source to be a JButton
            JButton button = (JButton) e.getSource();
            // Change the colour of the button
            button.setText(button.getText().replaceAll("white", "green"));
        }
    }

    /**
     * Method to change the colour of the button when it is no longer hovered over.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Check if the source is a JButton
        if(e.getSource() instanceof JButton){
            // Cast the source to a JButton
            JButton button = (JButton) e.getSource();
            // Change the colour of the JButton back to normal.
            button.setText(button.getText().replaceAll("green", "white"));
        }
    }
}
