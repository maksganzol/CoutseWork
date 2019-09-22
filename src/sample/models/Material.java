package sample.models;

import java.util.ArrayList;
import java.util.List;

public class Material {

    private String title;
    private List<String> portions;
    private int currentPortion = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Material(String theme, List<String> portions) {
        this.title = title;
        this.portions = portions;
    }

    public Material(String title) {
        this.title = title;
        portions = new ArrayList<>();
    }

    public void addPortion(String portion){
        portions.add(portion);
    }

    public String getPortion(int index){
        return portions.get(index);
    }

    public int size(){
        return portions.size();
    }

    public void forEach(ForEachHandler handler){
        for(String p: portions)
            handler.handle(p);
    }

    public boolean isLast(String item){
        return portions.indexOf(item) == portions.size()-1;
    }
}
