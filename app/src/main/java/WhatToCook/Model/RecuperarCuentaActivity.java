package WhatToCook.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getcooked.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecuperarCuentaActivity extends AppCompatActivity {
    private EditText correo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_cuenta);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        correo= findViewById(R.id.correo);


    }

    public void RestaurarConstraseña(View view) {
        mAuth =  FirebaseAuth.getInstance();
        String emailAddress = correo.getText().toString();

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast toast = Toast.makeText(RecuperarCuentaActivity.this, "Revise su casilla de correo para restablecer su contraseña", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(RecuperarCuentaActivity.this, "El email no existe", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                });


        /*correo= findViewById(R.id.correo);
        String[] TO = {correo.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recuperacion de constraseña de What to cook");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "WhatToCook.asd");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        finish();
        */

    }
}