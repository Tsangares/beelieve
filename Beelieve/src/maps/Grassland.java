package maps;

import attributes.Item;
import modules.BackgroundBuilder;
import modules.EnvironmentManager;
import modules.MapEngine;

import java.util.Vector;

/**
 * Created by William on Dec-12-2014.
 */
/*time: 16:05*/
public class Grassland extends MapEngine {
    public EnvironmentManager weather;

    public Grassland(Vector<Item> itemList){
        super(itemList);
        backgroundHolder = new BackgroundBuilder(40,40,"grass");
        weather = new EnvironmentManager(this);
        initialize();
    }
    @Override
    public void everyFrame(){

    }
}
