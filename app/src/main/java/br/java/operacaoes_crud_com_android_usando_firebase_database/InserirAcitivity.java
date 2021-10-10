package br.java.operacaoes_crud_com_android_usando_firebase_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import br.java.operacaoes_crud_com_android_usando_firebase_database.modelo.DBHelper;

public class InserirAcitivity extends AppCompatActivity {

    private Intent i;
    private String s;
    private EditText txtEmail;
    private EditText txtNumero;
    private EditText txtUsuarioNome;
    private FirebaseDatabase firebaseDatabase;
    private String usuario = "Usuarios";
    private String usuarioNome;
    private String email;
    private String numero;
    private Button button;

    private DatabaseReference databaseReference;
    private String Usuario = "Usuarios";



    @Override
    public void onBackPressed() {
        Intent Intent = new Intent(InserirAcitivity.this, MainActivity.class);
        startActivity(Intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_acitivity);

        button = findViewById(R.id.btnEnviar);
        txtEmail = (EditText) findViewById(R.id.editTextEmail);;
        txtNumero = (EditText) findViewById(R.id.editTextNumero);
        txtUsuarioNome =(EditText) findViewById(R.id.editTextUsuarioNome);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(usuario);
        
        button.setText("Button");


        i = getIntent();
        s = i.getStringExtra("Button");
        
        if (s.equals("Criar")) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usuarioNome = txtUsuarioNome.getText().toString();
                    email = txtEmail.getText().toString();
                    numero = txtNumero.getText().toString();

                    databaseReference.child(usuarioNome).setValue(new DBHelper(usuarioNome,email,numero));
                    Toast.makeText(InserirAcitivity.this, "Dados inseridos!", Toast.LENGTH_SHORT).show();
                    i = new Intent(InserirAcitivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        } else if (s.equals("Editar")) {
            String temp;
            temp = usuarioNome;

            HashMap hashMap = new HashMap();

            usuario = i.getStringExtra("usuarioNome");
            email = i.getStringExtra("email");
            numero = i.getStringExtra("numero");

            txtUsuarioNome.setText(usuarioNome);;
            txtEmail.setText(email);;
            txtNumero.setText(numero);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usuarioNome = txtUsuarioNome.getText().toString();
                    email = txtEmail.getText().toString();
                    numero = txtNumero.getText().toString();

                    hashMap.put("usuarioNome", usuarioNome);
                    hashMap.put("email", email);
                    hashMap.put("numero", numero);

                    databaseReference.child(temp).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            i = new Intent(InserirAcitivity.this, MainActivity.class);

                            if (task.isSuccessful()) {
                                Toast.makeText(InserirAcitivity.this, "Dados Atualizados!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InserirAcitivity.this, "Falha ao atualizar dados!", Toast.LENGTH_SHORT).show();

                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }
            });
        } else if (s.equals("Ler")) {
            usuarioNome = i.getStringExtra("usuarioNome");
            email = i.getStringExtra("email");
            numero = i.getStringExtra("numero");

            button.setText("OK");

            txtUsuarioNome.setText(usuarioNome);
            txtEmail.setText(email);
            txtNumero.setText(numero);

            txtUsuarioNome.setEnabled(false);
            txtEmail.setEnabled(false);
            txtNumero.setEnabled(false);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i = new Intent(InserirAcitivity.this, MainActivity.class);

                    startActivity(i);
                    finish();
                }
            });
        }
    }
}