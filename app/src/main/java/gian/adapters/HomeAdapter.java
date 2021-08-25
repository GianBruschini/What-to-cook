package gian.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gian.getcooked.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import gian.Model.item;

import static android.content.Context.MODE_PRIVATE;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {

    private Context context;
    private List<item> mData;
    private OnItemClickListener mListener;
    private String key;
    private String keyComida;


    public interface OnItemClickListener{
        void onitemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public HomeAdapter(Context context, List<item> mData) {
        this.context = context;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        item currentItem = mData.get(position);
        checkearFavoritosEnLaBaseYsetearIconoFav(position,currentItem.getTitle(),holder);
        holder.nombre_comida.setText(currentItem.getTitle());
        double calorias = Double.parseDouble(String.valueOf(currentItem.getCalories()));
        holder.calorias_comida.setText((int) calorias + " " + "calorías");
        Picasso.get().load(currentItem.getBackground()).into(holder.imagen_comida);
    }

    private void checkearFavoritosEnLaBaseYsetearIconoFav(int position, String nombre_comidaHome, final MyViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        key = checkearSharedPreference();
        if(key!=null){
            Query applesQuery = reference.child("RecetasFavoritas").child(key).orderByChild("nombre").equalTo(nombre_comidaHome);
            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        if(appleSnapshot.exists()){
                            holder.favourite_button.setChecked(true);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    private String checkearSharedPreference() {


        SharedPreferences prefsGet = context.getSharedPreferences("shared", MODE_PRIVATE);
        key = prefsGet.getString("key","");

        return key;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imagen_comida;
        TextView nombre_comida;
        TextView calorias_comida;
        ToggleButton favourite_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen_comida = itemView.findViewById(R.id.imagen_comida);
            nombre_comida = itemView.findViewById(R.id.nombreComidaTextCard);
            //calorias_comida = itemView.findViewById(R.id.caloriasTextCard);
            favourite_button = itemView.findViewById(R.id.favourite_buttom);
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

            favourite_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        item comida = mData.get(position);
                        if(favourite_button.isChecked()){
                            key = checkearSharedPreference();
                            keyComida = FirebaseDatabase.getInstance().getReference("key").push().getKey();
                            DatabaseReference nombreDeLaReceta = FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(key).child(keyComida).child("nombre");
                            DatabaseReference caloriasDeLaReceta= FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(key).child(keyComida).child("calorias");
                            DatabaseReference urlDeLaImagenDeComida= FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(key).child(keyComida).child("imagenURL");
                            nombreDeLaReceta.setValue(comida.getTitle());
                            caloriasDeLaReceta.setValue(String.valueOf(comida.getCalories()));
                            urlDeLaImagenDeComida.setValue(comida.getBackground());
                        }else{
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref.child("RecetasFavoritas").child(key).orderByChild("nombre").equalTo(mData.get(position).getTitle());

                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }



                    }else{

                    }



                }

                private String checkearSharedPreference() {
                    SharedPreferences prefsGet = context.getSharedPreferences("shared", MODE_PRIVATE);
                    key = prefsGet.getString("key","");
                    return key;
                }
            });





        }
    }
}
