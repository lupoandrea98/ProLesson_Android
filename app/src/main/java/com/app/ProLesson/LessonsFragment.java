package com.app.ProLesson;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
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

    public LessonsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: e devo pure prendere giorno e ora.
        //TODO: occhio che nella lista ci dovrebbero essere tutte le lezioni e non solo quelle specificate



        lessonObj = serverQry.requestLessons("", 15, "Lun");
        System.out.println("fragment" + lessonObj.getLessons());



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
        lessonObj.addLesson(a1);
        lessonObj.addLesson(a2);
        lessonObj.addLesson(a3);
        lessonObj.addLesson(a4);
        //*************************************************************************************************************


         */
        // Set the adapter and layoutmanager
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new MyLessonsAdapter(this.lessonObj));

        return view;
    }
}