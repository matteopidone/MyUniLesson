package com.MyUniLesson.app.domain;

public class Studente {
    private String matricola;
    private String nome;
    private String cognome;
    private String email;

    public Studente(String matricola, String nome, String cognome, String email) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    //Getters and Setters

    public String getMatricola() {
        return matricola;
    }

    public String getEmail() {
        return email;
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
