package br.java.operacaoes_crud_com_android_usando_firebase_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.java.operacaoes_crud_com_android_usando_firebase_database.adapter.ListaAdapter;
import br.java.operacaoes_crud_com_android_usando_firebase_database.modelo.DBHelper;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDabase;
    private DatabaseReference databaseReference;
    private static ArrayList<DBHelper> arrayList = new ArrayList<DBHelper>();
    private RecyclerView lista;
    private Button btnCriar;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList.clear();
        fa = this;
        firebaseDabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDabase.getReference("Usuarios");

        getUsuarios();

        btnCriar = findViewById(R.id.btn_Principal);
        lista = findViewById(R.id.lista);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lista.getContext(), new LinearLayoutManager(this).getOrientation());

        lista.addItemDecoration(dividerItemDecoration);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(new ListaAdapter(arrayList,this ));


        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InserirAcitivity.class);
                String s;
                s = "Criar";
                i.putExtra("Button", s);
                startActivity(i);
                finish();
            }
        });
    }

    public void getUsuarios() {
        databaseReference.addValueEventListener(new ValueEventListener() {

            String usuarioNome;
            String email;
            String numero;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    usuarioNome = dataSnapshot.child("usuarioNome").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    numero = dataSnapshot.child("numero").getValue().toString();
                    arrayList.add(new DBHelper(usuarioNome, email, numero));
                }
                lista.setAdapter(new ListaAdapter(arrayList, MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void limparLista() {
        arrayList.clear();
    }
}