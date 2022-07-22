/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author tanak
 */
public abstract class DataBase {

    /*  * classe pai de todos as classes que serão salvas no banco de dados
         *
        * salvando ccoom abstrata apennas para não ser inicializadas
     */
    protected int quantidadeDeDadosNoBanco;
    protected String NomeArquivoDisco;
    protected String arquivoID;

    protected boolean cadastroAtivo = true;

    public DataBase(int quantidadeDeDadosNoBanco, String NomeArquivoDisco, String arquivoID) {
        this.quantidadeDeDadosNoBanco = quantidadeDeDadosNoBanco;
        this.NomeArquivoDisco = NomeArquivoDisco;
        this.arquivoID = arquivoID;
    }

    public boolean isCadastroAtivo() {
        return cadastroAtivo;
    }

    public void setCadastroAtivo(boolean cadastroAtivo) {
        this.cadastroAtivo = cadastroAtivo;
    }

    public String getNomeArquivoDisco() {
        return NomeArquivoDisco;
    }

    public String getArquivoID() {
        return arquivoID;
    }

}
