package WhatToCook.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.getcooked.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CrearUsuarioActivity extends AppCompatActivity {
    private DatabaseReference Usernames;
    private FirebaseAuth mAuth;
    private TextInputLayout userId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        Usernames = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = findViewById(R.id.usuario);
    }



    private void addUsername(){
        Query checkUser = FirebaseDatabase.getInstance().getReference("username").orderByChild("nombre").equalTo(userId.getEditText().getText().toString());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(CrearUsuarioActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                }else{
                    cargarDatosFirebase(userId.getEditText().getText().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*Usernames.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existo = false;
                String  nombreInput = userId.getEditText().getText().toString();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    NombresUsers nombres= snapshot.getValue(NombresUsers.class);
                    String nombre = nombres.getNombre();

                    existo = nombre.equals(nombreInput);
                    Log.e("Nombre usuario : ",""+nombre);
                    Log.e("Datos : ",""+snapshot.getValue() );

                }
                comprobarExistencia(existo);
                if(!existo){
                    cargarDatosFirebase(nombreInput);
                }else{
                    Toast.makeText(CrearUsuarioActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        */

    }

    private void cargarDatosFirebase(String nombre) {
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("clave");
        HashMap<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("nombre",nombre);
        datosUsuario.put("email",email);
        Usernames.child("username").push().setValue(datosUsuario);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                } else {
                    Toast toast = Toast.makeText(CrearUsuarioActivity.this, "Registro fallido,intentelo mas tarde", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        Toast toast = Toast.makeText(CrearUsuarioActivity.this, "Registro exitoso", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    public void guardarNombre(View view) {
        if(!userId.getEditText().getText().toString().isEmpty()) {
            addUsername();
        }

    }
}