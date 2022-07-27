/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import enumerations.EstadosBrazil;
import exceptions.DataBaseException;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import modelos.PessoaFisica;
import modelos.auxiliares.Endereco;

/**
 *
 * @author tanak
 */
public class ManipulaBancoPessoaFisica extends DataBase implements IManipulaBanco<PessoaFisica> {

    public ManipulaBancoPessoaFisica() {
        super(8, "ClientePessoaFisica.txt");
    }

    @Override
    public String getNomeArquivoIdDisco() {
        return this.arquivoID;
    }

    @Override
    public PessoaFisica parse(String dadosCompletos) throws SystemErrorException {
        try {
            String[] dados = dadosCompletos.split(";");
//  * id, nome, cpf, data de nascimento (dd/MM/yyyy),
//  * array de telefones, email, endereco, cadastro está ativo
            if (dados.length != 8) {
                System.out.println(dadosCompletos);
                throw new DataBaseException("Dados incorretos" + dadosCompletos);
            }
            String[] dadosEndereco = dados[6].split(",");
            if (dadosEndereco.length != 8) {
                throw new DataBaseException("Dados incorretos, de endereço");

            }
            Endereco endereco = new Endereco(dadosEndereco[0],//    * tipo de logradouro
                    dadosEndereco[1],//    * logradouro
                    dadosEndereco[2],//    * numero
                    dadosEndereco[3],//    * complemento
                    dadosEndereco[4],//    * bairro
                    dadosEndereco[5],//    * cidade
                    Enum.valueOf(EstadosBrazil.class,
                            dadosEndereco[6]),//    * estado, seguindo o Enum
                    dadosEndereco[7]);//    * cep

            Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dados[3]);

            String[] telefones = dados[4].substring(dados[4].indexOf("[") + 1, dados[4].lastIndexOf("]")).split(",");

            PessoaFisica p = new PessoaFisica(dados[1],// * nome
                    dados[2],// * CPF
                    dataNascimento,// * data de nascimento
                    dados[5],// * email
                    endereco,// * endereco
                    telefones);

            if (dados[7].equals(String.valueOf(false))) {
                p.setCadastroAtivo(false);
            }
            return p;
        } catch (DataBaseException | InvalidInputException | ParseException e) {
            corrigeBanco();
            throw new SystemErrorException("Os dados \"" + dadosCompletos + "\" foram corrompidos");
        }
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<PessoaFisica> listaPessoas = buscarTodosRemovidos();
        for (PessoaFisica p : listaPessoas) {
            if (p.getCpf().equals(dado) && p.isCadastroAtivo()) {//    * encontrou
                return getID(p);//  * retornando o id
            }
        }
        return 0;// * objeto não encontrado
    }

    public int buscarPorNome(String Nome) throws Exception {
        ArrayList<PessoaFisica> listaPessoas = buscarTodosRemovidos();
        for (PessoaFisica p : listaPessoas) {
            if (p.getNome().equals(Nome)) {//    * encontrou
                return getID(p);//  * retornando o id
            }
        }
        return 0;// * objeto não encontrado
    }

    @Override
    public String getNomeArquivoDisco() {
        return this.NomeArquivoDisco;
    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return this.quantidadeDeDadosNoBanco;
    }

}
