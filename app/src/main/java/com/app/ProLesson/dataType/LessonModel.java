package com.app.ProLesson.dataType;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LessonModel {

    private String link_getPrenotation = "http://10.0.2.2:8080/TWEB_war_exploded/api/booking";
    private String link_avaiableLession = "http://10.0.2.2:8080/TWEB_war_exploded/api/lessongetter";

    private String docente;
    private String corso;
    private String giorno;
    private int ora;
    private String stato; //effettuata, disdetta, attiva
    private int avaiable; //0 = ok , 1 = nope
    private ArrayList<LessonModel> lessons;

    public LessonModel() {
        this.lessons = new ArrayList<>();
    }

    public LessonModel(String corso, String docente, String giorno, int ora, String stato) {
        this.docente = docente;
        this.corso = corso;
        this.giorno = giorno;
        this.ora = ora;
        this.stato = stato;
    }

    public String getDocente() {
        return docente;
    }

    public String getCorso() {
        return corso;
    }

    public String getGiorno() {
        return giorno;
    }

    public int getOra() {
        return ora;
    }

    public String getStato() {
        return stato;
    }

    public ArrayList<LessonModel> getLessons() { return this.lessons; }

    public int getAvaible() {
        return avaiable;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public void setCorso(String corso) {
        this.corso = corso;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setAvaiable(int avaiable) {
        this.avaiable = avaiable;
    }

    public void addLesson(LessonModel newOne) {
        this.lessons.add(newOne);
    }

    @Override
    public String toString() {
        return "Lessons{" +
                "docente='" + docente + '\'' +
                ", corso='" + corso + '\'' +
                ", giorno='" + giorno + '\'' +
                ", ora=" + ora +
                ", stato='" + stato + '\'' +
                ", avaible=" + avaiable +
                '}';
    }
}
