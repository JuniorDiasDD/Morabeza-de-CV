package com.example.morabezacv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {
    String idCliente;
    TextView nome, morada, username, tel, senha;
    ArrayList<Usuario> list;
    private DatabaseReference reference;
    Button alterar,voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nome= findViewById(R.id.nome);
        morada= findViewById(R.id.morada);
        username= findViewById(R.id.username);
        senha= findViewById(R.id.senha);
        tel= findViewById(R.id.tel);
        alterar= findViewById(R.id.alterar);
        voltar= findViewById(R.id.voltar);
        Intent i = getIntent();
        idCliente = (String) i.getSerializableExtra("idCliente");
        reference= FirebaseDatabase.getInstance().getReference();
        getConta();
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
    private void getConta(){
        list= new ArrayList<>();
        reference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    list.clear();
                    for(DataSnapshot d : snapshot.getChildren()){
                        Usuario ob = d.getValue(Usuario.class);
                        list.add(ob);

                    }
                    Preencher();
                }else{
                    Log.i("erro:","erro");
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i("Cancela:","erro");
            }
        });
    }
    private void Preencher(){

        for(Usuario u: list){
            if(u.getId().equals(idCliente)){
                nome.setText(u.getNome());
                morada.setText(u.getMorada());
                tel.setText(u.getTel());
                username.setText(u.getEmail());
                senha.setText(u.getSenha());
            }
        }

    }
}