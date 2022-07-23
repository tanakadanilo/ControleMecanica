/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import exceptions.DataBaseException;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import modelos.Peca;

/**
 *
 * @author ALUNO
 */
public class ManipulaBancoPecas extends DataBase implements IManipulaBanco<Peca> {

    public ManipulaBancoPecas() {
        super(8, "Pecas.txt");
    }

    @Override
    public String getNomeArquivoIdDisco() {
        return this.arquivoID;
    }

    @Override
    public Peca parse(String dados) throws SystemErrorException {
        try {
            String[] dadosPeca = dados.split(";");
//  * id, código da peça, nome da peça, valor unitário,
//  * quantidade no estoque, quantidade de peças reservadas, estoque minimo, cadastro está ativo;

            if (dadosPeca.length != 8) {
                throw new DataBaseException("Dados incorretos, de peças");
            }

            Peca p = new Peca(dadosPeca[1],// * codigo da peça
                    dadosPeca[2],// * nome da peça
                    Float.parseFloat(dadosPeca[3]),// * valor unitario
                    Integer.parseInt(dadosPeca[4]),// * quantidade no estoque
                    Integer.parseInt(dadosPeca[6]));// * estoque minimo

            p.setQuantidadeReservadas(Integer.parseInt(dadosPeca[5]));//    * settando a quanntidade de peças reservadas
            if (dadosPeca[7].equals(String.valueOf(false))) {// * caso o cadastro esteja desativado
                p.setCadastroAtivo(false);//    * desativando cadastro antes de retornar
            }
            return p;
        } catch (DataBaseException e) {
            corrigeBanco();
            throw new SystemErrorException("os dados:\"" + dados + "\" estão corrompidos. ");//  * não conseguiu parsear a linha
        }
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<Peca> listaPecas = buscarTodos();
        for (Peca p : listaPecas) {
            if (p.getCodigoPeca().equals(dado)) {// * encontrou
                return getID(p);//  * retornando o id
            }
        }

        return 0;// * objeto não encontrado
    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return this.quantidadeDeDadosNoBanco;
    }
}
