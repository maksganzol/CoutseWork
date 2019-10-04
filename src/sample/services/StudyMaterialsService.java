package sample.services;

import sample.repositories.StudyMaterialRepository;
import sample.models.Material;
import sample.models.StudyMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Этот класс занимается предоставлением учебного материала
 */
public class StudyMaterialsService {
    private StudyMaterialRepository repository;
    private StudyMaterial studyMaterial;
    private boolean isLast = false;

    private int portionCounter = 0;//Счетчик считает, какую порцию материала выдавать


    public StudyMaterialsService() {
        repository = new StudyMaterialRepository();
        studyMaterial = repository.getMaterial();
    }


    public String getPortionOfMaterial(String title){

        String portion = studyMaterial.getPortionByTitle(title, portionCounter);
        if(portionCounter<studyMaterial.getCountOfPortion(title)-1) {
            portionCounter++;
            isLast = false;
        }
        else isLast = true;

        return portion;
    }

    public int getCountOfPortion(String title){
        return studyMaterial.getCountOfPortion(title);
    }

    public int getNumberOfPortion(){
        return portionCounter+1;
    }

    public void resetCounter(){
        portionCounter = 0;
        isLast = false;
    }


    public String getPrevPortion(String title){//Возвращает счетчик на предыдущую порцию материала
        if (portionCounter>0){
            portionCounter--;
            isLast = false;
        }
        return studyMaterial.getPortionByTitle(title, portionCounter);
    }

    public List<String> getAllTitles() {
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < repository.getMaterial().getSize(); i++){
            titles.add(repository.getMaterial().getMaterial(i).getTitle());
        }
        return titles;
    }

    public boolean isLastPortionInMaterial() {
        return isLast;
    }
}
