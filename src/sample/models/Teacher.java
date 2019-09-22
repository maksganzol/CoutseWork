package sample.models;

public class Teacher {
    private String login, password;

    public String getLogin() {
        return login;
    }

    public Teacher(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Teacher() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
