package modelos.auxiliares;

import java.util.Objects;
import modelos.DataBase;

public class MarcaVeiculo extends DataBase {

    private String nomeMarca;

    public MarcaVeiculo() {
        super(3, "MarcaVeiculo.txt", "IdMarcasVeiculos.txt");
        this.NomeArquivoDisco = "MarcaVeiculo.txt";
        this.arquivoID = "IdMarcasveiculos.txt";
    }

    public MarcaVeiculo(String nomeMarca) {
        super(3, "MarcaVeiculo.txt", "IdMarcasVeiculos.txt");
        this.NomeArquivoDisco = "MarcaVeiculo.txt";//   * salvanco nome do arquivo, para uso do banco de dados
        this.arquivoID = "IdMarcasveiculos.txt";

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
