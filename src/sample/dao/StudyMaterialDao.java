package sample.dao;


import sample.models.Material;
import sample.models.StudyMaterial;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Этот класс (dao: Data Access Object) позволяет абсрагироваться от работы с хранилищем данных (файлом данных)
 * и позволяет работать сервисам напрямую с объектами сущности (StudyMaterial)
 */
public class StudyMaterialDao {

    private FileWriter writer;
    String path = "study_material.txt";

    public StudyMaterialDao() {

    }

    public StudyMaterial getMaterial(){

       List<Material> materials = new ArrayList<>();
        try {
            Files.lines(Paths.get(path)).forEach(
                s -> {
                    boolean addTitle = false;
                    String title = s.split("&")[0];
                    List<String> portions = new ArrayList<>();
                    for(Material m: materials) {
                        if (m.getTitle().equals(title)) {
                            m.addPortion(s.split("&")[1]);
                            addTitle = true;
                            break;
                        }
                    }
                    if(!addTitle) {
                        Material material = new Material(title);
                        materials.add(material);
                        material.addPortion(s.split("&")[1]);
                    }
                    });
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return new StudyMaterial(materials);
    }

    public void addPortionOfMaterial(String title, String material){
        try {
            writer = new FileWriter(path, true);
            writer.write("\n" + title + "&");
            writer.write(material);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
