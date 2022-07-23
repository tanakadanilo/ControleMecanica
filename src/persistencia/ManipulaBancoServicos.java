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
import java.util.ArrayList;
import modelos.Servico;

/**
 *
 * @author tanak
 */
public class ManipulaBancoServicos extends DataBase implements IManipulaBanco<Servico> {

    public ManipulaBancoServicos() {
        super(4, "Servicos.txt");
    }

    @Override
    public Servico parse(String dados) throws SystemErrorException {
        try {
            String[] dadosServico = dados.split(";");
// * id, nomeServico, valorMaoDeObra, cadastro está ativo
            if (dadosServico.length != this.getQuantidadeDeDadosSalvos()) {
                throw new DataBaseException("os dados:\"" + dados + "\" estão incorretos");
            }
            Servico s = new Servico(dadosServico[1], Double.parseDouble(dadosServico[2]));
            if (dadosServico[3].equals(String.valueOf(false))) {
                s.setCadastroAtivo(false);
            }
            return s;
        } catch (DataBaseException e) {
            corrigeBanco();
            throw new IllegalStateException("Os dados \"" + dados + "\" foram corrompidos");//  * o método acima já soltou exceção quando encontrou a falha no banco de dados
        }
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<Servico> listaServicos = buscarTodos();
        for (Servico s : listaServicos) {
            if (s.getNomeServico().equals(dado)) {//  * encontrou
                return getID(s);//  * retornando o id
            }
        }
        return 0;// * objeto não encontrado
    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return this.quantidadeDeDadosNoBanco;
    }

}
