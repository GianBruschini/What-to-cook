package gian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gian.getcooked.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gian.Model.item;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {
    private Context mContext;
    private List<item> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onitemClick(int position);


    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }



    public Adapter(Context context, List<item> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.card_item,parent,false);
            return  new myViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        item currentItem = mData.get(position);
        String imageUrl = currentItem.getBackground();
        String title = currentItem.getTitle();
        holder.tv_title.setText(title);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.background_image);

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
            background_image = itemView.findViewById(R.id.card_background);
            tv_title = itemView.findViewById(R.id.textoTitulo);
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
