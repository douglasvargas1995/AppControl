package com.example.appcontrolautentication.model;

import com.example.appcontrolautentication.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private boolean operador;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isOperador() {
        return operador;
    }

    public void setOperador(boolean operador) {
        this.operador = operador;
    }

    public void salvarDados() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("Usuarios").child(this.id).setValue(this);
    }
}
