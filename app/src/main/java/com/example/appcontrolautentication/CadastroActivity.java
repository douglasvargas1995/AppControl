package com.example.appcontrolautentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcontrolautentication.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private TextView edNome;
    private TextView edEmail;
    private TextView edSenha;
    private Button btnCadastrar2;
    private FirebaseAuth mAuth;
    private CheckBox cbOperador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edNome = (TextView) findViewById(R.id.edNome);
        edEmail = (TextView) findViewById(R.id.edEmail);
        edSenha = (TextView) findViewById(R.id.edSenha);
        cbOperador = findViewById(R.id.cbOperador);
        btnCadastrar2 = findViewById(R.id.btnCadastrar2);
        mAuth = FirebaseAuth.getInstance();


        btnCadastrar2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(edNome.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edSenha.getText().toString().isEmpty()){
                   Toast.makeText(CadastroActivity.this,"Invalid fields!",Toast.LENGTH_SHORT).show();
               }else{
                   criarLogin();
               }
            }
        });


    }

    private void criarLogin() {
        mAuth.createUserWithEmailAndPassword(edEmail.getText().toString(),edSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Usuario u = new Usuario();
                            u.setId(user.getUid());
                            u.setNome(edNome.getText().toString());
                            u.setEmail(edEmail.getText().toString());
                            u.setSenha(edSenha.getText().toString());
                            if(cbOperador.isChecked()){
                                u.setOperador(true);
                            }else{
                                u.setOperador(false);
                            }
                            u.salvarDados();
                            Toast.makeText(CadastroActivity.this,"user create!",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(CadastroActivity.this,"Erro ao criar um login.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void receberDados() {

    }

}