package sample.models;

import java.util.*;

/**
 * Учебній материал представляет собой мапу, где каждлй теме соотвествует список порций материала
 */
public class StudyMaterial {

    private List<Material> materials;
    private int portionQuant = 0;

    public StudyMaterial() {
        materials = new ArrayList<>();
    }

    public StudyMaterial(List<Material> materials) {
        this.materials = materials;
        materials.forEach(material->{
            material.forEach(portion ->{
                portionQuant++; //Needs tests
            });
        });
    }

    public String getPortionByTitle(String title, int i){
        for(Material m: materials) {
            if (m.getTitle().equals(title)) {
                return m.getPortion(i);
            }
        }
        return null;
    }

    public void addPortion(String title, String content){
        for(Material m: materials) {
            if (m.getTitle().equals(title)) {
                m.addPortion(content);
                return;
            }
        }
        Material newMaterial = new Material(title);
        newMaterial.addPortion(content);
        materials.add(newMaterial);
        portionQuant++;
    }

    public int getSize() {
        return materials.size();
    }

    public Material getMaterial(int index){
        return materials.get(index);
    }

    public int getCountOfPortion(String title){
        for(Material m: materials) {
            if (m.getTitle().equals(title))
                return m.size();
        }
        return 0;
    }

    public int getPortionQuant(){
        return portionQuant;
    }

}
