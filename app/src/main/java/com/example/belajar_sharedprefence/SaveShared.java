package com.example.belajar_sharedprefence;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveShared {

    private static final String  USER_SHARED = "user_shared";
    private static final String  NAME        = "name";
    private static final String  EMAIL       = "email";
    private static final String  PASSWORD    = "password";
    private static final String  KELAMIN     = "kelamin";
    private static final String  AGREE       = "agree";
    private static final String LOGIN        = "satus_login";

    private final SharedPreferences preferences;

    SaveShared (Context context) {
        preferences = context.getSharedPreferences(USER_SHARED, Context.MODE_PRIVATE);
    }

    public void setUser(UserModel value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME, value.getNama());
        editor.putString(EMAIL, value.getEmail());
        editor.putString(PASSWORD, value.getPassword());
        editor.putString(KELAMIN, value.getKelamin());
        editor.putString(AGREE, value.getAgree());
        editor.putBoolean(LOGIN, value.isStatuslogin());
        editor.apply();
    }

    UserModel getuser(){
        UserModel model = new UserModel();
        model.setNama(preferences.getString(NAME,""));
        model.setEmail(preferences.getString(EMAIL,""));
        model.setPassword(preferences.getString(PASSWORD,""));
        model.setKelamin(preferences.getString(KELAMIN,""));
        model.setAgree(preferences.getString(AGREE,""));
        model.setStatuslogin(preferences.getBoolean(LOGIN, false));
        return model;
    }

}
