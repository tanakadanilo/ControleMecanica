package persistencia;

import exceptions.DataBaseException;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.util.ArrayList;
import modelos.auxiliares.ModeloVeiculo;

public class ManipulaBancoModelos extends DataBase implements IManipulaBanco<ModeloVeiculo> {

    public ManipulaBancoModelos() {
        super(4, "ModelosVeiculos.txt");
    }

    @Override
    public String getNomeArquivoIdDisco() {
        return this.arquivoID;
    }

    @Override
    public ModeloVeiculo parse(String linha) throws SystemErrorException {
        try {
            String[] dados = linha.split(";");
//  * id, nome do modelo, id da marca, cadastro está ativo

            if (dados.length != this.getQuantidadeDeDadosSalvos()) {
                throw new DataBaseException("Dados incorretos");
            }

            ModeloVeiculo m = new ModeloVeiculo(dados[1], Integer.parseInt(dados[2]));
            if (dados[3].equals(String.valueOf(false))) {//  * o cadastro está ativo
                m.setCadastroAtivo(false);
            }
            return m;
        } catch (DataBaseException e) {
            corrigeBanco();
            return null;
        }
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<ModeloVeiculo> listaModelos = buscarTodosRemovidos();

        for (ModeloVeiculo modelo : listaModelos) {
            if (modelo.getNomeModelo().equals(dado)) {//    * encontrou
                return getID(modelo);// * retornando o id
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

    @Override
    public int buscarNosExcluidos(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<ModeloVeiculo> listaModelos = buscarTodosRemovidos();

        for (ModeloVeiculo modelo : listaModelos) {
            if (modelo.getNomeModelo().equals(dado)) {//    * encontrou
                return getID(modelo);// * retornando o id
            }
        }
        return 0;// * objeto não encontrado
    }

}
