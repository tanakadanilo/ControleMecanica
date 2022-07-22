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
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Funcionario;
import modelos.auxiliares.Endereco;

/**
 *
 * @author tanak
 */
public class ManipulaBancoFuncionario implements IManipulaBanco<Funcionario> {

    Funcionario funcionarioVazio = new Funcionario();

    @Override
    public Funcionario parse(String dadosCompletos) throws SystemErrorException {
        Funcionario f = null;
        try {
            String[] dados = dadosCompletos.split(";");
//    * id, nome, cpf, dataNascimento,
//    * telefones, email, endereco,cadastroAtivo,
//    * especialidade, salarioMes, salarioHora, matriculaFuncionario

            if (dados.length != 12) {
                System.out.println(dadosCompletos);
                System.out.println(Arrays.toString(dados));
                System.out.println(dados.length);
                throw new DataBaseException("Dados do funcionario incorretos");
            }

            String[] dadosEndereco = dados[6].split(",");
//  * tipo de logradouro, logradouro, numero da casa, complemento, bairo, cidade, estado, cep

            if (dadosEndereco.length != 8) {
                throw new DataBaseException("Dados de endereço do funcionario incorretos");
            }
            String[] telefones = dados[4].substring(dados[4].indexOf("[") + 1, dados[4].lastIndexOf("]")).split(",");// * ignorando "[]"
            if (telefones.length != 3) {
                throw new DataBaseException("Telefones incorretos");
            }
            Endereco endereco = new Endereco(dadosEndereco[0],//    * tipo de logradouro
                    dadosEndereco[1],//    * logradouro
                    dadosEndereco[2],//    * numero
                    dadosEndereco[3],//    * complemento
                    dadosEndereco[4],//    * bairro
                    dadosEndereco[5],//    * cidade
                    Enum.valueOf(EstadosBrazil.class, dadosEndereco[6]),//    * estado, seguindo o Enum
                    dadosEndereco[7]);//    * cep

            f = new Funcionario(dados[8],//    * especialidade
                    Double.parseDouble(dados[9]),//    * salario mensal
                    Double.parseDouble(dados[10]),//    * salario hora
                    Integer.parseInt(dados[11]),//    * numero da matricula
                    dados[1],//    * nome 
                    dados[2],//    * CPF
                    new SimpleDateFormat("dd/MM/yyyy").parse(dados[3]),//    * data de nascimento
                    dados[5],//    * email
                    endereco,//    * endereço
                    telefones);//    * telefones, ignorando [] do array
            if (dados[7].equals(String.valueOf(false))) {//    * se tiver desativado, desativar
                f.setCadastroAtivo(false);
            }
        } catch (InvalidInputException | DataBaseException | ParseException inputInvalido) {
            corrigeBanco();
        }
        return f;
    }

    @Override
    public int getID(Funcionario obj) throws InvalidInputException, SystemErrorException {
        try ( BufferedReader br = new BufferedReader(new FileReader(obj.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                Funcionario f = parse(linha);// * parsing linha
                if (f.equals(obj)) {// * encontrou
                    return Integer.parseInt(linha.split(";")[0]);// * retornando o id
                }
                linha = br.readLine();
            }
        } catch (IOException ex) {
            try ( FileWriter fr = new FileWriter(obj.getNomeArquivoDisco())) {
//   * criando novo arquivo vazio(resettando o banco de dados
            } catch (IOException ex1) {//   * fudeu rapaz, não faço ideia de como resolver
                Logger.getLogger(ManipulaBancoFuncionario.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return 0;// * falha no banco de dados, arquivo não encontrado
        }

        return 0;// * objeto não encontrados

    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<Funcionario> listaFunc = buscarTodos();

        for (Funcionario f : listaFunc) {
            if (("" + f.getMatricula()).equals(dado)) {
                return getID(f);
            }
        }
        return 0;
    }

    public int buscar(String dado, boolean nome) throws InvalidInputException, SystemErrorException {
        ArrayList<Funcionario> listaFunc = buscarTodos();

        for (Funcionario f : listaFunc) {
            if (("" + f.getNome()).equals(dado)) {
                return getID(f);
            }
        }
        return 0;
    }

    @Override
    public boolean ativarEasterEgg(Funcionario obj) {
        return obj.ativarEasterEgg()
                || obj.getEspecialidade().toUpperCase().contains("das couve".toUpperCase());
    }

    @Override
    public String getNomeArquivoDisco() {
        return funcionarioVazio.getNomeArquivoDisco();
    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return 12;
    }

}
