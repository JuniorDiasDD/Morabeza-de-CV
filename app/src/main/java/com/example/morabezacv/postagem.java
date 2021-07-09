package com.example.morabezacv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;


public class postagem extends AppCompatActivity {

    private EditText titulo;
    private EditText descricao;
    private EditText imagem;
    private Button gravar,cancelar;
    Model m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);
        titulo=findViewById(R.id.titulo);
        descricao=findViewById(R.id.descricao);
        imagem=findViewById(R.id.imagem);
        cancelar=findViewById(R.id.cancelar);
        gravar=findViewById(R.id.gravar);
        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarDados();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    private void recuperarDados() {
        if (titulo.getText().length() == 0 || imagem.getText().length() == 0 || descricao.getText().length() == 0) {
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
        } else {
            m = new Model();
            m.setTitulo(titulo.getText().toString());
            m.setDescricao(descricao.getText().toString());
            m.setImagem(imagem.getText().toString());
            m.setLike("0");
            m.setComent("0");
            SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
            Date dat=new Date();
            String dataFormatada = formataData.format(dat);
            m.setData(dataFormatada);
            m.salvarDados();
            new AlertDialog.Builder(this)
                    .setTitle("Registado com sucesso")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    })
                    .setIcon(android.R.drawable.checkbox_on_background)
                    .show();


        }
    }
}