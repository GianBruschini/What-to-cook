package WhatToCook.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.getcooked.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.myViewHolder> {
    private Context mContext;
    private List<item> mData;


    private AdapterMenu.OnItemClickListener mListener;
    private List<item>actualList;

    public interface OnItemClickListener{
        void onitemClick(int position,List<item>unaLista);


    }
    public void setOnItemClickListener(AdapterMenu.OnItemClickListener listener,List<item>aList){
        mListener = listener;
        actualList = aList;


    }










    public AdapterMenu(Context context, List<item> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    public AdapterMenu.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_item_menu,parent,false);
        return new myViewHolder(v);


    }


    public void onBindViewHolder(@NonNull AdapterMenu.myViewHolder holder, int position) {
        item currentItem = mData.get(position);
        String imageUrl = currentItem.getBackground();
        String title = currentItem.getTitle();
        holder.tv_title.setText(title);
        Glide.with(mContext)
                .load(imageUrl)
                .fitCenter()
                .centerInside()
                .into(holder.background_image);

            System.out.println("Titulo es : " + " " + title);

        /*Image image = ImageIO.read(new URL(url1));
        if(image != null){
            System.out.println("IMAGE");
        }else{
            System.out.println("NOT IMAGE");
        }

         */

    }



    public int getItemCount() {
        return mData.size();
    }


    public class myViewHolder extends  RecyclerView.ViewHolder{
        ImageView background_image;
        TextView tv_title;
        public myViewHolder(View itemView){
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            background_image = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onitemClick(position,actualList);
                        }
                    }

                }
            });

        }
    }
}
