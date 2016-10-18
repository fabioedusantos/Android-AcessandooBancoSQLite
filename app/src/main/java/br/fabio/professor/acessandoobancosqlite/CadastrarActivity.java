package br.fabio.professor.acessandoobancosqlite;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.fabio.professor.dao.DaoPessoa;
import br.fabio.professor.modelo.Pessoa;

public class CadastrarActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtEmail;
    private Button btnSalvar;
    private Pessoa pes = null;
    private DaoPessoa dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        setTitle("Cadastrar Pessoa");

        txtNome = (EditText) findViewById(R.id.txt_nome);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        btnSalvar = (Button) findViewById(R.id.btn_salvar);
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

            setTitle("Alterar " + pes.getNome());
            btnSalvar.setText("Alterar");
        }
    }

    public void salvar(View v){
        if(validaCampos()){
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
                finish();
            } else{
                //alterar
                pes.setNome(txtNome.getText().toString());
                pes.setEmail(txtEmail.getText().toString());

                boolean ret = dao.update(pes);
                if (ret) {
                    Toast.makeText(this, "Alterado com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erro ao alterar!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean validaCampos(){
        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();

        if(nome.length() < 3 || email.length() < 7){
            if(nome.length() < 3){
                txtNome.setError("Preencha o nome corretamente!");
            }
            if(email.length() < 7){
                txtEmail.setError("Preencha o email corretamente!");
            }
            return false;
        }

        return true;
    }
}
