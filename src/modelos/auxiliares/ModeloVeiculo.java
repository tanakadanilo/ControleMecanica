package modelos.auxiliares;

import java.util.Objects;
import modelos.ExclusaoLogica;

public class ModeloVeiculo extends ExclusaoLogica {

    private String NomeModelo;
    private int idMarca;

    public ModeloVeiculo() {
    }

    public ModeloVeiculo(String NomeModelo, int idMarca) {
        this.NomeModelo = NomeModelo;
        this.idMarca = idMarca;
    }

    public String getNomeModelo() {
        return NomeModelo;
    }

    public void setNomeModelo(String NomeModelo) {
        this.NomeModelo = NomeModelo;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.NomeModelo);
        hash = 59 * hash + this.idMarca;
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
        final ModeloVeiculo other = (ModeloVeiculo) obj;
        if (this.idMarca != other.idMarca) {
            return false;
        }
        return Objects.equals(this.NomeModelo, other.NomeModelo);
    }

    @Override
    public String toString() {
        return NomeModelo + ";" + idMarca + ";" + cadastroAtivo;
    }

}
