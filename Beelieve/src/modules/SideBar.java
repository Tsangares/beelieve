package modules;

import attributes.GameImage;
import attributes.Item;
import attributes.ResolutionManager;
import attributes.Time;
import dataObjects.Hive;
import dataObjects.Player;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javax.swing.*;


/**
 * Created by William on Oct-30-2014.
 */
public class SideBar extends Group {
    public GameImage background = new GameImage("assets/original/displayBackground.png", "assets/low/displayBackground.png");
    public VBox children = new VBox(ResolutionManager.getGrid().get()*.5);
    public static VBox alerts = new VBox(2);
    public Group sideBarHolder = new Group();
    public static double thisWidth;
    public Game containingGame = null;

    public SideBar(Game input){
        containingGame = input;
        double padding = ResolutionManager.getGrid().get()*.25;
        thisWidth = background.image[0].getWidth()*ResolutionManager.getScale().get();
        children.setPadding(new Insets(padding));
        children.setMaxWidth(thisWidth);
        sideBarHolder.setLayoutX(ResolutionManager.getWidth().get() - thisWidth);
        sideBarHolder.setLayoutY(0);
        sideBarHolder.getChildren().addAll(background, children);
        this.getChildren().addAll(alerts, sideBarHolder);
    }

    public boolean displayBeeHive(final Hive myHive){
        addField(myHive.name, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                myHive.name = ((TextField) e.getSource()).getText();
            }
        });
        addTitle("Number of Bees: ", myHive.numBees);
        addTitle("Number of Queens: ", myHive.numQueens);
        addTitle("Number of Workers: ", myHive.numWorkers);
        addTitle("Number of Drones: ", myHive.numDrones);
        addButton("micro manage.", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                containingGame.changeMap(myHive);
                alert("Entering Hive...");
            }
        });
        return true;
    }
    public boolean displayInventory(Player hero){
        //addField("In " + containingGame.mainMap.name);
        addTitle(hero.name);
        addTitle("Money: $", hero.money);
        if(hero.clock != null)
            addTitle(hero.clock.findTime() + ", " + Time.findMonth(hero.clock.month) + " " + hero.clock.year);
        return true;
    }
    public boolean changeFocus(ReadOnlyObjectWrapper<Item> inputItem) {
        children.getChildren().clear();
        if (inputItem.get().getClass() == Player.class) {
            displayInventory((Player) inputItem.get());
        } else if(inputItem.get().getClass() == Hive.class){
            displayBeeHive(((Hive) inputItem.get()));
        }
        return true;
    }
    public Button addButton(String title, EventHandler<ActionEvent> change){
        final Button name = new Button(title);
        name.setPrefWidth(thisWidth);
        name.setStyle(ResolutionManager.getButtonStyle().get());
        name.setAlignment(Pos.CENTER);
        name.setOnAction(change);
        children.getChildren().add(name);
        return name;
    }
    public TextField addField(String title, EventHandler<KeyEvent> change){
        final TextField name = new TextField(title);
        name.setPrefWidth(thisWidth);
        name.setStyle(ResolutionManager.textStyle[1] +
                "\n-fx-border-style: solid;" +
                "\n-fx-border-color: #FFFFFF;" +
                "-fx-highlight-fill: #AAAAAA; -fx-highlight-text-fill: -fx-text-fill;");
        name.setAlignment(Pos.CENTER);
        name.setOnKeyReleased(change);
        children.getChildren().add(name);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                name.deselect();
            }
        });
        return name;
    }
    public Label addTitle(final String title, final Property<?> inputData){
        double data = 0.0;
        final Label text = new Label();
        text.setPrefWidth(thisWidth);
        text.setAlignment(Pos.CENTER);
        text.setStyle(ResolutionManager.textStyle[1]);
        children.getChildren().add(text);
        if(inputData.getValue().getClass().equals(Double.class)){
            data = (Double)(inputData.getValue());
            text.setText(title + String.format("%.2f", data));
        }else{
            text.setText(title + inputData.getValue().toString());
        }
        inputData.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observableValue, Object o, Object o2) {
                if(inputData.getValue().getClass().equals(Double.class)){
                    double newData = (Double)(inputData.getValue());
                    text.setText(title + String.format("%.2f", newData));
                }else{
                    text.setText(title + inputData.getValue().toString());
                }
            }
        });
        return text;
    }
    public Label addTitle(String title){
        Label name = new Label(title);
        name.setPrefWidth(thisWidth);
        name.setAlignment(Pos.CENTER);
        name.setStyle(ResolutionManager.textStyle[1]);
        children.getChildren().add(name);
        return name;
    }
    public static Label alert(String alert) {
        System.out.print(alert);
        final Label title = new Label(alert);
        title.setStyle(ResolutionManager.getButtonStyle().get());
        title.setPrefWidth(ResolutionManager.getWidth().get() - thisWidth);
        FadeTransition fade = new FadeTransition(Duration.seconds(1), title);
        fade.setDelay(Duration.seconds(3));
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.play();
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                alerts.getChildren().remove(title);
            }
        });
        alerts.getChildren().add(title);
        return title;
    }
}