package sample.services;

import sample.dao.StudyMaterialDao;
import sample.models.StudyMaterial;

import java.util.Set;

/**
 * Этот класс занимается предоставлением учебного материала
 */
public class StudyMaterialsService {

    private StudyMaterialDao dao;
    private StudyMaterial material;
    private int counter = 0;//Счетчик считает, какую порцию материала выдавать

    public StudyMaterialsService() {
        dao = new StudyMaterialDao();
    }

    public StudyMaterial getStudyMaterials(){
        return dao.getMaterial();
    }


    public String getPortionOfMaterial(String title){
        material = getStudyMaterials();

        String portion = material.getPortion(title, counter);
        if(counter+1>=getCountOfPortion(title))
            counter=0;
        else counter++;
        return portion;
    }

    public int getCountOfPortion(String title){
        return getStudyMaterials().getCountOfPortion(title);
    }

    public String getQuantityReadPages(){
        int allPortions = 0;

        return this.getNumberOfPortion() + "/" + this.getStudyMaterials().getPortionQuant();
    }


    public int getNumberOfPortion(){
        return counter+1;
    }


    public void prev(){//Возвращает счетчик на предыдущую порцию материала
        if (counter>0){
            counter--;
        }
    }


    public Set<String> getThemeSet() {
        return getStudyMaterials().getThemeSet();
    }
}
