package gian.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gian.getcooked.R;

import java.util.List;

import SpoonacularClases.SpoonacularItemToShow;
import gian.Interfaces.SpoonacularApi;

public class AdapterSpoonacularInfo extends RecyclerView.Adapter<AdapterSpoonacularInfo.myViewHolder> {
    private Context mContext;
    private List<SpoonacularItemToShow> mData;

    private Adapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onitemClick(int position);

    }
    public void setOnItemClickListener(Adapter.OnItemClickListener listener){
        mListener = listener;

    }
    public AdapterSpoonacularInfo(Context mContext, List<SpoonacularItemToShow> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        SpoonacularItemToShow currentItem = mData.get(position);
        String imageUrl = currentItem.getBackground();
        String title = currentItem.getTitle();
        holder.tv_title.setText(title);
        Glide.with(mContext)
                .load(imageUrl)
                .fitCenter()
                .centerInside()
                .into(holder.background_image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends  RecyclerView.ViewHolder{

        ImageView background_image;
        TextView tv_title;
        public myViewHolder(View itemView){
            super(itemView);
            tv_title = itemView.findViewById(R.id.textoTitulo);
            background_image = itemView.findViewById(R.id.card_background);

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
