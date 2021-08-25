package gian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gian.getcooked.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gian.Model.Ingrediente;

public class AdapterBuscarRecetas extends RecyclerView.Adapter<AdapterBuscarRecetas.myViewHolder> {
    private Context mContext;
    private List<Ingrediente> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onitemClick(int position);
        void onDelateClick(int position);


    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }



    public AdapterBuscarRecetas(Context context, List<Ingrediente> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item_ingredient,parent,false);
        return new myViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Ingrediente currentItem = mData.get(position);
        String title = currentItem.getNombreIngrediente();
        holder.ingrdiente_text.setText(title);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class myViewHolder extends  RecyclerView.ViewHolder{
        TextView ingrdiente_text;
        TextView imagen_delete;
        public myViewHolder(View itemView){
            super(itemView);

            ingrdiente_text = itemView.findViewById(R.id.ingredient_text);
            imagen_delete = itemView.findViewById(R.id.image_delete);

            imagen_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onDelateClick(position);
                        }

                    }

                }
            });

        }
    }



}
