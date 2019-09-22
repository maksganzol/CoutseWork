package sample.models;

import java.util.ArrayList;
import java.util.List;

public class Question{
    private String question, correctAnswer, theme;
    private List<String> answers;

    public Question() {

    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Question(String question, String correctAnswer, String answer1, String answer2, String theme) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        answers = new ArrayList<>();
        answers.add(correctAnswer);
        answers.add(answer1);
        answers.add(answer2);
        this.theme = theme;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnswer(int i){
        return answers.get(i%3);
    }

    public boolean isCorrectAnswer(String answer){
        return answer.equals(correctAnswer);
    }
}