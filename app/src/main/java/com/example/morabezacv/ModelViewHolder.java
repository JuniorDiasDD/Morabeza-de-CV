package com.example.morabezacv;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ModelViewHolder extends RecyclerView.ViewHolder  {
    public View contentLayout;
    public TextView btname, favorite;
    public TextView titulo;
    public TextView data,like,comentario,descricao;
    public ImageView images;



    public ModelViewHolder(View itemView) {
        super(itemView);
        contentLayout = itemView.findViewById(R.id.layout_content);
        titulo = itemView.findViewById(R.id.titulo);
        data = itemView.findViewById(R.id.data);
        like = itemView.findViewById(R.id.like);
        comentario = itemView.findViewById(R.id.comentario);
        descricao = itemView.findViewById(R.id.descricao);
        favorite = itemView.findViewById(R.id.favorito);
        images = itemView.findViewById(R.id.image);


    }

}
