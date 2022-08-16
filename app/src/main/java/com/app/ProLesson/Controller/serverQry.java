package com.app.ProLesson.Controller;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.ProLesson.dataType.LessonModel;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;

public class serverQry {

    private static final String link_getPrenotation = "http://10.0.2.2:8080/TWEB_war_exploded/api/booking";
    private static final String link_avaiableLession = "http://10.0.2.2:8080/TWEB_war_exploded/api/lessongetter";
    private static final String link_logout = "http://10.0.2.2:8080/TWEB_war_exploded/api/logout";
    private static RequestQueue requestQueue;

    public static void setRequestQueue(RequestQueue requestQueue) {
        serverQry.requestQueue = requestQueue;
    }
    //USARE IL METODO
    public static LessonModel requestLessons(String JSESSIONID, int ora, String giorno) {

        LessonModel lessonModel = new LessonModel();
        //Qua uso la libreria volley per interrogare la servlet e fare il login.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                link_avaiableLession,
                response -> {
                    try {
                        JSONArray data = new JSONArray(response);
                        //Mi ritorna la lista di lezioni. La parsifico e me la salvo
                        for(int i=0; i<data.length(); i++) {
                            //corso docente giorno orario state
                            LessonModel newOne = new LessonModel(data.getJSONObject(i).getString("corso"),
                                    data.getJSONObject(i).getString("docente"),
                                    data.getJSONObject(i).getString("giorno"),
                                    data.getJSONObject(i).getInt("orario"),
                                    data.getJSONObject(i).getString("state"));
                            newOne.setAvaiable(data.getJSONObject(i).getInt("avaiable"));
                            lessonModel.addLesson(newOne);
                            System.out.println(lessonModel.getLessons());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println(error.toString());
                }) {

            //Aggiungo i parametri della richiesta
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("JSESSIONID", JSESSIONID);
                params.put("ora", String.valueOf(ora));
                params.put("giorno", giorno);
                return params;
            }

        };
        //non posso aggiungere una string request all'app constest da questa classe.
        //lo ritorno con questa funzione e lo aggiungo quando richiamo la funzione nell'app.
        //Volley.newRequestQueue(this).add(stringRequest);
        //System.out.println(l);
        requestQueue.add(stringRequest);

        return lessonModel;

    }

    public static void logout(String JSESSIONID) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                link_logout,
                response -> {},
                error -> {}
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

}
