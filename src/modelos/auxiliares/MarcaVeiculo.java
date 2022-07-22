package modelos.auxiliares;

import java.util.Objects;
import modelos.ExclusaoLogica;

public class MarcaVeiculo extends ExclusaoLogica {

    private String nomeMarca;

    public MarcaVeiculo() {
    }

    public MarcaVeiculo(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.nomeMarca);
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
        final MarcaVeiculo other = (MarcaVeiculo) obj;
        return Objects.equals(this.nomeMarca, other.nomeMarca);
    }

    @Override
    public String toString() {
        return nomeMarca + ";" + isCadastroAtivo();
    }

}
