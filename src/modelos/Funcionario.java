/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import exceptions.InvalidInputException;
import modelos.auxiliares.Endereco;
import java.util.Date;

/**
 *
 * @author ALUNO
 */
public class Funcionario extends PessoaFisica {

    private String especialidade;
    private double salarioMensal;
    private double salariohora;
    private int matricula;

    public Funcionario() throws InvalidInputException {

        super("emailNaoInformado@email.com", new Endereco(), "99999999999", "99999999999", "99999999999");
    }

    public Funcionario(String especialidade, double salarioMensal, double salariohora, int matricula, String nome, String cpf, Date dataNascimento, String email, Endereco endereco, String... telefone) throws InvalidInputException {
        super(nome, cpf, dataNascimento, email, endereco, telefone);
        this.especialidade = especialidade;
        this.salarioMensal = salarioMensal;
        this.salariohora = salariohora;
        this.matricula = matricula;

    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public void setSalarioMensal(double salarioMensal) {
        this.salarioMensal = salarioMensal;
    }

    public double getSalariohora() {
        return salariohora;
    }

    public void setSalariohora(double salariohora) {
        this.salariohora = salariohora;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.matricula;
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
        final Funcionario other = (Funcionario) obj;
        return this.matricula == other.matricula || this.cpf.equals(other.cpf);
    }

    @Override
    public String toString() {
        return super.toString() + ";" + especialidade + ";" + String.format("%.2f;%.2f", salarioMensal, salariohora).replace(",", ".") + ";" + matricula;
    }

}
