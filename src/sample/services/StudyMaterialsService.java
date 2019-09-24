package sample.services;

import sample.repositories.StudyMaterialRepository;
import sample.models.Material;
import sample.models.StudyMaterial;

/**
 * Этот класс занимается предоставлением учебного материала
 */
public class StudyMaterialsService {

    private StudyMaterialRepository repository;
    private StudyMaterial studyMaterial;
    private Material currentMaterial;
    private int titleCounter = 0;
    private int portionCounter = 0;//Счетчик считает, какую порцию материала выдавать
    private boolean isLastPortionInMaterial = false;
    private boolean endTopic = false;

    public StudyMaterialsService() {
        repository = new StudyMaterialRepository();
        studyMaterial = repository.getMaterial();
        currentMaterial = studyMaterial.getMaterial(titleCounter);
    }

    public boolean isLastPortionInMaterial() {
        return isLastPortionInMaterial;
    }

    public StudyMaterial getStudyMaterials(){
        return studyMaterial;
    }

    public Material getMaterial(){
        currentMaterial = studyMaterial.getMaterial(titleCounter);
        //Если порция последняя в теме,
        if(currentMaterial.size()<=portionCounter){
            //и тема не последняя, то меняем ее.
            if(studyMaterial.getSize()-1>titleCounter) {
                titleCounter++;
                currentMaterial = studyMaterial.getMaterial(titleCounter);
                portionCounter = 0;
                endTopic = true;
            } else {
                portionCounter = currentMaterial.size()-1;
                isLastPortionInMaterial = true;
                endTopic = true;
            }

        } else {
            endTopic = false;
        }
        return currentMaterial;
    }

    public boolean isEndTopic(){
        return endTopic;
    }

    public String getPortionOfMaterial(){

        String portion = getMaterial().getPortion(portionCounter);
        portionCounter++;

        return portion;
    }

    public int getCountOfPortion(String title){
        return studyMaterial.getCountOfPortion(title);
    }

    public int getNumberOfPortion(){
        return portionCounter+1;
    }


    public void prev(){//Возвращает счетчик на предыдущую порцию материала
        if (portionCounter>0){
            portionCounter--;
        }
    }

    public String getCurrentTitle(){
        return currentMaterial.getTitle();
    }

}
