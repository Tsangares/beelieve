package modules;

import attributes.Item;
import attributes.ResolutionManager;
import attributes.Time;
import dataObjects.Hive;
import dataObjects.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.LongBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import maps.Grassland;
import maps.Hiveland;

import java.awt.*;


/**
 * Created by William on 27-Oct-14.
 */
public class Game extends Scene {
    //Refresh rate, currently set to 60 fps.
    public static int frameRate = 60;
    private Duration timeStamp = Duration.millis(1000/frameRate);

    //This creates the thread game loop.
    private Timeline myTimeline;

    //This will contain the function for the game loop.
    private KeyFrame myLoop;

    //This is the map class for the Game class. In theory this can change.
    public MapEngine mainMap;

    public Group container = new Group();
    private Group outputGroup = new Group();

    //Intro
    private Intro menu;

    //Creates the right hand display window.
    public SideBar sideBarPanel;

    //Saves
    private SaveEngine save;

    //Shopping cart;
    private Store localStore;


    public Game() {
        super(new Group());
        this.setRoot(outputGroup);
        ResolutionManager.updateResolution();

        menu = new Intro();
        outputGroup.getChildren().addAll(menu);
        save = menu.loadIntro(this);
        sideBarPanel = new SideBar(this);
    }
    public void startIntro(){
        menu = new Intro();
        save = menu.loadIntro(this);
    }
    public void changeMap(){
        mainMap = new Grassland(save.saveList);
        mainMap.loadSave(save.saveList);
    }
    public void changeMap(Item containingObject){
        container.getChildren().remove(mainMap);
        if(containingObject.getClass().equals(Hive.class)) {
            mainMap = new Hiveland(save.saveList, ((Hive)(containingObject)));
        }
        mainMap.loadSave(save.saveList);
        container.getChildren().add(mainMap);
        mainMap.toBack();
    }
    public boolean initialize(){
        outputGroup.getChildren().remove(menu);
        outputGroup.getChildren().add(container);
        mainMap = new Grassland(save.saveList);
        sideBarPanel.changeFocus(mainMap.focusObj);
        localStore = new Store(mainMap);

        container.getChildren().addAll(mainMap, sideBarPanel, localStore);
        mainMap.loadSave(save.saveList);
        mainMap.focusObj.addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observableValue, Item item, Item item2) {
                sideBarPanel.changeFocus(mainMap.focusObj);
            }
        });
        //Defining the function for the game loop.
        myLoop = new KeyFrame(timeStamp, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Updates display. (Right hand panel)
                mainMap.run();
                mainMap.player.frame.set(mainMap.player.frame.get()+1);
                if(mainMap.player.frame.get()%600 == 0){
                    save.setSave();

                }
            }
        });

        //Building the game loop.
        myTimeline = new Timeline(myLoop);
        myTimeline.setCycleCount(Animation.INDEFINITE);
        myTimeline.play();

        return true;
    }
}
