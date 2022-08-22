package com.app.ProLesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.ProLesson.dataType.LessonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrenotationFragment extends Fragment {

    private LessonModel prenotationObj;
    private String giorno;
    private String jsessionid;
    private final String link_prenotation = "http://10.0.2.2:8080/TWEB_war_exploded/api/booking";
    private String state;

    public PrenotationFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prenotationObj = new LessonModel();
        if(getArguments() != null) {
            giorno = getArguments().getString("giorno");
            jsessionid = getArguments().getString("sessionid");
            state = getArguments().getString("state");
        }else
            System.out.println("Fragment's arguments are null");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //qui va la query e il set dell'adapter
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                link_prenotation,
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
                            //Posso effettuare qui una divisione.
                            switch (state) {
                                case "tutte":
                                    prenotationObj.addLesson(newOne);
                                    break;
                                case "effettuate":
                                    if(newOne.getStato().equals("effettuata"))
                                        prenotationObj.addLesson(newOne);
                                    break;
                                case "attive":
                                    if(newOne.getStato().equals("attiva"))
                                        prenotationObj.addLesson(newOne);
                                    break;
                                case "disdette":
                                    if(newOne.getStato().equals("disdetta"))
                                        prenotationObj.addLesson(newOne);
                                    break;
                            }

                        }
                        MyPrenotationAdapter mAdapter = new MyPrenotationAdapter(prenotationObj, jsessionid);
                        recyclerView.setAdapter(mAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Errore di connessione \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {

            //Aggiungo i parametri della richiesta
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("JSESSIONID", jsessionid);
                params.put("action", "bookedAndroid");
                params.put("giorno", giorno);
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
        return view;
    }
}
