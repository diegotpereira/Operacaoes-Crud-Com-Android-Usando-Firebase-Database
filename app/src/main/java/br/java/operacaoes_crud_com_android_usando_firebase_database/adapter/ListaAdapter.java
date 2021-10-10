package br.java.operacaoes_crud_com_android_usando_firebase_database.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.java.operacaoes_crud_com_android_usando_firebase_database.InserirAcitivity;
import br.java.operacaoes_crud_com_android_usando_firebase_database.MainActivity;
import br.java.operacaoes_crud_com_android_usando_firebase_database.R;
import br.java.operacaoes_crud_com_android_usando_firebase_database.modelo.DBHelper;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListHolder> {

    private ArrayList<DBHelper> arrayList;
    private Context context;
    private DBHelper dbHelper;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ListaAdapter(ArrayList<DBHelper> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lista_itens,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        Intent i = new Intent(context, InserirAcitivity.class);
        String s1 = arrayList.get(position).getUsuarioNome();
        holder.textoLista.setText(s1);

        holder.editarLista_itens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = arrayList.get(position);

                i.putExtra("Button", "Editar");
                i.putExtra("usuarioNome", dbHelper.getUsuarioNome());
                i.putExtra("email", dbHelper.getEmail());
                i.putExtra("numero", dbHelper.getNumero());

                context.startActivity(i);
                MainActivity.fa.finish();
            }
        });

        holder.lerLista_itens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = arrayList.get(position);
                i.putExtra("Button","Read");
                i.putExtra("usuarioNome",dbHelper.getNumero());
                i.putExtra("email",dbHelper.getEmail());
                i.putExtra("numero",dbHelper.getNumero());
                context.startActivity(i);
                MainActivity.fa.finish();
            }
        });
        holder.deletarLista_itens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = arrayList.get(position);

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Usuarios");

                String temp= dbHelper.getUsuarioNome();

                databaseReference.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Dados Removidos!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Falha ao remover dados", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                MainActivity.limparLista();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        private TextView textoLista;
        private Button editarLista_itens;
        private Button deletarLista_itens;
        private Button lerLista_itens;


        public ListHolder(@NonNull View itemView) {
            super(itemView);

            textoLista = (TextView) itemView.findViewById(R.id.textItem);
            editarLista_itens = (Button) itemView.findViewById(R.id.editarLista_itens);
            deletarLista_itens = (Button) itemView.findViewById(R.id.deletarLista_itens);
            lerLista_itens = (Button) itemView.findViewById(R.id.lerLista_itens);
        }
    }
}
