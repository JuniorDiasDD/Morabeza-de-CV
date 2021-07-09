package com.example.morabezacv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class feeds extends AppCompatActivity {
    RecyclerView recyclerViewModel;
    RecyclerView.LayoutManager layoutManagerModel;
     ModelAdapter ModelAdapter;
    ArrayList<Model> list;
    ArrayList<Like> lista;
    private DatabaseReference reference;
    Button add, perfil,logout;
    String idCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        recyclerViewModel = findViewById(R.id.recyclerViewModel);
        add= findViewById(R.id.add);
        logout= findViewById(R.id.logout);
        perfil=findViewById(R.id.perfil);

        reference= FirebaseDatabase.getInstance().getReference();
        getMarkers();
        getLike();
        Intent i = getIntent();
        idCliente = (String) i.getSerializableExtra("idCliente");
        Log.i("IDCLIENTE:",idCliente);


        add.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feeds.this,postagem.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        perfil.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feeds.this,Perfil.class);
                intent.putExtra("idCliente",idCliente);
                startActivity(intent);
            }
        });

      //  Model nv= new Model("Cidade Velha","02/Junho/2021","data:image/jpeg;base64,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");
   /*Model nv=new Model();
   nv.setTitulo("Praia");
   nv.setData("20/06/2021");
     nv.setImagem("https://i.pinimg.com/236x/57/9d/bb/579dbbc56686fba7cc6380546097b1d1--praia-brava-verde-island.jpg");
     nv.setComent("0");
     nv.setDescricao("nkfcsfs");
     nv.setLike("0");
     nv.salvarDados();
        Model nvv=new Model();
        nvv.setTitulo("Cidade Velha");
        nvv.setData("18/06/2021");
        nvv.setDescricao("nkfcsfs");
        nvv.setImagem("https://mediaim.expedia.com/destination/1/903f497cfda925311336f2a83f0ad244.jpg?impolicy=fcrop&w=536&h=384&q=high");
        nvv.setComent("0");
        nvv.setLike("0");
        nvv.salvarDados();
        Model n=new Model();
        n.setTitulo("Tarrafal");
        n.setDescricao("nkfcsfs");
        n.setData("18/06/2021");
        n.setImagem("https://media-manager.noticiasaominuto.com/1920/naom_606439f837f5e.jpg");
        n.setComent("0");
        n.setLike("0");
        n.salvarDados();*/
    }
    public void Preencher(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerViewModel.setHasFixedSize(true);
                layoutManagerModel =new LinearLayoutManager(feeds.this, LinearLayoutManager.VERTICAL, false);
                recyclerViewModel.setLayoutManager(layoutManagerModel);
                ModelAdapter = new ModelAdapter(feeds.this,list,lista,idCliente,feeds.this);
                recyclerViewModel.setAdapter(ModelAdapter);
            }
        });
    }
    private void getLike(){
        lista= new ArrayList<>();
        reference.child("Like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    lista.clear();
                    for(DataSnapshot d : snapshot.getChildren()){
                        Like ob = d.getValue(Like.class);
                        lista.add(ob);
                        Log.i("LikeCarregou:",ob.getClient());
                    }
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
    private void getMarkers(){
        list= new ArrayList<>();
        reference.child("Conteudo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    list.clear();
                    for(DataSnapshot d : snapshot.getChildren()){
                        Model ob = d.getValue(Model.class);
                        list.add(ob);
                        Log.i("Imagem:",ob.getImagem());
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
}