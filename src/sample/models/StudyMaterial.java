package sample.models;

import java.util.*;

/**
 * Учебній материал представляет собой мапу, где каждлй теме соотвествует список порций материала
 */
public class StudyMaterial {

    private Map<String, List<String>> material;
    private int size = 0;
    private int portionQuant = 0;

    public StudyMaterial() {
        material = new HashMap<>();
    }

    public StudyMaterial(Map<String, List<String>> material) {
        this.material = material;
        size = material.size();
        material.keySet().forEach(s -> {
            material.get(s).forEach(str->{
                portionQuant++;
            });
        });
    }

    public String getPortion(String title, int i){
        return material.get(title).get(i);
    }

    public void addPortion(String title, String content){
        List<String> paragraph = material.get(title);
        if(paragraph==null) {
            paragraph = new ArrayList<>();
            paragraph.add(content);
            material.put(title, paragraph);
            size++;
        } else paragraph.add(content);
        portionQuant++;
    }

    public int getSize() {
        return size;
    }

    public Set<String> getThemeSet(){
        return material.keySet();
    }

    public int getCountOfPortion(String theme){
        return  material.get(theme).size();
    }
    public int getPortionQuant(){
        return portionQuant;
    }

}
