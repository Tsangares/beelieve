package attributes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by William on Dec-09-2014.
 */
/*time: 12:27*/
    //Input Low, High
public class GameImage extends ImageView{
    public Image image[];

    public GameImage(Image solo){
        image = new Image[1];
        image[0] = solo;
        setImage(solo);
        update();
    }
    public GameImage(String solo) {
        image = new Image[1];
        image[0] = new Image(solo);
        setImage(image[0]);
        update();
    }
    //First input is assumed the original or the full res image.
    public GameImage(Image... inputs){
        image = inputs;
        setImage(image[0]);
        update();
    }
    //First input is assumed the original or the full res image.
    public GameImage(String... inputs){
        image = new Image[inputs.length];
        for(int i = 0; i < inputs.length; ++i){
            image[i] = (new Image(inputs[i]));
        }
        setImage(image[0]);
        update();
    }
    public void update(){
        double desiredWidth = image[0].getWidth() * ResolutionManager.getScale().get();
        double desiredHeight = image[0].getHeight() *  ResolutionManager.getScale().get();
        for(int i = 0; i < image.length; ++i){
            double thisAccuracy = Math.abs(Math.abs((image[i].getWidth()/desiredWidth + image[i].getHeight()/desiredHeight)) - 2);
            double currentAccuracy = Math.abs(Math.abs((this.getImage().getWidth()/desiredWidth + this.getImage().getHeight()/desiredHeight)) - 2);
            if(thisAccuracy < currentAccuracy)
                this.setImage(image[i]);
        }
        double scale = image[0].getWidth()/this.getImage().getWidth();
        setX(image[0].getWidth()*(( ResolutionManager.getScale().get()-1/scale)/2));
        setY(image[0].getHeight()*(( ResolutionManager.getScale().get()-1/scale)/2));
        setScaleX(desiredWidth/getImage().getWidth());
        setScaleY(desiredHeight/getImage().getHeight());
    }
}
