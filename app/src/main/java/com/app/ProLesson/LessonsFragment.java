package com.app.ProLesson;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.ProLesson.dataType.LessonModel;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class LessonsFragment extends Fragment {

    private LessonModel lessonObj;
    private String giorno;
    private String jsessionid;
    private final String link_avaiableLession = "http://10.0.2.2:8080/TWEB_war_exploded/api/lessongetter";

    public LessonsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lessonObj = new LessonModel();
        if(getArguments() != null) {
            giorno = getArguments().getString("giorno");
            jsessionid = getArguments().getString("sessionid");
        }else
            System.out.println("Fragment's arguments are null");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

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
                            lessonObj.addLesson(newOne);
                        }
                        MyLessonsAdapter mAdapter = new MyLessonsAdapter(this.lessonObj, this.jsessionid);
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
                params.put("giorno", giorno);
                return params;
            }

        };

        Volley.newRequestQueue(getContext()).add(stringRequest);

        return view;
    }
}