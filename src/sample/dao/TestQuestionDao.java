package sample.dao;

import sample.models.Question;

import java.io.*;
/**
 * Этот класс (dao: Data Access Object) позволяет абсрагироваться от работы с хранилищем данных (файлом данных)
 * и позволяет работать сервисам напрямую с объектами сущности (Question)
 */
public class TestQuestionDao {
    private FileInputStream inputStream;
    private BufferedReader br;
    private FileWriter writer;

    public TestQuestionDao() {
        try {
            inputStream = new FileInputStream("questions.txt");
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(Question question){
        try {
            writer = new FileWriter("questions.txt", true);
            writer.write("\n" + question.getQuestion() + "," + question.getCorrectAnswer() + "," + question.getAnswer(1) + "," + question.getAnswer(2) + "," + question.getTheme());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Question getQuestion(){
        String[] values = new String[0];
        try {
            String value = br.readLine();
            if (value == null) return null;
            values = value.split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Question(values[0], values[1], values[2], values[3], values[4]);
    }

}
