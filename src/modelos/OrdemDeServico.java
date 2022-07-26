/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import exceptions.InvalidInputException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ALUNO
 */
public class OrdemDeServico extends ExclusaoLogica {

    public enum SituacaoOrdemServico {
        EM_ABERTO, EM_EXECUCAO, CONCLUIDA, CANCELADA;
    }

    private final static String NOME_ARQUIVO_CODIGO_OS = "CodigoOrdensDeServicos.txt";

    private int codigo;
    private String defeitoRelatado;
    private int idServico;
    private double valorMaoDeObra;
    private Date dataEntrada;
    private Date dataSaida = null;//só colocar depois de o serviço ser concluido
    private SituacaoOrdemServico situacao;
    private int idFuncionarioResponsavel;
    private int idPeca; //opcional
    private int quantidadePeca; //opciona
    private double valorUnitarioPeca;//apenas se usar peca
    private int idVeiculo;
    private double porcentagemDesconto;

    public OrdemDeServico() {
    }

    public OrdemDeServico(int codigo, String defeitoRelatado, int idServico, double valorMaoDeObra,
            int idFuncionarioResponsavel, int idVeiculo, double porcentagemDesconto) throws Exception {
        if (porcentagemDesconto < 0 || porcentagemDesconto > 100) {
            throw new Exception("Tentou usar uma porcentagem de desconto de: " + porcentagemDesconto + "%, informe um valor válido");
        }
        this.codigo = codigo;
        this.defeitoRelatado = defeitoRelatado;
        this.idServico = idServico;
        this.valorMaoDeObra = valorMaoDeObra;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
        this.idVeiculo = idVeiculo;
        situacao = SituacaoOrdemServico.EM_ABERTO;
        this.cadastroAtivo = true;
        this.porcentagemDesconto = porcentagemDesconto;
        this.dataEntrada = new Date();
    }

    public OrdemDeServico(int codigo, String defeitoRelatado, int idServico, double valorMaoDeObra,
            int idFuncionarioResponsavel, int idPeca, int quantidadePeca, double valorUnitarioDaPeca, int idVeiculo, double porcentagemDesconto) throws InvalidInputException {
        if (porcentagemDesconto < 0 || porcentagemDesconto > 100) {
            throw new InvalidInputException("Tentou usar uma porcentagem de desconto de: " + porcentagemDesconto + "%, informe um valor válido");
        }
        this.codigo = codigo;
        this.defeitoRelatado = defeitoRelatado;
        this.idServico = idServico;
        this.valorMaoDeObra = valorMaoDeObra;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
        this.idPeca = idPeca;
        this.quantidadePeca = quantidadePeca;
        this.idVeiculo = idVeiculo;
        this.valorUnitarioPeca = valorUnitarioDaPeca;
        situacao = SituacaoOrdemServico.EM_ABERTO;
        this.cadastroAtivo = true;
        this.porcentagemDesconto = porcentagemDesconto;
        this.dataEntrada = new Date();
    }

    public static String getNOME_ARQUIVO_CODIGO_OS() {
        return NOME_ARQUIVO_CODIGO_OS;
    }

    public double getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(double porcentagemDesconto) throws Exception {
        if (porcentagemDesconto < 0 || porcentagemDesconto > 100) {
            throw new Exception("Tentou usar uma porcentagem de desconto de: " + porcentagemDesconto + "%, informe um valor válido");
        }
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDefeitoRelatado() {
        return defeitoRelatado;
    }

    public void setDefeitoRelatado(String defeitoRelatado) {
        this.defeitoRelatado = defeitoRelatado;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setServico(int idServico) {
        this.idServico = idServico;
    }

    public int getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(int idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public double getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(double valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public int getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public SituacaoOrdemServico getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoOrdemServico situacao) throws InvalidInputException {
        if (situacao == null) {
            throw new IllegalStateException("Tentando trocar a situação da ordem de serviço: \"" + this + "\" para uma situação null");
        }
        OUTER:
        switch (this.situacao) {//  * situação em que a OS está
            case EM_ABERTO:
                // * orçamento 
                switch (situacao) {//   * para qual estado ela será mudada
                    case EM_ABERTO:
//   * pass
                        break;
                    case EM_EXECUCAO:
                        //    * tranformar em OS
                        this.situacao = SituacaoOrdemServico.EM_EXECUCAO;
                        break;
                    case CONCLUIDA:
                        //  * concluir
                        throw new InvalidInputException("O orçamento não foi aprovado, por isso não é possivel já ter sido concluido!");
                    case CANCELADA:
                        //  * cancelar
                        this.situacao = SituacaoOrdemServico.CANCELADA;
                        break OUTER;
                    default:
                        break OUTER;
                }
                break;
            case EM_EXECUCAO:
                // * orçamento aprovado
                switch (situacao) {//   * para qual estado ela será mudada
                    case EM_ABERTO:
                        //   * transformar em orçamento
                        throw new InvalidInputException("Uma ordem de serviço não pode ser transformada em orçamento novamente!");
                    case EM_EXECUCAO:
//  * pass
                        break;
                    case CONCLUIDA:
                        //  * concluir
                        this.situacao = SituacaoOrdemServico.CONCLUIDA;
                        this.dataSaida = new Date();//  * salvando a data atual como data de fechamento da OS
                        break OUTER;
                    case CANCELADA:
                        //  * cancelar
                        this.situacao = SituacaoOrdemServico.CANCELADA;
                        break OUTER;
                    default:
                        break OUTER;
                }
            case CONCLUIDA:
                // * orçamento aprovado
                throw new InvalidInputException("Esta ordem de serviço já foi concluida, não é possivel alterar uma nota fiscal!");
            case CANCELADA:
                switch (situacao) {
                    case CONCLUIDA:
                        throw new InvalidInputException("Este orçamento não foi aprovado ainda, por isso não é possível que já tenha sido concluido!");
                    default:
                        this.situacao = situacao;
                        break;
                }
            default:
                throw new AssertionError("falha ao mudar status do Orçamento/Ordem de serviço/nota fiscal, pois está tentando mudar para um estado inválido");
        }
    }

    public int getQuantidadePeca() {
        return quantidadePeca;
    }

    public void setQuantidadePeca(int quantidadePeca) {
        this.quantidadePeca = quantidadePeca;
    }

    public double getValorUnitarioPeca() {
        return valorUnitarioPeca;
    }

    public void setValorUnitarioPeca(double valorUnitarioPeca) {
        this.valorUnitarioPeca = valorUnitarioPeca;
    }

    public double calcularValorTotalSemDesconto() {
        return this.valorMaoDeObra + (this.quantidadePeca * this.valorUnitarioPeca);
    }

    public double calcularValorTotalComDesconto() {
        return calcularValorTotalSemDesconto() - (calcularValorTotalSemDesconto() * (porcentagemDesconto / 100.0));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemDeServico other = (OrdemDeServico) obj;
        return this.codigo == other.codigo;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (dataSaida != null) {
            return codigo + ";" + defeitoRelatado + ";" + idServico + ";" + String.format("%.2f", valorMaoDeObra).replace(",", ".") + ";" + sdf.format(dataEntrada) + ";"
                    + sdf.format(dataSaida) + ";" + situacao + ";" + idFuncionarioResponsavel + ";" + idPeca + ";"
                    + quantidadePeca + ";" + String.format("%.2f", valorUnitarioPeca).replace(",", ".") + ";" + idVeiculo + ";" + cadastroAtivo + ";" + porcentagemDesconto;
        } else {
            return codigo + ";" + defeitoRelatado + ";" + idServico + ";" + String.format("%.2f", valorMaoDeObra).replace(",", ".") + ";" + sdf.format(dataEntrada) + ";"
                    + null + ";" + situacao + ";" + idFuncionarioResponsavel + ";" + idPeca + ";"
                    + quantidadePeca + ";" + String.format("%.2f", valorUnitarioPeca).replace(",", ".") + ";" + idVeiculo + ";" + cadastroAtivo + ";" + porcentagemDesconto;
        }
    }
}
