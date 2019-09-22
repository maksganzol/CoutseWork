package sample.dao;


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

        Map<String, List<String>> material = new HashMap<>();

        try {
            Files.lines(Paths.get(path)).forEach(
                s -> {
                    String theme = s.split("&")[0];
                    List<String> portions = material.get(theme);
                    if(portions!=null)
                        portions.add(s.split("&")[1]);
                    else {
                        portions = new ArrayList<>();
                        portions.add(s.split("&")[1]);
                        material.put(theme, portions);
                    }
                });
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return new StudyMaterial(material);
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
