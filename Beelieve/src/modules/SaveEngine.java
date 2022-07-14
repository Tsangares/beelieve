package modules;

import attributes.Item;
import dataObjects.Hive;
import dataObjects.Player;
import javafx.util.Pair;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.nio.file.Files;
import java.util.*;

/**
 * Created by William on Nov-24-2014.
 */
public class SaveEngine {
    public static String folderName = "Beelieve";
    public static String fileName = ".satan";
    private static String osData = System.getProperty("os.name");
    public Vector<Item> saveList = new Vector<Item>();//Used to save objects.
    private Vector<Class> classes = new Vector<Class>();
    public SaveEngine(){
        //Describe items for saving here;
        classes.add(Player.class);
        classes.add(Hive.class);
    }
    public void newSave(String inputName){
        String newData = createPreliminaryInfo();
        newData = newData.replace("[dataObjects.Player]0:[dataObjects.Player][dataObjects.PlayerN?L]:[dataObjects.PlayerN?L]",
                "[dataObjects.Player]1:[dataObjects.Player][dataObjects.PlayerN?L]!<!"+inputName+"!>!?<?1?>?:[dataObjects.PlayerN?L]");
        writeToSave(newData);
        initializePreliminaries();
        setSave();
    }
    public void loadSave(){
        initializePreliminaries();
        String output = readFile(connect());
        if(output != null) {
            for(Item  thisItem:saveList) {
                String classTag = thisItem.name + thisItem.id;
                thisItem.loadSave(findData(classTag, output));
            }
        }else{
            System.out.print("Problem collecting save. Cannot locate file location.");
        }
    }
    public static void loadSpecific(Item thisItem){
        String output = readFile(connect());
        if(output != null) {
            String classTag = thisItem.name  + Integer.toString(thisItem.id);
            thisItem.loadSave(findData(classTag, output));
        }else{
            System.out.print("Problem collecting save. Cannot locate file location.");
        }
    }
    public boolean setSave(){
        String output = createPreliminaryInfo();
        for(Item thisItem:saveList) {
            String opener = "["+ thisItem.name + thisItem.id + "]";
            String closer = ":["+ thisItem.name + thisItem.id + "]";
            output = output.concat(opener + thisItem.getSave() + closer);
        }
        return writeToSave(output);
    }
    public static String findData(String tag, String output){
        String opener = "["+ tag + "]";
        String closer = ":["+ tag + "]";
        int open = output.indexOf(opener);
        int close = output.lastIndexOf(closer)+closer.length();
        if(open != -1 && close != -1)
            return output.substring(open, close);
        System.out.print("\nNo data for key " + tag + " in saved memory. -findData" + SaveEngine.class.toString());
        return "0";
    }
    public static String readFile(File input){
        /*
        try {
            byte[] dataInBytes = Files.readAllBytes(input.toPath());
            String output = new String(dataInBytes);
            return output;
        }catch (Exception e){
            System.out.print(e.toString());
        }
        return null;
        */
        return new String("");
    }
    public boolean writeToSave(String output){
        try {
            File savesLoc = connect();
            FileWriter scribe = new FileWriter(savesLoc);
            scribe.write(output);
            scribe.close();
            return true;
        }
        catch (IOException e){
           System.out.print(e.toString());
        }
        return false;
    }
    private void initializePreliminaries(){
        File source = connect();
        String output = readFile(source);
        for (Class thisClass : classes){
            Vector<Pair<String, Integer>> nameList = getNameList(thisClass.getName(), output);
            for(int n = 0; n < returnPrelim(thisClass.getName(), output); ++n){
               try {
                    Item temp = (Item) (thisClass.newInstance());
                    temp.name = nameList.elementAt(0).getKey();
                    temp.id = nameList.elementAt(0).getValue();
                    nameList.removeElementAt(0);
                    saveList.add((temp));
                }catch (Exception e){
                    System.out.print(e.toString() + " -initializePreliminaries from:" + this.getClass().toString());
                }
            }
        }
        //Primary setup

    }
    private String createPreliminaryInfo(){
        String output = "";
        for(Class thisClass:classes){
            String opener = "["+ thisClass.getName() + "N?L" + "]";
            String closer = ":["+ thisClass.getName() + "N?L" + "]";
            String nameList = opener;
            int x = 0;
            for(Item item:saveList){
                if(item.getClass().toString().equals(thisClass.toString())) {
                    ++x;
                    nameList = nameList.concat("!<!" + item.name + "!>!" + "?<?" + item.id + "?>?");
                }
            }
            nameList = nameList.concat(closer);
            output = output.concat("["+thisClass.getName()+"]" + x + ":["+thisClass.getName()+"]" + nameList);

        }
        return output;
    }
    private Vector<Pair<String, Integer>> getNameList(String key, String source){
        Vector<Pair<String, Integer>> output = new Vector<Pair<String, Integer>>();
        String opener = "["+ key + "N?L" + "]";
        String closer = ":["+ key + "N?L" + "]";
        int start = source.indexOf(opener) + opener.length();
        int end = source.indexOf(closer);
        String value;
        if(end == -1){
            value = "no data";
            System.out.print("\nNo data for key " + key + " in saved memory. -getNameList");
        }else {
            value = source.substring(start, end);
        }
        start = value.indexOf("!<!");
        end = value.indexOf("!>!");
        int sstart = value.indexOf("?<?");
        int send = value.indexOf("?>?");
        while(start != -1 && end != -1 && sstart != -1 && send != -1) {
            output.add(new Pair<String, Integer>(value.substring(start+3, end),Integer.parseInt(value.substring(sstart+3, send)) ));
            start = value.indexOf("!<!", end);
            end = value.indexOf("!>!", start);
            sstart = value.indexOf("?<?", end);
            send = value.indexOf("?>?", start);
        }
        return output;
    }
    private int returnPrelim(String key, String source){
        String opener = "["+ key + "]";
        String closer = ":["+ key + "]";
        int start = source.indexOf(opener) + opener.length();
        int end = source.indexOf(closer);
        String value;
        if(end == -1){
            value = "no data";
            System.out.print("\nNo data for key " + key + " in saved memory. -returnPrelim");
        }else {
            value = source.substring(start, end);
        }
        return Integer.parseInt(value);
    }
    public static File connect(){
        //Save location for windows
        String dataFolderWin = System.getenv("APPDATA") + "\\"+folderName;
        //Save location for mac
        String dataFolderMac = System.getProperty("user.home") + "\\Library\\Application Support\\"+folderName;

        if(osData.contains("Windows")) {
            File savesLoc = new File(dataFolderWin);
            if (savesLoc.exists()) {
                try {
                    savesLoc = new File(dataFolderWin + "\\" + fileName);

                    return savesLoc;
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            } else {
                try {
                    savesLoc.mkdir();
                    savesLoc = new File(dataFolderWin + "\\" + fileName);
                    savesLoc.createNewFile();
                    return savesLoc;
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
            }
        }else if(osData.contains("Mac")){
            File savesLoc = new File(dataFolderMac);
            if (savesLoc.exists()) {
                try {
                    savesLoc = new File(dataFolderWin + "\\" + fileName);

                    return savesLoc;
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            } else {
                try {
                    savesLoc.mkdir();
                    savesLoc = new File(dataFolderWin + "\\" + fileName);
                    savesLoc.createNewFile();
                    return savesLoc;
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
            }
        }else{
            System.out.print("System unsupported for saves");
            return null;
        }
        return null;
    }
}
