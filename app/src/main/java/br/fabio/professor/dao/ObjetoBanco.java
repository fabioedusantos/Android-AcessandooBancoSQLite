package br.fabio.professor.dao;

import android.database.Cursor;

import java.util.ArrayList;

public class ObjetoBanco {
    private ArrayList<String> colunasName = new ArrayList<String>(); //Nome das colunas da tabela
    private ArrayList<ArrayList<String>> dados = new ArrayList<ArrayList<String>>(); //Linhas (tuplas) da tabela

    public void setDados(Cursor c) {
        /*
		 * Setamos os nomes das colunas e os dados (linhas ou tuplas)
		 */
        if (c.moveToFirst()) {
            // adicionamos os nomes das colunas
            for (int i = 0; i < c.getColumnCount(); i++) {
                colunasName.add(c.getColumnName(i));
            }

            // adicionamos as linhas (tuplas) da tabela
            do {
                ArrayList<String> linha = new ArrayList<String>();
                for (int i = 0; i < c.getColumnCount(); i++) {
                    linha.add(c.getString(i));
                }
                dados.add(linha);
            } while (c.moveToNext());
        }

        c.close(); //fechamos o cursor
    }

    private String getDados(int linha, String alias) {
		/*
		 * Retorna um dado da tabela à partir da sua
		 * linha e coluna (alias).
		 */
        String ret = null;
        int coluna = -1;

		/*
		 *  achamos a coluna em formato numérico (começa do 0,
		 *  pois é um vetor...
		 */
        for (int i = 0; i < colunasName.size(); i++) {
            if (alias.equals(colunasName.get(i))) {
                coluna = i;
                break;
            }
        }

        if (coluna == -1) {
            return null;
        }

        ret = dados.get(linha).get(coluna);

        return ret;
    }

    /*
     * Inicio dos métodos que retornam os valores
     * convertidos (casting, parse) para uma tipagem
     * (int, float...).
     */
    public String getString(int linha, String alias) {
        return getDados(linha, alias);
    }

    public char getChar(int linha, String alias) {
        return getDados(linha, alias).charAt(0);
    }

    public int getInt(int linha, String alias) {
        return Integer.parseInt(getDados(linha, alias));
    }

    public long getLong(int linha, String alias) {
        return Long.parseLong(getDados(linha, alias));
    }

    public double getDouble(int linha, String alias) {
        return Double.parseDouble(getDados(linha, alias));
    }

    public short getShort(int linha, String alias) {
        return (short) getInt(linha, alias);
    }

    //retorna a quantidade de registros (linhas ou tuplas)
    public int size() {
        return dados.size();
    }

}