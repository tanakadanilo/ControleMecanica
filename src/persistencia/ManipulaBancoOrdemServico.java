/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import exceptions.DataBaseException;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import modelos.OrdemDeServico;
import modelos.Peca;

/**
 *
 * @author tanak
 */
public class ManipulaBancoOrdemServico extends DataBase implements IManipulaBanco<OrdemDeServico> {

    public ManipulaBancoOrdemServico() {
        super(15, "Funcionarios.txt");
    }

    @Override
    public OrdemDeServico parse(String dadosCompletos) throws SystemErrorException {
        try {
            String[] dados = dadosCompletos.split(";");
//  * codigo, id, defeito relatado, servico feito,
//  *  Valor mao de obra, data de criacao da OS (dd/MM/yyyy), data de finalizacao da OS(dd/MM/yyyy), 
//  *  situacao da OS, id do funcionario responsável, iD da peca usada, quantidade de pecas usadas, 
//  *  valor unitario da peca, id do veiculo, cadastro esta ativo

            if (dados.length != 15) {
                System.out.println(dadosCompletos);
                System.out.println(dados.length);
                throw new DataBaseException("Dados incompletos da ordem de serviço");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date DataAbertura = sdf.parse(dados[5]);
            Date dataFechamento = null;
            if (!dados[6].equals("null")) {//não tentar se o valor não for uma data
                dataFechamento = sdf.parse(dados[6]);
            } else {
//  * pass
            }

            OrdemDeServico os = new OrdemDeServico(Integer.parseInt(dados[1]),//  * codigo da OS
                    dados[2], //  * defeito relatado
                    Integer.parseInt(dados[3]), //  * id do serviço que será executado
                    Double.parseDouble(dados[4]), //  * valor da mao de obra
                    Integer.parseInt(dados[8]), //  * id do fincionario responsável
                    Integer.parseInt(dados[9]), //  * id da peça que será usada(0 caso não tenha nenhuma)
                    Integer.parseInt(dados[10]), //  * quantidade desta peça que serão usadas no veículo
                    Double.parseDouble(dados[11]), //  * valor unitário da peça
                    Integer.parseInt(dados[12]), //  * id do veiculo   
                    Double.parseDouble(dados[14]));//   * porcentagem de desconto

            os.setDataEntrada(DataAbertura);//  * settando data de abertura

            if (dados[7].equals(String.valueOf(OrdemDeServico.SituacaoOrdemServico.EM_EXECUCAO))) {//   * trocando o status da OS
                os.setSituacao(OrdemDeServico.SituacaoOrdemServico.EM_EXECUCAO);
            } else if (dados[7].equals(String.valueOf(OrdemDeServico.SituacaoOrdemServico.CANCELADA))) {
                os.setSituacao(OrdemDeServico.SituacaoOrdemServico.CANCELADA);
            } else if (dados[7].equals(String.valueOf(OrdemDeServico.SituacaoOrdemServico.CONCLUIDA))) {
                os.setSituacao(OrdemDeServico.SituacaoOrdemServico.EM_EXECUCAO);
                os.setSituacao(OrdemDeServico.SituacaoOrdemServico.CONCLUIDA);
            }
            if (dados[13].equals(String.valueOf(false))) {//    * caso o cadastro estivesse inativo
                os.setCadastroAtivo(false);//   * inativando o cadastro que será retornado
            }

            return os;
        } catch (DataBaseException e) {
            corrigeBanco();
        } catch (ParseException | InvalidInputException e) {
            this.limpaLinha(dadosCompletos);
        }
        return null;
    }

    @Override
    public int buscar(String dado) throws InvalidInputException, SystemErrorException {
        ArrayList<OrdemDeServico> listaOSs = buscarTodos();
        for (OrdemDeServico os : listaOSs) {
            if (String.valueOf(os.getCodigo()).equals(dado)) {//   * encontrou
                return getID(os);// * retornando o id
            }
        }
        return 0;// * objeto não encontrado
    }

    public ArrayList<OrdemDeServico> buscarTodos(int idVeiculo) throws Exception {
        ArrayList<OrdemDeServico> listaOSsCompleta = buscarTodos();//   * todas as OSs ativas no sistema
        ArrayList<OrdemDeServico> listaOSsDesteDono = new ArrayList<>();//  *OSs do cliente que está sendo buscado
        for (OrdemDeServico osAtual : listaOSsCompleta) {
            if (osAtual.getIdVeiculo() == idVeiculo) {// * encontrou uma OS deste dono
                listaOSsDesteDono.add(osAtual);//   * addicionando na lista
            }
        }

        if (listaOSsDesteDono.isEmpty()) {//    * não existe nenhuma OS deste veiculo
            return null;
        } else {//  * existe alguma OS deste veiculo
            return listaOSsDesteDono;
        }
    }

    public ArrayList<OrdemDeServico> BuscarTodosOrcamentos() throws Exception {
        ArrayList<OrdemDeServico> listaOSsCompleta = buscarTodos();//   * todas as OSs ativas no sistema
        ArrayList<OrdemDeServico> listaOrcamentos = new ArrayList<>();//    * todos os orcamentos que ainda não foram aprovados
        for (OrdemDeServico os : listaOSsCompleta) {
            if (os.getSituacao() == OrdemDeServico.SituacaoOrdemServico.EM_ABERTO) {//   * é um orcamento
                listaOrcamentos.add(os);//  * adicionar orcamento na lista
            }
        }
        if (listaOrcamentos.isEmpty()) {//  * nenhum orcamento em aberto
            return null;
        } else {
            return listaOrcamentos;//   *  retornando lista com todos os orcamentos
        }
    }

    @Override
    public void incluir(OrdemDeServico obj) throws InvalidInputException, IOException, SystemErrorException {
        Peca p = new ManipulaBancoPecas().buscar(obj.getIdPeca());
        if (p != null) {//  * caso estejaa usando alguma peça
            p.reservarPecas(obj.getQuantidadePeca());// * reservando peça do estoque
        }
        IManipulaBanco.super.incluir(obj); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void remover(int id) throws InvalidInputException, IOException, SystemErrorException {
        StringBuilder bancoCompleto = new StringBuilder();//   * vai armazenar todos os dados do banco, para serem reescritos
        try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
            String linha = br.readLine();
            while (linha != null) {
                OrdemDeServico os = parse(linha);// * parsing linha
                if (getID(os) == id) {//   * encontrou
                    Peca p = new ManipulaBancoPecas().buscar(os.getIdPeca());
                    if (p != null) {//  * caso a OS use alguma peça
                        p.cancelarReservarPecas(id);//  * cancelando a reserva da peça, pois a OS foi cancelada
                    }
                    os.setCadastroAtivo(false);// * desativando o cadastro antes de reescrever
                }
                bancoCompleto.append(getID(os)).append(";");// * salvando id do objeto
                bancoCompleto.append(os).append("\n");//   * salvando dados para serem reescritos
                linha = br.readLine();
            }
//  * leu todos os dados do banco
            try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco()))) {
                bw.write(bancoCompleto.toString());//   * reescreveu todo o banco
            }
        }
    }

    @Override
    public void editar(int idObjParaRemover, OrdemDeServico objParaAdicionar) throws InvalidInputException, IOException, SystemErrorException {

        OrdemDeServico osAntiga = new ManipulaBancoOrdemServico().buscar(idObjParaRemover);
        try {
            StringBuilder bancoCompleto = new StringBuilder();
            //   * vai armazenar todos os dados do banco, para serem reescritos
            try ( BufferedReader br = new BufferedReader(new FileReader(this.getNomeArquivoDisco()))) {
                String linha = br.readLine();
                while (linha != null) {
                    OrdemDeServico os = parse(linha);// * parsing linha
                    if (getID(os) == idObjParaRemover) {//   * encontrou
                        os.setCadastroAtivo(false);// * desativando o cadastro antes de reescrever
                    }
                    bancoCompleto.append(getID(os)).append(";");// * salvando id do objeto
                    bancoCompleto.append(os).append("\n");//   * salvando dados para serem reescritos
                    linha = br.readLine();
                }
//  * leu todos os dados do banco
                try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco()))) {
                    bw.write(bancoCompleto.toString());//   * reescreveu todo o banco
                }
            }
            try ( BufferedWriter bw = new BufferedWriter(new FileWriter(this.getNomeArquivoDisco(), true))) {
                Peca p = new ManipulaBancoPecas().buscar(objParaAdicionar.getIdPeca());
                if (p != null) {//  * caso tenha alguma peça
                    if (objParaAdicionar.getSituacao().equals(OrdemDeServico.SituacaoOrdemServico.EM_EXECUCAO)) {//    * caso esteja criando uma noova OS em execução
                        p.retirarDoEstoque(objParaAdicionar.getQuantidadePeca());//  * retirnando peças do estoque
                    } else if (objParaAdicionar.getSituacao().equals(OrdemDeServico.SituacaoOrdemServico.CANCELADA)) {//    * caso esteja cancelando uma ordem de serviço existente
                        p.cancelarReservarPecas(objParaAdicionar.getQuantidadePeca());//    * tirando peça  reservada, pois a OS foi cancelada
                    }
                }
                bw.write(idObjParaRemover + ";" + objParaAdicionar.toString() + "\n");//    * salvando novo valor no banco e mantendo o id
            }
        } catch (InvalidParameterException e) {//   * pegando apenas o erro de peca, mechendo no estoque
            new ManipulaBancoOrdemServico().incluir(osAntiga);//    * reescrevendo no banco, caso tenha dado algum erro no método
        }

    }

    @Override
    public int getQuantidadeDeDadosSalvos() {
        return this.quantidadeDeDadosNoBanco;
    }

}
