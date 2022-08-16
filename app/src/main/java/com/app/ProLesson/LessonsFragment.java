package com.app.ProLesson;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.app.ProLesson.Controller.SessionManager;
import com.app.ProLesson.Controller.serverQry;
import com.app.ProLesson.dataType.LessonModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class LessonsFragment extends Fragment {

    private LessonModel lessonObj;
    private TabItem selectDay;
    private TabLayout selectableDays;
    private HomeActivity homeActivity;
    private String giorno;
    private String jsessionid;

    public LessonsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivity = (HomeActivity) getActivity();
        selectableDays = homeActivity.findViewById(R.id.tabs);
        giorno = "Lun";
        SessionManager sessionManager = new SessionManager(getContext());
        jsessionid = sessionManager.getSession();
        lessonObj = serverQry.requestLessons(jsessionid, giorno);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        /*
        //*************************************************************************************************************
        lessonObj = new LessonModel();
        LessonModel a1 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a2 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a3 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a4 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a5 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a6 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a7 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        LessonModel a8 = new LessonModel("Matematica", "Cannavò", "Lun", 15, "attiva");
        lessonObj.addLesson(a1);
        lessonObj.addLesson(a2);
        lessonObj.addLesson(a3);
        lessonObj.addLesson(a4);
        lessonObj.addLesson(a5);
        lessonObj.addLesson(a6);
        lessonObj.addLesson(a7);
        lessonObj.addLesson(a8);

        //*************************************************************************************************************


         */

        // Set the adapter and layoutmanager
        MyLessonsAdapter mAdapter = new MyLessonsAdapter(this.lessonObj);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(mAdapter);

        selectableDays.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //richiamo la query per quel determinato giorno
                switch (tab.getPosition()){
                    case 0:             //Lun
                        giorno = "Lun";
                        break;
                    case 1:             //Mar
                        giorno = "Mar";
                        break;
                    case 2:             //Mer
                        giorno = "Mer";
                        break;
                    case 3:             //Gio
                        giorno = "Gio";
                        break;
                    case 4:             //Ven
                        giorno = "Ven";
                        break;
                }
                lessonObj = serverQry.requestLessons(jsessionid, giorno);
                mAdapter.setNewLessons(lessonObj);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}