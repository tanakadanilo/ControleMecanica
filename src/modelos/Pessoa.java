// Essa classe será usada como a superclasse de todas as classes que tenham pessoas, pois teram vários atributos iguais
package modelos;

import java.util.Arrays;
import modelos.auxiliares.Endereco;
import java.util.InputMismatchException;

/**
 *
 * @author tanak
 */
public abstract class Pessoa extends ExclusaoLogica {

    protected String[] telefone = new String[3];
    protected String email;
    protected Endereco endereco;

    public Pessoa() {
    }

    public Pessoa(String email, Endereco endereco, String[] telefone) {
        if (!validaTelefone(telefone[0]) || !validaTelefone(telefone[1]) || !validaTelefone(telefone[2])) {
            System.out.println(telefone.length);
            System.out.println(Arrays.toString(telefone));
            System.out.println(telefone[0] + "\t" + telefone[1] + "\t" + telefone[2]);
            if (!validaTelefone(telefone[0])) {
                throw new InputMismatchException("O Telefone:\"" + telefone[0] + "\" é inválido");
            }
            if (!validaTelefone(telefone[1])) {
                throw new InputMismatchException("O Telefone:\"" + telefone[1] + "\" é inválido");
            }
            if (!validaTelefone(telefone[2])) {
                throw new InputMismatchException("O Telefone:\"" + telefone[2] + "\" é inválido");
            }
        }
        if (!validaEmail(email)) {
            throw new InputMismatchException("O email \"" + email + "\" é inválido");
        }
        this.telefone[0] = telefone[0].trim();//    * retirando espaços em branco
        this.telefone[1] = telefone[1].trim();//    * retirando espaços em branco
        this.telefone[2] = telefone[2].trim();//    * retirando espaços em branco
        this.email = email;
        this.endereco = endereco;
    }

    public String[] getTelefone() {
        return telefone;
    }

    public void setTelefone(String[] telefone) {
        if (!validaTelefone(telefone[0]) || !validaTelefone(telefone[1]) || !validaTelefone(telefone[2])) {
            throw new InputMismatchException("Telefone inválido");
        }
        this.telefone[0] = formataTelefone(telefone[0]);
        this.telefone[1] = formataTelefone(telefone[1]);
        this.telefone[2] = formataTelefone(telefone[2]);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean validaTelefone(String telefoneParaValidar) {
        telefoneParaValidar = telefoneParaValidar.trim();
        if (telefoneParaValidar.matches("^\\([1-9]{2}\\) 9[7-9]{1}[0-9]{3}\\-[0-9]{4}$")) {
            return true;
        } else {
            telefoneParaValidar = formataTelefone(telefoneParaValidar);//formatando o telefone para colocar os parenteses no DDD e o hifen no meio do numero
            return (telefoneParaValidar.matches("^\\([1-9]{2}\\) 9[7-9]{1}[0-9]{3}\\-[0-9]{4}$"));
        }
        // o método matches, diz se uma String segue o padrão informado, que no caso é o padrão de telefone, com parênteses e o hifen "(xx) 9XXXX-XXXX"
    }

    public String formataTelefone(String telefoneParaFormatar) {
        if (telefoneParaFormatar.length() < 7) {//se for menor q 7 não dá pra formatar
            return "O Telefone: " + telefoneParaFormatar + "é inválido";
        }

        StringBuilder telefoneFormatado = new StringBuilder();
        telefoneFormatado.append('(').append(telefoneParaFormatar.substring(0, 2)).append(')');
        telefoneFormatado.append(' ').append(telefoneParaFormatar.substring(2, 7)).append('-');
        telefoneFormatado.append(telefoneParaFormatar.substring(7));

        return String.valueOf(telefoneFormatado);
    }

    private boolean validaEmail(String email) {
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    @Override
    public String toString() {
        return Arrays.toString(telefone) + ";" + email + ";" + endereco.toString() + ";" + this.isCadastroAtivo();
    }
}
