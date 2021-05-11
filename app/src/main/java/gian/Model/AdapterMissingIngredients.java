package gian.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gian.getcooked.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMissingIngredients extends RecyclerView.Adapter<AdapterMissingIngredients.myViewHolder> {

    private Context mContext;
    private ArrayList<String>mData;

    public AdapterMissingIngredients(Context mContext, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdapterMissingIngredients.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.missing_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        String imageUrl = mData.get(position);
        Picasso.get().
                load(imageUrl).
                fit()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder{
        ImageView image;

        public myViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.imagen_ingr_missing);
        }
    }
}
