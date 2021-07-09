package com.example.morabezacv;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class ModelAdapter extends RecyclerView.Adapter<ModelViewHolder> {
    private List<Model> mList;
    private Context context;
    String idClient;
    ArrayList<Like> list;
Activity activity;
    private DatabaseReference reference;
    public ModelAdapter(Context context, List<Model> menuLis, ArrayList<Like> list, String idClient, Activity activity) {
        this.context = context;
        this.mList = menuLis;
        this.idClient=idClient;
        this.list=list;
        this.activity=activity;

    }
    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        ModelViewHolder itentHolder = new ModelViewHolder(view);
        return itentHolder;
    }

    @Override
    public void onBindViewHolder(ModelViewHolder holder, int position)  {
        final Model item = mList.get(position);
        Log.e("dados","-- : "+item.getImagem());
        holder.titulo.setText(item.getTitulo());
        holder.data.setText(item.getData());
        holder.like.setText(item.getLike());
        holder.comentario.setText(item.getComent());
        holder.descricao.setText(item.getDescricao());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Like lk: list){
                    if(lk.getClient().equals(idClient) && lk.getConteudo().equals(String.valueOf(item.getId()))){
                        holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.like));
                    }
                }
            }
        });

        Picasso.get()
                .load(item.getImagem())
                .into(holder.images);

       // holder.images.setImageURI(item.getImagem());
        holder.favorite.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int validar=1;
                for(Like lk: list){
                    if(lk.getClient().equals(idClient) && lk.getConteudo().equals(String.valueOf(item.getId()))){
                        holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24));
                        int cont= Integer.parseInt(item.getLike())-1;
                        item.setLike(String.valueOf(cont));
                        item.UpdateDados();
                        lk.deleteDados();
                        validar=0;
                    }
                }
                if(validar==1){

                    int cont= Integer.parseInt(item.getLike())+1;
                    item.setLike(String.valueOf(cont));
                    item.UpdateDados();
                    Like like= new Like();
                    like.setClient(idClient);
                    like.setConteudo(String.valueOf(item.getId()));
                    like.salvarDados();
                    holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.like));
                }
            }
        });
        holder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"Play Ativado Com Sucesso",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static Bitmap converttobitmap(String imageString) {
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;

    }
    public void setData(List<Model> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
