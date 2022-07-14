package attributes;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by William on Oct-30-2014.
 * This class is designed to be the base for any item added to the map.
 * Currently its the parent to: Hive,
 */

public abstract class Item extends Group{
    //Grid is the width & height of every square on the map. Smallest graphical unit.
    protected final int grid;
    protected static int idOut = 1;
    public int id = idOut++;
    //This is the x & y coordinate based off of the tiles.
    protected int x = 0;
    protected int y = 0;

    //Width & height based off of tiles.
    protected int width = 0;
    protected int height = 0;

    protected GameImage graphic = null;

    public String name = "undefined";
    public String realm = "undefined";

    public Item(){
        grid = ResolutionManager.getGrid().get();
    }


    //updateLocation will refresh the x & y coordinates based off of a tile/grid x & y coordinates.
    //Precondition: input a x & y tile location
    //Post-condition: updates x & y of object multiplied by the grid size;
    public void updateLocation(int inputX, int inputY){
        setLayoutX(inputX*grid);
        setLayoutY(inputY*grid);
        x = inputX;
        y = inputY;
    }
    public GameImage setGraphic(String ...inputs){
        graphic = new GameImage(inputs);
        if(!this.getChildren().contains(graphic)){
            this.getChildren().add(graphic);
        }
        return graphic;
    }
    public GameImage setGraphic(Image ...inputs){
        graphic = new GameImage(inputs);
        if(!this.getChildren().contains(graphic)){
            this.getChildren().add(graphic);
        }
        return graphic;
    }

    public ImageView getGraphic(){
        return graphic;
    }

    //This function is abstract and is used for child objects to update their variables.
    public abstract void everyFrame();
    public int getTileX(){
        return x;
    }
    public int getTileY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void startHighlight(){
        //eh
    }
    public void stopHighlight(){
        //eh
    }
    public void loadSave(String save){
        name = findTag("name", save);
        realm = findTag("realm", save);
        x = Integer.valueOf(findTag("x", save));
        y = Integer.valueOf(findTag("y", save));
        width = Integer.valueOf(findTag("width", save));
        height = Integer.valueOf(findTag("height", save));
        updateLocation(x, y);
    }
    public String getSave(){
        StringBuffer output = new StringBuffer();
        output.append(createTag("name", name));
        output.append(createTag("realm", realm));
        output.append(createTag("x", x));
        output.append(createTag("y", y));
        output.append(createTag("width", width));
        output.append(createTag("height", height));
        return output.toString();
    }
    protected String findTag(String tag, String source){
        String opener = "["+ name + "." +tag + "]";
        String closer = ":["+ name + "." + tag + "]";
        int start = source.indexOf(opener) + opener.length();
        int end = source.indexOf(closer);
        if(start != -1 && end != -1)
            return source.substring(start, end);
        System.out.print("\nNo data for key " + tag + " in saved memory. -findTag" + this.getClass().toString());
        return "0";
    }
    protected String createTag(String tag, int value){
        return createTag(tag, String.valueOf(value));
    }
    protected String createTag(String tag, double value){
        return createTag(tag, String.valueOf(value));
    }
    protected String createTag(String tag, long value){
        return createTag(tag, String.valueOf(value));
    }
    protected String createTag(String tag, boolean value){
        return createTag(tag, String.valueOf(value));
    }
    protected String createTag(String tag, String value){
        String opener = "["+ name + "." + tag + "]";
        String closer = ":["+ name + "." + tag + "]";
        return opener+value+closer;
    }
}
