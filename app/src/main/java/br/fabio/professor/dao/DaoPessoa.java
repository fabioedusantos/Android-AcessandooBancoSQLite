package br.fabio.professor.dao;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import br.fabio.professor.br.fabio.professor.modelo.Pessoa;

public class DaoPessoa {
    private Context context;
    private DaoAdapter banco;

    public DaoPessoa(Context context) {
        this.context = context;
        //instanciamos o DaoAdapter (Dao mãe)
        banco = new DaoAdapter(context);
    }

    public long insert(Pessoa pessoa) {
        /*
		 * Este método é um pouco mais trabalhado porém, nos retorna o id do
		 * utlimo registro. Este processo soluciona quando temos que inserir
		 * dados em chaves estrangeiras em outras tabelas...
		 */
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("email", pessoa.getEmail());

        long result = banco.queryInsertLastId("pessoa", values);

        return result;
    }

    //Método de alteração
    public boolean update(Pessoa pessoa) {
        Object[] args = {
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getId()
        };

        boolean result = banco.queryExecute("UPDATE pessoa SET " + "nome = ?, email = ? WHERE id = ?;", args);

        return result;
    }

    //Método de exclusão
    public boolean delete(long id) {
        Object[] args = {id};
        boolean result = banco.queryExecute("DELETE FROM pessoa WHERE id = ?", args);

        return result;
    }

    //Método de consulta geral
    public ArrayList<Pessoa> getTodos() {
        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM pessoa ORDER BY id DESC", null);

        if (ob != null) {
            for (int i = 0; i < ob.size(); i++) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(ob.getLong(i, "id"));
                pessoa.setNome(ob.getString(i, "nome"));
                pessoa.setEmail(ob.getString(i, "email"));

                pessoas.add(pessoa);
            }
        }

        return pessoas;
    }

    //Método de consulta especifica
    public Pessoa getPessoa(long id) {
        String[] args = {String.valueOf(id)};
        ObjetoBanco ob = banco.queryConsulta("SELECT * FROM pessoa WHERE id = ?", args);

        Pessoa pessoa = null;
        if (ob != null) {
            pessoa = new Pessoa();
            pessoa.setId(ob.getLong(0, "id"));
            pessoa.setNome(ob.getString(0, "nome"));
            pessoa.setEmail(ob.getString(0, "email"));
        }

        return pessoa;
    }
}