package br.fabio.professor.acessandoobancosqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.fabio.professor.dao.DaoPessoa;
import br.fabio.professor.modelo.Pessoa;

public class MainActivity extends AppCompatActivity {

    private final int menuCadastrar = 6543;
    private ListView lista;
    private DaoPessoa daoPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);
        daoPessoa = new DaoPessoa(this);

        setTitle("Listar Pessoas");
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> values = new ArrayList<String>();
        final ArrayList<Pessoa> pessoas = daoPessoa.getTodos();
        for(Pessoa p : pessoas){
            values.add(p.getNome());
        }
        Log.e("apk", values.size() + "");
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
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Pessoa p = pessoas.get(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Tem certeza que deseja remover " + p.getNome() + "?").setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (daoPessoa.delete(p.getId())) {
                                    Toast.makeText(MainActivity.this, p.getNome() + " removido com Sucesso",
                                            Toast.LENGTH_SHORT).show();
                                    onResume();
                                } else {
                                    Toast.makeText(MainActivity.this, "Erro ao remover " + p.getNome(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("Não", null).show();

                return false;
            }
        });

        lista.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, menuCadastrar, 0, "Cadastrar");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case menuCadastrar:
                Intent i = new Intent(MainActivity.this, CadastrarActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
