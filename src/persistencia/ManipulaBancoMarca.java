/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import exceptions.DataBaseException;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.util.ArrayList;
import modelos.auxiliares.MarcaVeiculo;

/**
 *
 * @author tanak
 */
public class ManipulaBancoMarca implements IManipulaBanco<MarcaVeiculo> {

    MarcaVeiculo marcaVazia = new MarcaVeiculo();
    int quantidadeDeDadosAhSeremSalvos = 3;

    @Override
    public MarcaVeiculo parse(String dadosCompletos) throws SystemErrorException {
        try {
            String[] dados = dadosCompletos.split(";");
//  * id, nome da marca, cadastro está ativo

            if (dados.length != quantidadeDeDadosAhSeremSalvos) {
                System.out.println(dados.length);
                System.out.println(dadosCompletos);
                throw new DataBaseException("Dados incorretos");
            }
            MarcaVeiculo marca = new MarcaVeiculo(dados[1]);
            if (dados[2].equals("false")) {
                marca.setCadastroAtivo(false);
            }
            return marca;
        } catch (DataBaseException e) {
            corrigeBanco();//   * corrigindo defeito no banco
            return null;
        }
    }

    @Override
    public String getNomeArquivoDisco() {
        return marcaVazia.getNomeArquivoDisco();
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<MarcaVeiculo> listaMarcas = buscarTodos();
        for (MarcaVeiculo marca : listaMarcas) {
            if (marca.getNomeMarca().equals(dado)) {//  * encontrou
                return getID(marca);//  * retornando o id
            }
        }

        return 0;// * objeto não encontrado
    }

    @Override
    public boolean ativarEasterEgg(MarcaVeiculo m) {
        return m.getNomeMarca().toUpperCase().contains("das couve".toUpperCase());
    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return this.quantidadeDeDadosAhSeremSalvos;
    }

}
