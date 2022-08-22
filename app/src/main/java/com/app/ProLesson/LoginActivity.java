package com.app.ProLesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.ProLesson.Controller.SessionManager;
import com.app.ProLesson.Controller.serverQry;
import com.app.ProLesson.dataType.LessonModel;
import com.app.ProLesson.dataType.User;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText username;
    TextInputEditText password;
    Button login_button;
    private final String servlet_login = "http://10.0.2.2:8080/TWEB_war_exploded/api/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //serverQry.setRequestQueue(Volley.newRequestQueue(getApplicationContext()));
        serverQry.setContext(LoginActivity.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = findViewById(R.id.editTextUser);
        password = findViewById(R.id.editTextPw);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(view -> {
            //Faccio un controllo sui parametri
            if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                Toast.makeText(this, "Campi vuoti", Toast.LENGTH_SHORT).show();
                return;
            }
            //test();
            loginPost();
            //launch_home();

        });

    }

    private void loginPost() {
        //Qua uso la libreria volley per interrogare la servlet e fare il login.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                servlet_login,
                response -> {
                    try {
                        JSONArray data = new JSONArray(response);
                        if(data.getBoolean(0)) {
                            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                            //Creo un nuovo utente al cui interno salvo i parametri che mi arrivano dal server
                            User user = new User(data.getJSONObject(1).getInt("id"), data.getJSONObject(1).getString("account"),
                                    data.getJSONObject(1).getString("psw"), data.getJSONObject(1).getInt("isAdmin"));
                            user.setJSESSIONID(data.getString(2));
                            //*************************
                            //System.out.println("Controllo utente post login " + user);   //controllo che prenda tutto correttamente.
                            //*************************
                            //Creo la nuova sessione utente tramite il SessionManager e ci salvo l'utente creato sopra
                            SessionManager sessionManager = new SessionManager(LoginActivity.this);
                            sessionManager.saveSession(user);
                            //Lancio l'activity della homepage.
                            launch_home();
                        }else
                            Toast.makeText(this, "Utente o password errati", Toast.LENGTH_SHORT).show();
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Errore di connessione \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

                    //Aggiungo i parametri della richiesta
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("account", username.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }

                };

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void launch_home() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*private void test() {
        LessonModel lessons = new LessonModel();

        StringRequest strRequest = lessons.requestLessons("", 15, "Lun");
        Volley.newRequestQueue(this).add(strRequest);
        for(LessonModel l : this.lessons) {
            System.out.println(l);
        }

    }*/

}
