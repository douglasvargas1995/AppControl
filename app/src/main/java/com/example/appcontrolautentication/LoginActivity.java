package com.example.appcontrolautentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcontrolautentication.config.ConfiguracaoFirebase;
import com.example.appcontrolautentication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView etEmail;
    private TextView etSenha;
    private Button btLogar2;
    private FirebaseAuth mAuth;
    private Usuario u;
    private String dado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etEmail = (TextView) findViewById(R.id.etEmail);
        etSenha = (TextView) findViewById(R.id.etSenha);
        btLogar2 = findViewById(R.id.btLogar2);

        btLogar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receberDados();
                if(etEmail.getText().toString().isEmpty() || etSenha.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Invalid fields!", Toast.LENGTH_SHORT).show();
                }else{
                    logar();
                }

            }
        });
    }

    private void logar() {
        mAuth.signInWithEmailAndPassword(u.getEmail(), u.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
                            firebase = firebase.child("Usuarios").child(user.getUid()).child("operador");
                            firebase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    dado = snapshot.getValue().toString();
                                    if(dado.equals("false")){
                                        startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                                    }else{
                                        //startActivity(new Intent(LoginActivity.this,ProfessorActivity.class));
                                    }
                                    Log.i("LOCAL",dado);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Autenticação falhou.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void receberDados() {

            u = new Usuario();
            u.setEmail(etEmail.getText().toString());
            u.setSenha(etSenha.getText().toString());

    }
}