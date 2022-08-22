package com.app.ProLesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ProLesson.Controller.serverQry;
import com.app.ProLesson.dataType.LessonModel;

import java.util.List;

public class MyPrenotationAdapter extends RecyclerView.Adapter<MyPrenotationAdapter.ViewHolder>{

    private List<LessonModel> prenotations;
    private String jsessionid;


    public MyPrenotationAdapter(LessonModel prenotationObj, String jsession) {
        this.prenotations = prenotationObj.getLessons();
        this.jsessionid = jsession;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prenotation_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LessonModel prenotation = this.prenotations.get(position);
        holder.corso.setText(prenotation.getCorso());
        holder.docente.setText(prenotation.getDocente());
        holder.orario.setText(String.valueOf(prenotation.getOrario()));
        holder.giorno = prenotation.getGiorno();
        holder.stato.setText(prenotation.getStato());
        holder.session = jsessionid;
        if(!String.valueOf(holder.stato.getText()).equals("attiva")) {
            holder.del_button.setVisibility(View.GONE);
            holder.done_button.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return prenotations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView corso;
        private TextView docente;
        private TextView orario;
        private Button del_button;
        private Button done_button;
        private String giorno;
        private TextView stato;
        private String session;

        public ViewHolder(View itemView) {
            super(itemView);
            corso = itemView.findViewById(R.id.nCorso);
            docente = itemView.findViewById(R.id.nDoc);
            orario = itemView.findViewById(R.id.nOrario);
            del_button = itemView.findViewById(R.id.set_as_cancel);
            done_button = itemView.findViewById(R.id.set_as_done);
            stato = itemView.findViewById(R.id.nStato);

            del_button.setOnClickListener(view -> {
                LessonModel prenotation = new LessonModel(String.valueOf(corso.getText()), String.valueOf(docente.getText()), giorno,
                        Integer.parseInt(String.valueOf(orario.getText())), String.valueOf(stato.getText()));
                serverQry.changeState(prenotation, session, "dismiss");
            });

            done_button.setOnClickListener( view -> {
                LessonModel prenotation = new LessonModel(String.valueOf(corso.getText()), String.valueOf(docente.getText()),
                        giorno, Integer.parseInt(String.valueOf(orario.getText())), String.valueOf(stato.getText()));
                serverQry.changeState(prenotation, session, "done");
            });
        }
    }
}
