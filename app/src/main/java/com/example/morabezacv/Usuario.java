package com.example.morabezacv;

import com.google.firebase.database.DatabaseReference;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha,morada,tel;

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

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


    public void salvarDados() {
        DatabaseReference firebase = ConfFirebase.getFirebaseDatabase();
        DatabaseReference utilizadorRef = firebase.child( "Usuarios" ).child( getId() );

        utilizadorRef.setValue( this );
    }


}


