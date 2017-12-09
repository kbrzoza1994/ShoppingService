package Controlers;

import models.User;
import Databases.UserDataBase;

import java.sql.Connection;

public class UsersControler {
    private UserDataBase userDataBase;

    UsersControler(String userDataBaseFileName) {
        userDataBase = new UserDataBase(userDataBaseFileName);
    }

    public UsersControler() {
        userDataBase = new UserDataBase();
    }

    public boolean addNewUser(User user) {
        return userDataBase.addNewUser(user);
    }

    public boolean isUserPresentInDataBase(User user) {
        return userDataBase.isUserPresentInDataBase(user);
    }

}
