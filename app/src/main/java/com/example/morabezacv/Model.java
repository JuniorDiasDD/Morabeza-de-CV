package com.example.morabezacv;


import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Date;

public class Model {
    String titulo,data;
    String imagem,like,coment,descricao;
    int id;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void salvarDados() {
        int val= (int) new Date().getTime();
        DatabaseReference firebase = ConfFirebase.getFirebaseDatabase();
        DatabaseReference utilizadorRef = firebase.child( "Conteudo" ).child(String.valueOf(val));
        this.setId(val);
        utilizadorRef.setValue( this );
    }
    public void UpdateDados() {

        DatabaseReference firebase = ConfFirebase.getFirebaseDatabase();
        DatabaseReference utilizadorRef = firebase.child( "Conteudo" ).child(String.valueOf(this.getId()));
        utilizadorRef.setValue( this );
    }
}
