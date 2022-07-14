package attributes;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modules.MapEngine;

import java.util.Random;

/**
 * Created by William on 27-Oct-14.
 */
public class Tile extends Group{
    //Images for the tiles:
    public static Image grass = new Image("assets/original/grassTile.png");
    public static Image hiveWood = new Image("assets/original/cement.png");
    //Grid size of each tile.
    private int grid = ResolutionManager.getGrid().get();

    //Item data, for game references.
    public Item data = null;

    //Random seed to randomize the background.
    private Random rand = new Random();
    public Tile(String inputType){
        if(rand.nextBoolean())
            this.setRotate(180);
        if(rand.nextBoolean())
            this.setScaleX(-1);
        if (inputType == "grass"){
            this.getChildren().add(new GameImage(grass));
        }else if(inputType == "cement"){
            this.getChildren().add(new GameImage(hiveWood));
        }else{
            System.out.print("\nProblem with tile. -Tile");
        }
    }
}
