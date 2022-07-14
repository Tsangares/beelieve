package attributes;

/**
 * Created by William on Dec-12-2014.
 */
/*time: 13:01*/
public class Time {
    public static String longMonth = "longMonth";
    public static String shortMonth = "shortMonth";
    public Long year;
    public Long month;
    public Long day;
    public Long hour;
    public Long min;
    public Long second;
    public Time(){
    }
    public Time(long input){
        copy(calculate(input));
    }
    public void copy(Time input){
        year = input.year;
        month = input.month;
        day = input.day;
        hour = input.hour;
        min = input.min;
        second = input.second;
    }
    public void update(long frame){
        second = frame/5;
        min = second/60;
        hour = min/60;
        day = hour/24;
        month = day/30 + 4;
        year = month/12 + 1926;
    }
    public static Time calculate(long frame){
        Time output = new Time();
        output.second = frame/20;
        output.min = output.second/60;
        output.hour = output.min/60;
        output.day = output.hour/24;
        output.month = output.day/30 + 4;
        output.year = output.month/12 + 1926;
        return output;
    }
    public String findTime(){
        return  String.format("%02d",hour%12) + ":" + String.format("%02d",min%60) + (hour%24>12?" pm":" am");
    }
    public static String findMonth(long number){
        switch ((int)(number)) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
        }
        return "Month";
    }
}
