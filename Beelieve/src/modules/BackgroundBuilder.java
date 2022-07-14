package modules;

import attributes.Item;
import attributes.ResolutionManager;
import attributes.Tile;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.Vector;

/**
 * Created by William on 27-Oct-14.
 */
public class BackgroundBuilder extends Group{
    //Matrix holder for tiles.
    private Vector<Vector<Tile>> tileMatrix = new Vector<Vector<Tile>>();

    //Width and height of the matrix. Presets.
    public int width = 50; //Map Widths
    public int height = 50; //Map Height

    private double prevMouseX = 0;
    private double prevMouseY = 0;
    private boolean isRightButtonDown = false;

    //Grid size for each square.
    private final int grid = ResolutionManager.getGrid().get();

    public BackgroundBuilder(int _width, int _height, String color){
        initializeMatrix(_width, _height);
        colorMatrix(color);
        drawMatrix();
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isSecondaryButtonDown()) {
                    isRightButtonDown = true;
                    prevMouseX = MouseInfo.getPointerInfo().getLocation().getX();
                    prevMouseY = MouseInfo.getPointerInfo().getLocation().getY();
                }
            }
        });
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(!e.isSecondaryButtonDown()){
                    isRightButtonDown = false;
                }
            }
        });
    }
    public void everyFrame(){
        if(isRightButtonDown) {
            double dX = getParent().getLayoutX() - (prevMouseX - MouseInfo.getPointerInfo().getLocation().getX());
            double dY = getParent().getLayoutY() - (prevMouseY - MouseInfo.getPointerInfo().getLocation().getY());
            if (dX<=0 && dX>=-grid*width+ResolutionManager.getWidth().get() - SideBar.thisWidth)//gridSize * size of map + screen size - obstructions
                getParent().setLayoutX(dX);
            if(dY<=0 && dY>=-grid*height+ResolutionManager.getHeight().get())
                getParent().setLayoutY(dY);

            prevMouseX = MouseInfo.getPointerInfo().getLocation().getX();
            prevMouseY = MouseInfo.getPointerInfo().getLocation().getY();
        }
    }
    private boolean initializeMatrix(int inputWidth, int inputHeight){
        width = inputWidth;
        height = inputHeight;
        tileMatrix.clear();
        for(int i = 0; i < width; ++i){
            Vector<Tile> tempVec = new Vector<Tile>();
            tileMatrix.add(tempVec);
        }
        return true;
    }
    private boolean colorMatrix(String inputType){
        if(inputType == "stop")
            return false;
        for(int x = 0; x < width; ++x){
            Vector<Tile> tempX = tileMatrix.get(x);
            tempX.clear();
            for(int y = 0; y < height; ++y) {
                Tile thisTile = new Tile(inputType);
                thisTile.setLayoutX(x*grid);
                thisTile.setLayoutY(y*grid);
                tempX.add(thisTile);
            }
        }
        return true;
    }
    private boolean drawMatrix(){
        getChildren().clear();
        for(int x = 0; x < width; ++x){
            Vector<Tile> tempX = tileMatrix.get(x);
            for(int y = 0; y < height; ++y) {
                Tile thisTile = tempX.get(y);
                this.getChildren().add(thisTile);
            }
        }
        return true;
    }
    public boolean checkEmpty(int inputX, int inputY){
        if(inputX < 0 || inputY < 0 || inputX >= tileMatrix.size() || inputY >= tileMatrix.get(inputX).size()){
            SideBar.alert("Location invalid, (" + inputX + ", " + inputY + "). -checkEmpty in " + this.toString() + "\n");
            return false;
        }
        if(tileMatrix.get(inputX).get(inputY).data == null){
                return true;
        }
        SideBar.alert("Obstructed, cannot place item there.");
        return false;
    }
    public boolean addItem(Item inputItem, int inputX, int inputY){
        if(inputX < 0 || inputY < 0 || inputX >= tileMatrix.size() || inputY >= tileMatrix.get(inputX).size()){
            SideBar.alert("Location invalid, (" + inputX + ", " + inputY + "). -addItem in " + this.toString() + "\n");
            return false;
        }
        Tile temp = tileMatrix.get(inputX).get(inputY);
        if(temp.data == null) {
            temp.data = inputItem;
            return true;
        }
        return false;
    }
    public Tile getTile(int inputX, int inputY){
        Tile temp = tileMatrix.get(inputX).get(inputY);
        return temp;
    }
    public Item getItem(int inputX, int inputY){
        Tile temp = tileMatrix.get(inputX).get(inputY);
        return temp.data;
    }
    public Tile removeItem(int inputX, int inputY){
        Tile temp = tileMatrix.get(inputX).get(inputY);
        temp.data = null;
        return temp;
    }
}
