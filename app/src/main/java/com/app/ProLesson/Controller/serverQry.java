package com.app.ProLesson.Controller;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.ProLesson.dataType.LessonModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class serverQry {

    private static final String link_logout = "http://10.0.2.2:8080/TWEB_war_exploded/api/logout";
    private static final String link_booking = "http://10.0.2.2:8080/TWEB_war_exploded/api/booking";
    private static RequestQueue requestQueue;
    private static Context application_context = null;

    public static void setContext(Context context) {
        application_context = context;
    }

    public static void logout(String JSESSIONID) {
        requestQueue = Volley.newRequestQueue(application_context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                link_logout,
                response -> {},
                error -> {
                    Toast.makeText(application_context, "Errore di connessione \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            //Aggiungo i parametri della richiesta
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("JSESSIONID", JSESSIONID);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    public static void bookingLesson(LessonModel lesson, String JSESSIONID) {
        requestQueue = Volley.newRequestQueue(application_context);
        Gson gson = new Gson();
        ArrayList<LessonModel> array = new ArrayList<>();
        array.add(lesson);
        String bookedLesson = gson.toJson(array);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                link_booking,
                response -> {
                    try {
                        if(Boolean.parseBoolean(response.substring(1, response.length()-3)))
                            Toast.makeText(application_context, "Prenotazione effettuata con successo", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(application_context, "Errore nell'effettuare la prenotazione", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(application_context, "Errore di connessione \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            //Aggiungo i parametri della richiesta
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("JSESSIONID", JSESSIONID);
                params.put("booking", bookedLesson);
                params.put("action", "booking");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    //sempre su /booking e le action sono "dismiss" e "done"
    //mi servono sempre jsessionid e l'array con le lezioni da cambiare

    public static void changeState(LessonModel prenotation, String JSESSIONID, String action) { //action --> "dismiss"/"done"
        requestQueue = Volley.newRequestQueue(application_context);
        Gson gson = new Gson();
        ArrayList<LessonModel> array = new ArrayList<>();
        array.add(prenotation);
        String changedPrenotation = gson.toJson(array);
        System.out.println("changedPrenotation " + changedPrenotation);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                link_booking,
                response -> {
                    try {
                        if(Boolean.parseBoolean(response.substring(0, response.length()-2)))
                            Toast.makeText(application_context, "Operazione effettuata con successo", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(application_context, "Errore nell'effettuare l'operazione", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(application_context, "Errore di connessione \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            //Aggiungo i parametri della richiesta
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("JSESSIONID", JSESSIONID);
                params.put("booking", changedPrenotation);
                params.put("action", action);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



}
