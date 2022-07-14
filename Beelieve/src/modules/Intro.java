package modules;

import attributes.GameImage;
import attributes.ResolutionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * Created by William on Dec-05-2014.
 */
/*time: 13:49*/
public class Intro extends Group{

    private Game thisGame;
    private SaveEngine save;
    private GameImage logo = new GameImage("assets/original/introBackground.png", "assets/low/introBackground.png");
    private GameImage tempPic = new GameImage("assets/original/beePhoto01.jpg", "assets/low/beePhoto01.jpg");
    private Media backgroundVideo = new Media("http://startandselect.com/DoNotReadThis/Downloads/introVid.mp4");
    private MediaPlayer vidPlayer = new MediaPlayer(backgroundVideo);
    private MediaView video = new MediaView(vidPlayer);

    public SaveEngine loadIntro(final Game game){
        thisGame = game;
        save = new SaveEngine();
        final Group introHolder = new Group();
        this.getChildren().add(introHolder);
        setupVideo();
        setupButtons();
        getChildren().addAll(logo);
        return save;
    }
    private void setupVideo(){
        final Label loading = new Label("Video is loading...");
        vidPlayer.bufferProgressTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration duration2) {
                loading.setText(" Video is loading: " + Integer.toString((int)(100*vidPlayer.getBufferProgressTime().toMillis()/backgroundVideo.getDuration().toMillis())) + "%");
                if(vidPlayer.getBufferProgressTime().greaterThanOrEqualTo(backgroundVideo.getDuration().divide(1))){
                    video.setX(backgroundVideo.getWidth()*((ResolutionManager.getScale().get()-.5)));
                    video.setY(backgroundVideo.getHeight()*((ResolutionManager.getScale().get()-.5)));
                    video.setScaleX(ResolutionManager.getScale().get()*2);
                    video.setScaleY(ResolutionManager.getScale().get()*2);
                    vidPlayer.play();
                    getChildren().remove(tempPic);
                    if(!getChildren().contains(video))
                        getChildren().add(video);
                    video.toBack();
                    vidPlayer.bufferProgressTimeProperty().removeListener(this);
                }
            }
        });
        vidPlayer.setCycleCount(Integer.MAX_VALUE);
        getChildren().addAll(tempPic, loading);
    }
    private void setupButtons(){
        int buttonsY = (int)(ResolutionManager.getHeight().get()*.7);
        final Button load = new Button("continue.");
        final Button newGame = new Button("new.");
        load.setStyle(ResolutionManager.getButtonStyle().get());
        newGame.setStyle(ResolutionManager.getButtonStyle().get());
        load.setLayoutX(ResolutionManager.getWidth().get() - ResolutionManager.getWidth().get() / 4);
        newGame.setLayoutX(ResolutionManager.getWidth().get()/4);
        load.setLayoutY(buttonsY);
        newGame.setLayoutY(buttonsY);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Label name = new Label("name:");
                name.setStyle("-fx-text-fill: gray;");
                name.setLayoutX(newGame.getLayoutX()-newGame.getWidth());
                name.setLayoutY(newGame.getLayoutY());
                final TextField nameField = new TextField("Edmund Basil");
                nameField.setLayoutX(newGame.getLayoutX());
                nameField.setLayoutY(newGame.getLayoutY());
                getChildren().add(name);
                getChildren().add(nameField);
                newGame.setLayoutX(load.getLayoutX());
                newGame.setText("make new game.");
                load.setText("goBack.");
                load.setLayoutY(load.getLayoutY() + load.getHeight()*1.2);
                load.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //Go Back
                    }
                });
                newGame.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        getChildren().removeAll(load, name, nameField);
                        newGame.setText("loading.");
                        newGame.setLayoutX(775F);
                        save.newSave(nameField.getText());
                        thisGame.initialize();
                    }
                });
            }
        });
        load.setOnAction(new EventHandler<ActionEvent>() {
            //These threads are not working the way I want.
            @Override
            public void handle(ActionEvent actionEvent) {
                newGame.setText("loading.");
                newGame.setLayoutX(ResolutionManager.getWidth().get()*.5);
                getChildren().remove(load);
                save.loadSave();
                thisGame.initialize();
            }
        });
        getChildren().addAll(newGame, load);
    }
}
