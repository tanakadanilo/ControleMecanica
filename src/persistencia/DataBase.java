/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author tanak
 */
public abstract class DataBase {

    /*  * classe pai de todos as classes que serão salvas no banco de dados
         *
         * salvando ccoom abstrata apennas para não ser inicializadas
     */
    protected final int quantidadeDeDadosNoBanco;
    protected final String NomeArquivoDisco;
    protected final String arquivoID;

    public DataBase(int quantidadeDeDadosNoBanco, String NomeArquivoDisco) {
        this.quantidadeDeDadosNoBanco = quantidadeDeDadosNoBanco;
        this.NomeArquivoDisco = NomeArquivoDisco;
        this.arquivoID = "Id" + NomeArquivoDisco;
    }

    public String getNomeArquivoDisco() {
        return NomeArquivoDisco;
    }

    public String getArquivoID() {
        return arquivoID;
    }

}
