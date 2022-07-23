/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import enumerations.EstadosBrazil;
import exceptions.DataBaseException;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import modelos.PessoaJuridica;
import modelos.auxiliares.Endereco;

/**
 *
 * @author ALUNO
 */
public class ManipulaBancoPessoaJuridica extends DataBase implements IManipulaBanco<PessoaJuridica> {

    public ManipulaBancoPessoaJuridica(int quantidadeDeDadosNoBanco, String NomeArquivoDisco) {
        super(8, "ClientePessoaJuridica.txt");
    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return this.quantidadeDeDadosNoBanco;
    }

    @Override
    public PessoaJuridica parse(String dados) throws SystemErrorException {
        try {
            String[] dadosPessoa = dados.split(";");
//  * id, nome fantasia, CNPJ, razao social, 
//  * telefones, email, endereco, cadastro está ativo

            if (dadosPessoa.length != this.getQuantidadeDeDadosSalvos()) {
                throw new DataBaseException("Dados incorretos");
            }

            String[] dadosEndereco = dadosPessoa[6].split(",");
            if (dadosEndereco.length != 8) {
                throw new DataBaseException("Dados incorretos, do endereco de pessoa juridica");

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

            PessoaJuridica pj = new PessoaJuridica(dadosPessoa[2],//   * CNPJ
                    dadosPessoa[3],//   * razao social
                    dadosPessoa[1],//   * nome fantasia
                    dadosPessoa[5],//   * email
                    endereco,//   * endereco
                    dadosPessoa[4].substring(1, dadosPessoa[4].length() - 1).split(","));//   * telefones
            if (dadosPessoa[7].equals(String.valueOf(false))) {//   * caso o cadastro esteja inativo
                pj.setCadastroAtivo(false);//   * inativar objeto antes de retornar
            }
            return pj;
        } catch (DataBaseException | InvalidInputException e) {
            corrigeBanco();
        }
        throw new IllegalStateException("Tentou corrigir o banco de dadoos após encontrar inconsistencias, mas não foi possível encontrar nenhuma inconsistencia no banco de dados");
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<PessoaJuridica> listaPessoas = buscarTodos();
        for (PessoaJuridica p : listaPessoas) {
            if (p.getCnpj().equals(dado)) {//    * encontrou
                return getID(p);//  * retornando o id
            }
        }
        return 0; //    * objeto não encontrado
    }

    public int buscarPorRazaoSocial(String nome) throws InvalidInputException, SystemErrorException {
        ArrayList<PessoaJuridica> listaPessoas = buscarTodos();
        for (PessoaJuridica p : listaPessoas) {
            if (p.getRazaoSocial().equals(nome)) {//    * encontrou
                return getID(p);//  * retornando o id
            }
        }
        return 0; //    * objeto não encontrado
    }
}
