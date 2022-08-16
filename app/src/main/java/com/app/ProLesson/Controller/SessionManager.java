package com.app.ProLesson.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.ProLesson.dataType.User;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user) {
        //save session when a user logged in
        String jsessionid = user.getJSESSIONID();
        editor.putString(SESSION_KEY, jsessionid).commit();

    }

    public String getSession() {
        //return user who's logged in the app (JSESSIONID)
        return sharedPreferences.getString(SESSION_KEY, null);
    }

    public void removeSession() {
        editor.putString(SESSION_KEY, null).commit();
    }
}
