package WhatToCook.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getcooked.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Toast toast;
    private TextInputLayout emailInput;
    private TextInputLayout passWordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailInput= findViewById(R.id.emailInput);
        passWordInput = findViewById(R.id.passwordInput);
        final LoadingDialog loadingDialog = new LoadingDialog((RegistrarseActivity.this));
    }



    private void setearToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }

    public boolean validateEmail(){
        if (emailInput.getEditText().getText().toString().trim().matches(EMAIL_PATTERN) & !emailInput.getEditText().getText().toString().trim().isEmpty()) {
            return true;
        }else{
            if(!emailInput.getEditText().getText().toString().trim().matches(EMAIL_PATTERN) ){
                Toast.makeText(RegistrarseActivity.this, "Email no valido", Toast.LENGTH_SHORT).show();
            }else{
                if(emailInput.getEditText().getText().toString().trim().isEmpty() & !passWordInput.getEditText().getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistrarseActivity.this, "Debe insertar un email", Toast.LENGTH_SHORT).show();
                    return false;

                }
            }

        }
        return false;
    }

    public boolean validatePassword(){
        if(!passWordInput.getEditText().getText().toString().trim().isEmpty() &passWordInput.getEditText().getText().toString().length() >=4){
            return true;
        }else{
            if(passWordInput.getEditText().getText().toString().trim().isEmpty() & !emailInput.getEditText().getText().toString().trim().isEmpty()){
                Toast.makeText(RegistrarseActivity.this, "Debe insertar una constraseña", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                if(passWordInput.getEditText().getText().toString().trim().length() <4){
                    Toast.makeText(RegistrarseActivity.this, "Su contraseña debe tener 4 o mas caracteres", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return false;
    }

    /*public void Registrarse(View view) {

        if (email.getText().toString().matches(EMAIL_PATTERN)) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast toast = Toast.makeText(RegistrarseActivity.this, "Registro exitoso", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Intent intent = new Intent(RegistrarseActivity.this, CrearUsuarioActivity.class);
                        startActivity(intent);
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast toast = Toast.makeText(RegistrarseActivity.this, "Registro fallido, la cuenta ya existe", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        //updateUI(null);
                    }

                    // ...
                }
            });
        } else {
            if (!email.getText().toString().matches(EMAIL_PATTERN) & !email.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(this, "Email incorrecto", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();


            }
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(this, "Debe completar todos los campos para poder registrarse", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        }

    }
    */
    public void Registrarse(View view) {


        if (validateEmail() & validatePassword()) {
            String pasarEmail = emailInput.getEditText().getText().toString().trim();
            String pasarPassword = passWordInput.getEditText().getText().toString().trim();
            Intent i = new Intent(this, CrearUsuarioActivity.class);
            System.out.println("El mail en registro es " + " " + pasarEmail);
            i.putExtra("email", pasarEmail);
            i.putExtra("clave",pasarPassword);
            startActivity(i);




        } else {
            if(emailInput.getEditText().getText().toString().trim().isEmpty() & passWordInput.getEditText().getText().toString().trim().isEmpty()){
                Toast.makeText(RegistrarseActivity.this, "Debe insertar un email y una contraseña", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void goToLogin(View view) {
        Intent i = new Intent(RegistrarseActivity.this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    public void goBack(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }
}