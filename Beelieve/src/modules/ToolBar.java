package modules;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

/**
 * Created by William on Nov-02-2014.
 */
//This is the button on top right part of the game that is detached from the game itself.
public class ToolBar extends JFXPanel{
    private ImageView image = new ImageView(new Image("assets/exitButton.png"));

    private int initialClickX = 0;
    private int initialClickY = 0;
    private boolean moving = false;
    public ToolBar(final JFrame exitThis){
        Group temp = new Group();
        temp.getChildren().add(image);
        this.setScene(new Scene(temp, 100, 25));
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                exitThis.dispose();
                System.exit(0);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                initialClickX = (int) (e.getX());
                initialClickY = (int) (e.getY());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                // get location of Window
                int thisX = exitThis.getLocation().x;
                int thisY = exitThis.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = (int) (thisX + e.getX()) - (thisX + initialClickX);
                int yMoved = (int) (thisY + e.getY()) - (thisY + initialClickY);

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                exitThis.setLocation(X, Y);
            }

            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {

            }
        });
    }
}
