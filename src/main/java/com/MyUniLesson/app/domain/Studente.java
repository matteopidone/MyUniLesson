package com.MyUniLesson.app.domain;

public class Studente {
    private String matricola;
    private String nome;
    private String cognome;

    public Studente(String matricola, String nome, String cognome) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    @Override
    public String toString() {
        return "Studente{" +
                "matricola='" + matricola + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }
}
