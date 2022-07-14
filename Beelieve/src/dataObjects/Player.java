package dataObjects;

import attributes.Item;
import attributes.Time;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import modules.SideBar;

import java.awt.*;
import java.util.Vector;

/**
 * Created by William on Nov-14-2014.
 */
public class Player extends Item{
    public ReadOnlyDoubleWrapper money = new ReadOnlyDoubleWrapper(0.0);
    public ReadOnlyDoubleWrapper honey = new ReadOnlyDoubleWrapper(0.0);
    public ReadOnlyDoubleWrapper pollen = new ReadOnlyDoubleWrapper(0.0);
    public ReadOnlyDoubleWrapper experience = new ReadOnlyDoubleWrapper(0.0);
    public ReadOnlyIntegerWrapper level = new ReadOnlyIntegerWrapper(0);
    public ReadOnlyLongWrapper frame = new ReadOnlyLongWrapper(0);
    public static Time clock;
    public Player(){
        x = -1;
        y = -1;
        width = 1;
        height = 1;
        money.set(1000);
    }
    public void everyFrame(){
        if(clock != null)
            clock.update(frame.get());
    }
    public void loadSave(String save){
        super.loadSave(save);
        money.set(Double.valueOf(findTag("money", save)));
        honey.set(Double.valueOf(findTag("honey", save)));
        pollen.set(Double.valueOf(findTag("pollen", save)));
        experience.set(Double.valueOf(findTag("experience", save)));
        frame.set(Long.valueOf(findTag("frame", save)));
        clock = new Time(frame.get());
    }
    public String getSave(){
        StringBuffer output = new StringBuffer();
        output.append(super.getSave());
        output.append(createTag("money", money.get()));
        output.append(createTag("honey", honey.get()));
        output.append(createTag("pollen", pollen.get()));
        output.append(createTag("experience", experience.get()));
        output.append(createTag("frame", frame.get()));
        SideBar.alert("Saved on frame " + frame.get() + "\n");
        return output.toString();
    }
}
