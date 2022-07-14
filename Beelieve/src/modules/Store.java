package modules;

import attributes.GameImage;
import attributes.Item;
import attributes.ResolutionManager;
import dataObjects.Hive;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by William on Nov-14-2014.
 */
public class Store extends Group{
    private Group children = new Group();
    public VBox catalog = new VBox(ResolutionManager.getGrid().get());

    private boolean displayToggle = false;
    private GameImage graphic = new GameImage("assets/original/CatalogGraphic.png", "assets/low/CatalogGraphic.png");
    private GameImage icon = new GameImage("assets/original/shoppingCartButton.png", "assets/low/shoppingCartButton.png");

    private MapEngine map = null;
    public Store(MapEngine inputMap){
        setLayoutX(0);
        setLayoutY(0);
        children.setLayoutX(0);
        children.setLayoutY(0);
        int catalogX = ResolutionManager.getGrid().get()*3;
        int catalogY = ResolutionManager.getGrid().get()*4;
        map = inputMap;
        if(map != null) {
            setExitNode();
        }
        this.getChildren().add(icon);

        catalog.setLayoutX(catalogX);
        catalog.setLayoutY(catalogY);
        graphic.setLayoutX(ResolutionManager.getGrid().get());
        graphic.setLayoutY(ResolutionManager.getGrid().get());
        icon.setLayoutX(ResolutionManager.getGrid().get()*.5);
        icon.setLayoutY(ResolutionManager.getHeight().get() - ResolutionManager.getGrid().get()*.5 - icon.getImage().getHeight());

        icon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (displayToggle) {
                    close();
                } else {
                    display();
                }
            }
        });
        children.getChildren().addAll(graphic, catalog);
    }
    public void buildCatalog(){
        catalog.getChildren().clear();
        catalog.getChildren().add(addItemToCatalog(Hive.class, 100.59));
    }
    private Node addItemToCatalog(final Class input, final double cost){
        HBox output = new HBox();
        Item thisItem = null;
        try {
            thisItem = (Item) (input.newInstance());
        }catch(Exception e){
            System.out.print(e.toString());
            return null;
        }
        final VBox purchase = new VBox(ResolutionManager.getGrid().get()*.1);
        output.setSpacing(ResolutionManager.getGrid().get());
        Label object = new Label(thisItem.name + ".");
        object.setStyle(ResolutionManager.textStyle[0]);
        Button price = new Button("Buy $" + cost + ".");
        Label errors = new Label("Not enough funds\nYou need $" + String.format("%.2f", cost - map.player.money.get()) + " more.");
        price.setStyle(ResolutionManager.getButtonStyle().get());
        errors.setMaxWidth(ResolutionManager.getGrid().get()*7);
        errors.setWrapText(true);
        errors.setStyle(ResolutionManager.textStyle[0]);
        if(map.player.money.get() >= cost){
            errors.setText("After purchase you will have: $" + String.format("%.2f", map.player.money.get() - cost));
        }
        if(map.player != null && map.player.money.get() >= cost){
            price.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    close();
                    map.placingNewItem(input);
                    map.player.money.set(map.player.money.get() - cost);
                }
            });
        }
        purchase.getChildren().addAll(object, price);
        output.getChildren().addAll(thisItem.getGraphic(), purchase, errors);
        return output;
    }
    public void setExitNode(){
        //This is used to toggle it open and closed when clicking outside the catalog.
        map.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (displayToggle) {
                    close();
                }
            }
        });
    }
    private void close(){
        catalog.getChildren().clear();
        getChildren().remove(children);
        displayToggle = false;
    }
    private void display(){
        buildCatalog();
        getChildren().add(children);
        displayToggle = true;
        System.out.print(graphic.getLayoutX()+" : " + getLayoutY());
    }
}
