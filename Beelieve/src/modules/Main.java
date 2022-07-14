package modules;

import attributes.ResolutionManager;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

/*
This is the main class. It only initializes the game class.
Beelieve has a hierarchy of modules and a few assistant classes.
                               |GAME|
                      /          |          \
              |MAP ENGINE|      |PLAYER|   |SHOP|
          /        |       \
|background| |environment| |hive|

I hope this tree helps.
Pretty much, the player has to talk to the map engine in order to know whats going on the game.
The shop to buy items is entirely separate from the map but can still draw information from the map.
The map is in charge of polling everything it holds a list of all the objects it contains.

 */

//Declaring the main class.
public class Main extends Application {
    //Declaring the main game class. Only one is needed per game.
    public Game myGame;
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final JFrame frame = new JFrame("Beelieve");
        myGame = new Game();

        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);

        //frame.setShape(ResolutionManager.getShape().get());

        final JFXPanel fxPanel = new JFXPanel();
        final ToolBar exit = new ToolBar(frame);

        fxPanel.setScene(myGame);

        frame.add(fxPanel);
        frame.add(exit);

        fxPanel.setSize(ResolutionManager.getWidth().get(), ResolutionManager.getHeight().get());
        fxPanel.setLocation(0,25);

        exit.setSize(100,25);
        exit.setLocation(ResolutionManager.getWidth().get() - 100, 0);

        frame.setSize(ResolutionManager.getWidth().get(), ResolutionManager.getHeight().get() + 50);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocation(50, 50);

        frame.setVisible(true);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
