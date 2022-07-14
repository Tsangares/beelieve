package modules;

import attributes.Time;

/**
 * Created by William on Dec-12-2014.
 */
/*time: 12:17*/
public class EnvironmentManager {

    private MapEngine map;
    private Long frame;
    private Time clock;

    public EnvironmentManager(MapEngine temp){
        changeMap(temp);
    }
    public void changeMap(MapEngine temp){
        map = temp;
        if(map.player.frame != null) {
            frame = map.player.frame.get();
        }else{
            frame = new Long(0);
            map.player.frame.set(frame);
        }
        clock = new Time(frame);
    }
    public void setFrame(Long input){
        frame = input;
    }
    public Time getTime(){
        return clock;
    }

}
