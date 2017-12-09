package Databases;

import Helper.ConectToDatabase;
import Helper.FileOperations;
import models.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDataBase {
    private ArrayList<User> listOfUsers;
    private String fileName;
    private Connection connection = ConectToDatabase.getInstance("zenek","zenek","123").getConnection();

    public UserDataBase() {
        fileName = "Users.bin";
        listOfUsers = FileOperations.loadUserList(fileName);
    }

    public UserDataBase(String fileName) {
        this.fileName = fileName;
        listOfUsers = FileOperations.loadUserList(fileName);
    }

    public boolean addNewUser(User user) {
        String login = user.getUserName();

        if (!isLoginAlreadyInUse(login)) {
            try {
                Statement statement = connection.createStatement();
                String salt = null;
                ResultSet rs = statement.executeQuery("SELECT gen_salt('bf')");

                while (rs.next()) {
                    salt = rs.getString("gen_salt");
                }
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login,password,salt) VALUES (?,crypt(?,?),?)");
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, salt);
                preparedStatement.setString(4, salt);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    public boolean isUserPresentInDataBase(User user) {
        String login = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = crypt(?,salt)");
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs;
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                login = rs.getString("login");
            }
            if (login.equals(user.getUserName()))
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("database problem");
            return false;
        }
    }

    private boolean isLoginAlreadyInUse(String login) { // jezeli jest to true
        String loginCheck = null;
        try {
            ResultSet isLoginInBase;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT login FROM users WHERE login = ?");
            preparedStatement.setString(1, login);
            isLoginInBase = preparedStatement.executeQuery();

            while (isLoginInBase.next()) {
                loginCheck = isLoginInBase.getString("login");
            }

            if (loginCheck != null)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
