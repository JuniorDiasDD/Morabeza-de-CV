package com.example.morabezacv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etSenha;
    private Button btLogar,btRegisto;
    private FirebaseAuth mAuth;
    private Usuario u;
    private String dado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.email);
        etSenha = findViewById(R.id.passwordL);
        btLogar = findViewById(R.id.login);
        btRegisto= findViewById(R.id.registar);

        btRegisto.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent registo = new Intent(getApplicationContext(),Registo.class);
               startActivity(registo);
            }
        });


        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receberDados();
                logar();
            }
        });
    }


    private void logar() {
        mAuth.signInWithEmailAndPassword(u.getEmail(), u.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference firebase = ConfFirebase.getFirebaseDatabase();
                            firebase = firebase.child("Usuarios").child(user.getUid());
                            firebase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    dado = snapshot.getValue().toString();
                                    if(dado.equals("true")){

                                    }
                                    Log.i("LOCAL",dado);
                                    Intent intent = new Intent(MainActivity.this,feeds.class);
                                    intent.putExtra("idCliente",user.getUid());
                                    startActivity(intent);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            //Log.i("LOCAL","Professor: "+professor);
                            //startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Autenticação falhou.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void receberDados() {
        u = new Usuario();
        u.setEmail(etEmail.getText().toString());
        u.setSenha(etSenha.getText().toString());
    }
}