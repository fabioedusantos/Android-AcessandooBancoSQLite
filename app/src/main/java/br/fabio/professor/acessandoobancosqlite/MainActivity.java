package br.fabio.professor.acessandoobancosqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.fabio.professor.dao.DaoPessoa;
import br.fabio.professor.modelo.Pessoa;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private DaoPessoa daoPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);
        daoPessoa = new DaoPessoa(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> values = new ArrayList<String>();
        final ArrayList<Pessoa> pessoas = daoPessoa.getTodos();
        for(Pessoa p : pessoas){
            values.add(p.getNome());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa p = pessoas.get(position);
                Intent i = new Intent(MainActivity.this, CadastrarActivity.class);
                i.putExtra("id", p.getId());
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

    }
}
