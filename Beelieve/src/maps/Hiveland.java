package maps;

import attributes.Item;
import dataObjects.Hive;
import modules.BackgroundBuilder;
import modules.EnvironmentManager;
import modules.MapEngine;

import java.util.Vector;

/**
 * Created by William on Dec-12-2014.
 */
/*time: 20:46*/
/*
Derived classes from MapEngine have a protocol.
- Name the map. Try to make it original/dynamic as possible.
- Call the super constructor by passing in a list of items.
- Assign a new BackgroundBuilder to the backgroundHolder.
- Define a EnvironmentManager if necessary.
 */
public class Hiveland extends MapEngine{
    public Hive root = null;
    public EnvironmentManager weather;
    public Hiveland(Vector<Item> itemList, Hive homeland){
        super(itemList);
        root = homeland;
        backgroundHolder = new BackgroundBuilder(50,50,"cement");
        weather = new EnvironmentManager(this);
        initialize();
    }
    @Override
    public void everyFrame(){

    }
}
