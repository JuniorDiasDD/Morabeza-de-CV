package com.example.morabezacv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private EditText etSenha,morada,tel;
    private Button btCadastrar,cancelar;
    private FirebaseAuth mAuth;
    private Usuario u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        etNome = findViewById(R.id.usernameR);
        etEmail = findViewById(R.id.emailR);
        tel = findViewById(R.id.tel);
        morada = findViewById(R.id.morada);
        etSenha = findViewById(R.id.passwordL);
        btCadastrar = findViewById(R.id.registarBtn);
        cancelar=findViewById(R.id.cancelar);
        mAuth = FirebaseAuth.getInstance();


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  recuperarDados();
                criarLogin();
            }
        });


    }

    private void criarLogin() {

        if (etNome.getText().length() == 0 || etEmail.getText().length() == 0 || etSenha.getText().length() == 0 || morada.getText().length() == 0 || tel.getText().length() == 0) {

            new AlertDialog.Builder(this)
                    .setTitle("Dados em Falta")
                    .setMessage("Por Favor preencha todos os campos")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            u = new Usuario();
            u.setNome(etNome.getText().toString());
            u.setEmail(etEmail.getText().toString());
            u.setSenha(etSenha.getText().toString());
            u.setTel(tel.getText().toString());
            u.setMorada(morada.getText().toString());

            mAuth.createUserWithEmailAndPassword(u.getEmail(),u.getSenha())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                u.setId(user.getUid());
                                u.salvarDados();
                                Intent intent = new Intent(Registo.this,feeds.class);
                                intent.putExtra("idCliente",user.getUid());
                                startActivity(intent);
                            }else{
                                Toast.makeText(Registo.this,"Erro ao criar um login. obs:Password min 7 caractere ou conta existente",Toast.LENGTH_SHORT).show();

                            }
                        }

                    });
        }
    }
}