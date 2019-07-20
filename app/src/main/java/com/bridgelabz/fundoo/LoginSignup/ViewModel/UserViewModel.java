package com.bridgelabz.fundoo.LoginSignup.ViewModel;

import android.content.Context;

import com.bridgelabz.fundoo.LoginSignup.Model.User;
import com.bridgelabz.fundoo.LoginSignup.Model.UserDatabaseManager;

public class UserViewModel {
    private UserDatabaseManager userDbManager;

    public UserViewModel(Context context) {
        userDbManager = new UserDatabaseManager(context);
    }

    public boolean addUser(User user) {
        return userDbManager.addUser(user);
    }

    public boolean checkUser(String email, String password) {
       return userDbManager.checkUser(email, password);
    }
}
