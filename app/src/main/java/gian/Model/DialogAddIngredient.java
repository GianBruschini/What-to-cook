package gian.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.gian.getcooked.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogAddIngredient extends AppCompatDialogFragment {
   /* private EditText ingrediente;
    private gianDialogListener listener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (gianDialogListener) context;

        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "must implement gianDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view).setTitle("Ingrediente").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ingredienteInput =ingrediente.getText().toString();
                listener.applyTexts(ingredienteInput);

            }
        });

        ingrediente = view.findViewById(R.id.editTextingredient);

        return builder.create();

    }

    public interface gianDialogListener{
        void applyTexts(String ingrediente);
    }
    */



}
