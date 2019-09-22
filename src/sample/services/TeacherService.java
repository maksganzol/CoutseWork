package sample.services;

import sample.dao.TeacherDao;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Teacher;

public class TeacherService {
    private TeacherDao dao;
    public TeacherService(){
        dao = new TeacherDao();
    }
    public Teacher getTeacher(String login){
        return dao.getTeacherByLogin(login);
    }
    public void addTeacher(Teacher teacher) throws UserAlreadyExistsException {
        dao.addTeacher(teacher);
    }
    public boolean authorized(Teacher teacher){
        Teacher t = dao.getTeacherByLogin(teacher.getLogin());
        if(t==null) return false;
        boolean auth = teacher.getPassword().equals(t.getPassword());
        return auth;
    }
}
