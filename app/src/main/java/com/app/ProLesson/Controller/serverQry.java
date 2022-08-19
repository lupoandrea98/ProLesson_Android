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
    private static final String link_booking = "http://10.0.2.2:8080/TWEB_war_exploded/api/booking";
    private static RequestQueue requestQueue;
    private static Context application_context = null;
    /*public static void setRequestQueue(RequestQueue requestQueue) {
        serverQry.requestQueue = requestQueue;
    }
     */

    public static void setContext(Context context) {
        application_context = context;
    }

    public static void logout(String JSESSIONID) {
        requestQueue = Volley.newRequestQueue(application_context);
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

    public static void bookingLesson(LessonModel lesson, String JSESSIONID) {

        //TODO: Devo trasformare lesson in un oggetto json altrimenti il backend non può tradurlo
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                link_booking,
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
