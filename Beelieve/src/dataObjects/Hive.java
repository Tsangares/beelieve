package dataObjects;

import attributes.GameImage;
import attributes.Item;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by William on 30-Oct-14.
 */
public class Hive extends Item {
    public ReadOnlyIntegerWrapper numBees = new ReadOnlyIntegerWrapper(0);
    public ReadOnlyIntegerWrapper numQueens = new ReadOnlyIntegerWrapper(0);
    public ReadOnlyIntegerWrapper numDrones = new ReadOnlyIntegerWrapper(0);
    public ReadOnlyIntegerWrapper numWorkers = new ReadOnlyIntegerWrapper(0);
    public ReadOnlyIntegerWrapper deaths = new ReadOnlyIntegerWrapper(0);
    public ReadOnlyDoubleWrapper totalHoney = new ReadOnlyDoubleWrapper(0.0);
    public ReadOnlyDoubleWrapper totalPollen = new ReadOnlyDoubleWrapper(0.0);


    public Hive() {
        name = "My Favorite Hive";
        setGraphic("assets/original/telescopingRoofTop.png", "assets/low/telescopingRoofTop.png");
        width = 2;
        height = 2;
    }
    public void loadSave(String save){
        super.loadSave(save);
        numBees.set(Integer.valueOf(findTag("numBees", save)));
        numQueens.set(Integer.valueOf(findTag("numQueens", save)));
        numDrones.set(Integer.valueOf(findTag("numDrones", save)));
        numWorkers.set(Integer.valueOf(findTag("numWorkers", save)));
        deaths.set(Integer.valueOf(findTag("deaths", save)));
        totalHoney.set(Double.valueOf(findTag("totalHoney", save)));
        totalPollen.set(Double.valueOf(findTag("totalPollen", save)));
    }
    public String getSave(){
        StringBuffer output = new StringBuffer();
        output.append(super.getSave());
        output.append(createTag("numBees", numBees.get()));
        output.append(createTag("numQueens", numQueens.get()));
        output.append(createTag("numDrones", numDrones.get()));
        output.append(createTag("numWorkers", numWorkers.get()));
        output.append(createTag("deaths", deaths.get()));
        output.append(createTag("totalHoney", totalHoney.get()));
        output.append(createTag("totalPollen", totalPollen.get()));
        return output.toString();
    }
    public boolean insertNuke(int inputQueens, int inputDrones, int inputWorkers, double inputHoney, double inputPollen){
        numQueens.set(numQueens.get() + inputQueens);
        numDrones.set(numDrones.get() + inputDrones);
        numWorkers.set(numWorkers.get() + inputWorkers);
        totalHoney.set(totalHoney.get() + inputHoney);
        totalPollen.set(totalPollen.get() + inputPollen);
        updateBeeCount();
        return true;
    }
    private boolean updateBeeCount(){
        numBees.set(numDrones.get() + numQueens.get() + numWorkers.get());
        return true;
    }
    public void everyFrame(){
        if(Player.clock.second%10 == 0){
            insertNuke(1, 2, 3, 4, 5);
        }
    }

}
