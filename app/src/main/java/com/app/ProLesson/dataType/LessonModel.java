package com.app.ProLesson.dataType;

import java.util.ArrayList;

public class LessonModel {

    private String docente;
    private String corso;
    private String giorno;
    private int orario;
    private String stato; //effettuata, disdetta, attiva
    private int avaiable; //0 = ok , 1 = nope
    private ArrayList<LessonModel> lessons;

    public LessonModel() {
        this.lessons = new ArrayList<>();
    }

    public LessonModel(String corso, String docente, String giorno, int orario, String stato) {
        this.docente = docente;
        this.corso = corso;
        this.giorno = giorno;
        this.orario = orario;
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

    public int getOrario() {
        return orario;
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

    public void setOrario(int orario) {
        this.orario = orario;
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
                ", ora=" + orario +
                ", stato='" + stato + '\'' +
                ", avaible=" + avaiable +
                '}';
    }
}
