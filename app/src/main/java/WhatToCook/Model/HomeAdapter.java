package WhatToCook.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getcooked.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {

    private Context context;
    private List<Comida_detail_cardViewHome> mData;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onitemClick(int position);


    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }

    public HomeAdapter(Context context, List<Comida_detail_cardViewHome> mData) {
        this.context = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comida_detail_cardViewHome currentItem = mData.get(position);
        holder.nombre_comida.setText(currentItem.getNombre_comidaHome());
        Double calorias = Double.parseDouble(currentItem.getCalorias_comidaHome());
        holder.calorias_comida.setText(String.valueOf(calorias.intValue()) + " " + "calorías");
        Picasso.get().load(currentItem.getImagen_comidaHome()).into(holder.imagen_comida);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagen_comida;
        TextView nombre_comida;
        TextView calorias_comida;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen_comida = itemView.findViewById(R.id.imagen_comida);
            nombre_comida = itemView.findViewById(R.id.nombreComidaTextCard);
            calorias_comida = itemView.findViewById(R.id.caloriasTextCard);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onitemClick(position);
                        }
                    }

                }
            });

        }
    }
}
