package attributes;

import javafx.beans.property.*;
import modules.Game;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by William on Dec-09-2014.
 */
/*time: 10:23*/
//This class is an extension of the game class. It is required if scaling or resolution changes are desired.
//It contains a reference to every object that is drawn.
public class ResolutionManager {

    //All drawn objects:
    public Game myGame;

    public static String state = "uninitialized";

    public static int widthHigh = 1600;
    public static int heightHigh = 1000;
    public static int gridHigh = 50;

    public static String textStyle[] = new String[5];

    private static ReadOnlyIntegerWrapper grid = new ReadOnlyIntegerWrapper(50);
    private static ReadOnlyIntegerWrapper width = new ReadOnlyIntegerWrapper(0);
    private static ReadOnlyIntegerWrapper height = new ReadOnlyIntegerWrapper(0);
    private static ReadOnlyDoubleWrapper scale = new ReadOnlyDoubleWrapper(1);
    private static ReadOnlyStringWrapper buttonStyle = new ReadOnlyStringWrapper();
    private static ReadOnlyObjectWrapper<Polygon> screenShape = new ReadOnlyObjectWrapper<Polygon>();

    public ResolutionManager(Game thisGame){
        myGame = thisGame;
    }
    public static void changeResolution(int inputWidth, int inputHeight) {
        Polygon shape = new Polygon();
        shape.addPoint(0, 25);
        shape.addPoint(inputWidth - 100, 25);
        shape.addPoint(inputWidth - 100, 0);
        shape.addPoint(inputWidth, 0);
        shape.addPoint(inputWidth, inputHeight + 25);
        shape.addPoint(0, inputHeight + 25);
        scale.set(inputWidth / (double) (widthHigh));
        screenShape.set(shape);
        width.set(inputWidth);
        height.set(inputHeight);
        grid.set((int) (gridHigh * scale.get()));
        double textSize = 24.0;
        buttonStyle.set(" -fx-padding: 5 22 5 22;   \n" +
                "    -fx-border-color: #e2e2e2;\n" +
                "    -fx-border-width: 2;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-background-color: #6d6d6d;\n" +
                "    -fx-font-family: \"Segoe UI\", Helvetica, Arial, sans-serif;\n" +
                "    -fx-font-size: " + Double.toString(textSize * scale.get()) + "pt;\n" +
                "    -fx-text-fill: #d8d8d8;\n" +
                "    -fx-background-insets: 0 0 0 0, 0, 1, 2;");
        textStyle[0] = ("-fx-font-size: " + Double.toString(textSize * scale.get()) + "pt;\n" +
                "    -fx-font-family: \"Cambri\", Helvetica, Arial, sans-serif;\n");
        textStyle[1] = "-fx-font-family:  'Hoefler Text', Georgia, 'Times New Roman', serif;\n" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: "+ Double.toString(textSize * scale.get()) + "pt;\n" +
                "-fx-text-transform: uppercase;" +
                "-fx-background-color: #CCCCCC;";
    }
    public static void updateResolution(){
        updateResolution(Toolkit.getDefaultToolkit().getScreenSize());
    }
    public static void updateResolution(Dimension input){
        if(input == null){
            input = Toolkit.getDefaultToolkit().getScreenSize();
        }
        Dimension screenSize = input;
        double desiredWidth = screenSize.getWidth()*.75;
        double desiredHeight = screenSize.getHeight()*.75;
        if(desiredHeight > desiredWidth){
            desiredHeight = (double)(heightHigh)*desiredWidth/(double)(widthHigh);
        }else{
            desiredWidth = (double)(widthHigh)*desiredHeight/(double)(heightHigh);
        }

        changeResolution((int) desiredWidth, (int) (desiredHeight));
    }
    public static ReadOnlyIntegerProperty getGrid(){
        return grid.getReadOnlyProperty();
    }
    public static ReadOnlyIntegerProperty getWidth(){ return width.getReadOnlyProperty();}
    public static ReadOnlyIntegerProperty getHeight(){return height.getReadOnlyProperty();}
    public static ReadOnlyDoubleProperty getScale(){return scale.getReadOnlyProperty();}
    public static ReadOnlyObjectProperty<Polygon> getShape(){
        return screenShape.getReadOnlyProperty();
    }
    public static ReadOnlyStringProperty getButtonStyle() { return buttonStyle.getReadOnlyProperty();}
}
