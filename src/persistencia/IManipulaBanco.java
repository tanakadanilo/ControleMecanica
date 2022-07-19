/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import exceptions.InvalidInputException;
import geradorId.GeradorId;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelos.DataBase;
import tela.TelaEasterEgg;

/**
 *
 * @author tanak
 * @param <T>
 */
public interface IManipulaBanco<T extends DataBase> {

    public boolean ativarEasterEgg(T obj);

    String getNomeArquivoDisco();

    int getQuantidadeDeDadosSalvos();

    public int getID(T obj) throws InvalidInputException;

    public T parse(String dados);

    public default void incluir(T obj) throws InvalidInputException, IOException {
        if (ativarEasterEgg(obj)) {
            new TelaEasterEgg().setVisible(true);
        }
        ArrayList<T> listaCompleta = buscarTodos();//   * pegando todos os dados ativos do banco
        if (listaCompleta != null && !listaCompleta.isEmpty()) {//  * tem algo na lista
            for (T objAtual : listaCompleta) {//    * percorrendo toda a lista
                if (((T) obj).equals(((T) objAtual))) {//   * já existe um dado igual no banco
                    throw new InvalidInputException("Já existe um cadastro ativo disso!");
                }
            }
        }
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco(), true))) {
            int id = GeradorId.getID(obj.getArquivoID());
            bw.write(id + ";" + obj.toString() + "\n");
        }
    }

    public default int buscar(T obj) throws InvalidInputException, IOException {
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                T objAtual = parse(linha);// * parsing linha
                if (obj.equals(objAtual) && obj.isCadastroAtivo()) {// * encontrou
                    return getID(obj);
                }
                linha = br.readLine();
            }
            return 0;//  * objeto não encontrado

        }

    }

    public int buscar(String dado) throws InvalidInputException;

    public default T buscar(int id) throws InvalidInputException, IOException {
        if (id == 0) {
            return null;
        }
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                T obj = parse(linha);// * parsing linha
                if (getID(obj) == id && obj.isCadastroAtivo()) {// * encontrou
                    return obj;
                }
                linha = br.readLine();
            }
        }

        return null;//  * objeto não encontrado
    }

    public default ArrayList<T> buscarTodos() throws InvalidInputException, IOException {
        ArrayList<T> listaCompleta = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                T obj = parse(linha);// * parsing linha
                if (obj.isCadastroAtivo()) {// * adicionar apenas cadastros ativos
                    listaCompleta.add(obj);
                }
                linha = br.readLine();
            }
        }

        if (listaCompleta.isEmpty()) {//    * caso não tenha nenhum cadastro ativo
            return null;
        } else {
            return listaCompleta;
        }
    }

    public default ArrayList<T> buscarTodosRemovidos() throws InvalidInputException, IOException {
        ArrayList<T> listaCompleta = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                T obj = parse(linha);// * parsing linha
                if (!obj.isCadastroAtivo()) {// * adicionar apenas cadastros ativos
                    listaCompleta.add(obj);
                }
                linha = br.readLine();
            }
        }

        if (listaCompleta.isEmpty()) {//    * caso não tenha nenhum cadastro ativo
            return null;
        } else {
            return listaCompleta;
        }
    }

    public default void remover(T obj) throws InvalidInputException, IOException {
        int id = buscar(obj);// * pegando o id do objeto
        remover(id);//  * usando a exclusão por id
    }

    public default void remover(int id) throws InvalidInputException, IOException {
        StringBuilder bancoCompleto = new StringBuilder();//   * vai armazenar todos os dados do banco, para serem reescritos
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                T obj = parse(linha);// * parsing linha
                if (getID(obj) == id) {//   * encontrou
                    obj.setCadastroAtivo(false);// * desativando o cadastro antes de reescrever
                }
                bancoCompleto.append(getID(obj)).append(";");// * salvando id do objeto
                bancoCompleto.append(obj).append("\n");//   * salvando dados para serem reescritos
                linha = br.readLine();
            }
//  * leu todos os dados do banco
            try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco()))) {
                bw.write(bancoCompleto.toString());//   * reescreveu todo o banco
            }
        }
    }

    public default void editar(T objParaRemover, T objParaAdicionar) throws InvalidInputException, IOException {
        int id = getID(objParaRemover);//   * pegando o id do objeto que será excluido
        editar(id, objParaAdicionar);// * chamando a edição por id
    }

    public default void editar(int idObjParaRemover, T objParaAdicionar) throws InvalidInputException, IOException {
        remover(idObjParaRemover);//    * removendo objeto antigo
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco(), true))) {
            bw.write(idObjParaRemover + ";" + objParaAdicionar.toString() + "\n");//    * salvando novo valor no banco e mantendo o id
        }
    }

    public default void corrigeBanco() throws IOException {
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            if (this.getQuantidadeDeDadosSalvos() == 0) {
                throw new IllegalStateException("Tentando corrigir o banco de dados de um arquivo que não salva nada no banco de dados");
            }
            String linha = br.readLine();
            while (linha != null) {
                String dados[] = linha.split(";");
                if (dados.length != this.getQuantidadeDeDadosSalvos()) {//  * achou uma linha com defeito
                    limpaLinha(linha);
                }
            }
        }
    }

    private void limpaLinha(String linha) throws IOException {
        StringBuilder bancoCompleto = new StringBuilder();
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {//   * lendo todos os dados para serem reescritos no banco
            String linhaBanco = br.readLine();
            if (!linhaBanco.equals(linha)) {//  * não excluir
                bancoCompleto.append(linhaBanco).append("\n");
            }
        }
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco()))) {//   * apagando banco de dados defeituoso
            bw.write(bancoCompleto.toString());//   * reescrevendo dados que serão salvos
        }
    }

}
