/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;
import persistencia.ManipulaBancoPecas;

/**
 *
 * @author ALUNO
 */
public class Peca extends ExclusaoLogica {

    private String codigoPeca;
    private String descricao;
    private float valorPeca;
    private int quantidadeNoEstoque;//  * quantas peças tem no estoque, incluindo a quantidade reservada
    private int quantidadeReservadas;// * a quantidade de peças reservadas para fazer algum serviço mas que ainda não foram usadas
    private int estoquequantidadeMinima;

    public Peca() {
    }

    public Peca(String codigoPeca, String descricao, float valorPeca) {
        this.codigoPeca = codigoPeca;
        this.descricao = descricao;
        this.valorPeca = valorPeca;

    }

    public Peca(String codigoPeca, String descricao, float valorPeca, int quantidadeNoEstoque, int estoquequantidadeMinima) {
        this.codigoPeca = codigoPeca;
        this.descricao = descricao;
        this.valorPeca = valorPeca;
        this.quantidadeNoEstoque = quantidadeNoEstoque;
        this.estoquequantidadeMinima = estoquequantidadeMinima;
    }

    public Peca(String codigoPeca, String descricao, float valorPeca, int estoquequantidadeMinima) {
        this.codigoPeca = codigoPeca;
        this.descricao = descricao;
        this.valorPeca = valorPeca;
        this.estoquequantidadeMinima = estoquequantidadeMinima;
    }

    public String getCodigoPeca() {
        return codigoPeca;
    }

    public void setCodigoPeca(String codigoPeca) {
        this.codigoPeca = codigoPeca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValorPeca() {
        return valorPeca;
    }

    public void setValorPeca(float valorPeca) {
        this.valorPeca = valorPeca;
    }

    public int getQuantidadeNoEstoque() {
        return quantidadeNoEstoque;
    }

    public void setQuantidadeNoEstoque(int quantidadeNoEstoque) {
        this.quantidadeNoEstoque = quantidadeNoEstoque;
    }

    public int getQuantidadeReservadas() {
        return quantidadeReservadas;
    }

    public void setQuantidadeReservadas(int quantidadeReservadas) {
        this.quantidadeReservadas = quantidadeReservadas;
    }

    public int getEstoquequantidadeMinima() {
        return estoquequantidadeMinima;
    }

    public void setEstoquequantidadeMinima(int estoquequantidadeMinima) {
        this.estoquequantidadeMinima = estoquequantidadeMinima;
    }

    public void retirarDoEstoque(int quantidadeAhSerRetirada) throws InvalidInputException, IOException, SystemErrorException {
        if (quantidadeAhSerRetirada < 0) {//   * valor negativo
            throw new InvalidInputException("A quantidade de peças a ser retirada do estoque não pode ser um valor negativo.");
        }
        if (quantidadeAhSerRetirada == 0) {//  * não fazer nada
            return;//   * finalizando o método
        }
        if (quantidadeAhSerRetirada > quantidadeNoEstoque) {//   * não existem peças o suficiente no estoque
            System.out.println("tentando retirar: " + quantidadeAhSerRetirada);
            throw new InvalidInputException("O estoque possui apenas: " + quantidadeNoEstoque + " peças, informe um valor inferior ou igual a isso!");
        }
//  * tudo certo
        Peca valorAntigo = new Peca(codigoPeca, descricao, valorPeca, quantidadeNoEstoque, estoquequantidadeMinima);
        this.quantidadeReservadas -= quantidadeAhSerRetirada;// * não está mais reservada, pois já foi utilizada
        this.quantidadeNoEstoque -= quantidadeAhSerRetirada;//  * retirando do estoque
        new ManipulaBancoPecas().editar(valorAntigo, this);
    }

    public void cancelarReservarPecas(int quantidadeAhSerRetiradaDaReserva) throws InvalidInputException, IOException, SystemErrorException {
        if (quantidadeAhSerRetiradaDaReserva < 0) {//   * valor negativo
            throw new InvalidInputException("A quantidade de peças a ser cancelada da reserva não pode ser um valor negativo.");
        }
        if (quantidadeAhSerRetiradaDaReserva == 0) {//  * não fazer nada
            return;//   * finalizando o método
        }
        if (quantidadeAhSerRetiradaDaReserva > quantidadeReservadas) {//   * não existem peças o suficiente reservadas
            throw new InvalidParameterException("Tentando retirar: " + quantidadeAhSerRetiradaDaReserva
                    + ", mas foram reservadas apenas: " + quantidadeReservadas
                    + " peças, informe um valor inferior ou igual a isso!");
        }
//  * tudo certo
        Peca valorAntigo = new Peca(codigoPeca, descricao, valorPeca, quantidadeNoEstoque, estoquequantidadeMinima);
        this.quantidadeReservadas -= quantidadeAhSerRetiradaDaReserva;
        new ManipulaBancoPecas().editar(valorAntigo, this);
    }

    public void reservarPecas(int quantidadeAhSerReservada) throws InvalidInputException, IOException, SystemErrorException {
        if (quantidadeAhSerReservada < 0) {//   * valor negativo
            throw new InvalidInputException("A quantidade de peças a ser reservada não pode ser um valor negativo.");
        }
        if (quantidadeAhSerReservada == 0) {//  * não fazer nada
            return;//   * finalizando o método
        }
        if (quantidadeAhSerReservada > (quantidadeNoEstoque - quantidadeReservadas)) {//   * faltam peças no estoque
            throw new InvalidInputException("Estoque insuficiente! O estoque possui apenas " + quantidadeNoEstoque + " peças");
        }
//  * tudo certo
        Peca valorAntigo = new Peca(codigoPeca, descricao, valorPeca, quantidadeNoEstoque, estoquequantidadeMinima);
        this.quantidadeReservadas += quantidadeAhSerReservada;
        new ManipulaBancoPecas().editar(valorAntigo, this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.codigoPeca);
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
        final Peca other = (Peca) obj;
        return Objects.equals(this.codigoPeca, other.codigoPeca);
    }

    @Override
    public String toString() {
        return codigoPeca + ";" + descricao + ";" + String.format("%.2f", valorPeca).replace(",", ".") + ";" + quantidadeNoEstoque + ";"
                + quantidadeReservadas + ";" + estoquequantidadeMinima + ";" + cadastroAtivo;
    }

}
