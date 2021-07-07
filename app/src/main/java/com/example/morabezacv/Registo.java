package com.example.morabezacv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registo extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private Button btCadastrar;
    private FirebaseAuth mAuth;
    private Usuario u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        etNome = findViewById(R.id.usernameR);
        etEmail = findViewById(R.id.emailR);
        etSenha = findViewById(R.id.passwordL);
        btCadastrar = findViewById(R.id.registarBtn);
        mAuth = FirebaseAuth.getInstance();



        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarDados();
                criarLogin();
            }
        });


    }

    private void criarLogin() {
        mAuth.createUserWithEmailAndPassword(u.getEmail(),u.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            u.setId(user.getUid());
                            u.salvarDados();
                            startActivity(new Intent(Registo.this,feeds.class));
                        }else{
                            Toast.makeText(Registo.this,"Erro ao criar um login. obs:Password min 7 caractere ou conta existente",Toast.LENGTH_SHORT).show();

                        }
                         }

                });
        if (etNome.getText().toString() == "" || etEmail.getText().toString() == "" || etSenha.getText().toString() == "") {
            Toast.makeText(this, "Você deve preencher todos os dados", Toast.LENGTH_LONG);
        }
    }



    private void recuperarDados() {
        if (etNome.getText().toString() == "" || etEmail.getText().toString() == "" || etSenha.getText().toString() == "") {
            Toast.makeText(this, "Você deve preencher todos os dados", Toast.LENGTH_LONG);
        } else {
            u = new Usuario();
            u.setNome(etNome.getText().toString());
            u.setEmail(etEmail.getText().toString());
            u.setSenha(etSenha.getText().toString());

        }
    }}