package gian.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gian.getcooked.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText correo;
    private EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView recuperar = findViewById(R.id.olvidar);
        recuperar.setPaintFlags(recuperar.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        recuperar.setTypeface(null, Typeface.BOLD);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);


    }

    public void goToSignUp(View view) {
        Intent i = new Intent(LoginActivity.this, RegistrarseActivity.class);
        this.startActivity(i);
    }

    public void IniciarSesion(View view) {
        if(!correo.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            /*final LoadingDialog loadingDialog = new LoadingDialog((LoginActivity.this));
            loadingDialog.startLoadingAlertDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismissDialog();

                }
            },5000);

            */
            mAuth.signInWithEmailAndPassword(correo.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                TextView email = findViewById(R.id.correo);
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                i.putExtra("Email", email.getText().toString());
                                startActivity(i);
                                LoginActivity.this.finish();
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LoginActivity.this, "Inicio de sesion fallido, correo o contraseña invalidos", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }else{

            Toast.makeText(LoginActivity.this, "Debe ingrsar un correo y una contraseña para loguearse", Toast.LENGTH_SHORT).show();
        }

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void goBack(View view) {
        /*Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
        */


    }

    public void Recuperar(View view) {
        Intent i = new Intent (this, RecuperarCuentaActivity.class);
        startActivity(i);
        this.finish();
    }
}