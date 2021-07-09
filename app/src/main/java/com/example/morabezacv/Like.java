package com.example.morabezacv;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Like {
    String client, conteudo;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    public void salvarDados() {
        int val= (int) new Date().getTime();
        DatabaseReference firebase = ConfFirebase.getFirebaseDatabase();
        DatabaseReference utilizadorRef = firebase.child( "Like").child(String.valueOf(val));
        this.setId(val);
        utilizadorRef.setValue( this );
    }
    public void deleteDados() {
        DatabaseReference firebase = ConfFirebase.getFirebaseDatabase();
        DatabaseReference utilizadorRef = firebase.child( "Like");
        utilizadorRef.child(String.valueOf(this.getId())).removeValue();
    }
}
