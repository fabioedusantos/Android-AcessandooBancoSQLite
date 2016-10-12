package br.fabio.professor.acessandoobancosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.fabio.professor.dao.DaoPessoa;
import br.fabio.professor.modelo.Pessoa;

public class CadastrarActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtEmail;
    private Pessoa pes = null;
    private DaoPessoa dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        txtNome = (EditText) findViewById(R.id.txt_nome);
        txtEmail = (EditText) findViewById(R.id.txt_email);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dao = new DaoPessoa(this);
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            long id = extras.getLong("id");
            pes = dao.getPessoa(id);
            txtNome.setText(pes.getNome());
            txtEmail.setText(pes.getEmail());
        }
    }

    public void salvar(View v){
        if(pes == null) {
            //inserir
            pes = new Pessoa();
            pes.setNome(txtNome.getText().toString());
            pes.setEmail(txtEmail.getText().toString());

            long ret = dao.insert(pes);
            if (ret > 0) {
                Toast.makeText(this, "Salvo com sucesso!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao salvar!",
                        Toast.LENGTH_SHORT).show();
            }
        } else{
            //alterar
        }
    }
}
