package com.app.ProLesson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.app.ProLesson.dataType.LessonModel;

import java.util.ArrayList;
import java.util.List;

public class MyLessonsAdapter extends RecyclerView.Adapter<MyLessonsAdapter.ViewHolder> {

    private List<LessonModel> lessons;
    //private Context context;

    public MyLessonsAdapter(LessonModel lessonObj) {
        //this.context = context;
        this.lessons = lessonObj.getLessons();
    }

    //faccio l'inflate del layout definito in xml inserendolo nel viewholder
    //grazie al quale riusciamo a richiamare i vari componenti della vista.

    //potrei fare la query al backend qui nella create del viewolder ---> NO, al massimo nel costruttore dell'adapter
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
        return new ViewHolder(v);
    }

    //imposto gli oggetti presi dalla lista popolata delle lezioni.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Serve ad inizializzare gli oggetti definiti nel ViewHolder qui sotto.
        //Ad esempio prendo dalla lista delle lezioni quella della determinata posizione nella lista.

        //inutile occupare altra memoria se posso accedervici direttamente dalla classe lessons
        LessonModel lesson = this.lessons.get(position);
        //Lessons lesson = Lessons.lessons.get(position);
        holder.corso.setText(lesson.getCorso());
        holder.docente.setText(lesson.getDocente());
        holder.giorno.setText(lesson.getGiorno());
        holder.orario.setText(Integer.toString(lesson.getOra()));

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Qua dentro ci va la roba presente nel view del fragment, tutti i riferimenti agli id dei vari componenti che vado ad usare
        private TextView corso;
        private TextView docente;
        private TextView giorno;
        private TextView orario;

        public ViewHolder(View itemView) {
            super(itemView);
            //Usando la view passata recupero i componenti con il solito findByView(R.id.....
            corso = itemView.findViewById(R.id.nCorso);
            docente = itemView.findViewById(R.id.nDoc);
            giorno = itemView.findViewById(R.id.nGiorno);
            orario = itemView.findViewById(R.id.nOrario);
        }
    }

    //TODO: ho bisogno di un metodo che mi faccia un update dell'oggetto lessonObj quando viene richiamata la query dalla HomeActivity
    public void setNewLessons(LessonModel lessonsObj) {
        this.lessons = new ArrayList<>(lessonsObj.getLessons());
    }
}