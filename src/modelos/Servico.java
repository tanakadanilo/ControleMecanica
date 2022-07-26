/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.Objects;

/**
 *
 * @author tanak
 */
public class Servico extends ExclusaoLogica {

    private String nomeServico;
    private double valorMaoDeObra;

    public Servico() {
    }

    public Servico(String nomeServico, double valorMaoDeObra) {
        this.nomeServico = nomeServico;
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public double getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(double valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.nomeServico);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.valorMaoDeObra) ^ (Double.doubleToLongBits(this.valorMaoDeObra) >>> 32));
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
        final Servico other = (Servico) obj;
        if (Double.doubleToLongBits(this.valorMaoDeObra) != Double.doubleToLongBits(other.valorMaoDeObra)) {
            return false;
        }
        return Objects.equals(this.nomeServico, other.nomeServico);
    }

    @Override
    public String toString() {
        return nomeServico + ";" + String.format("%.2f", valorMaoDeObra).replace(",", ".") + ";" + cadastroAtivo;
    }

}
