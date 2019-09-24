package sample.models;

import java.util.Objects;

public class Student {
    private String login, password, name, lastName;
    private int totalMark = 0;

    public int getTotalMark() {
        return totalMark;
    }

    public void increaseMark(){
        totalMark++;
    }

    public Student() {
    }

    public Student(String login, String password, String name, String lastName) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
    }

    public Student(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", totalMark=" + totalMark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return login.equals(student.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
