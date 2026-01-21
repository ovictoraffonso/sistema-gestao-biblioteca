package sistema.bib;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Pessoa implements Serializable{
    private static int contagemPessoas = 0;
    private String nome;
    private String sobreNome;
    private String cpfStr;
    private LocalDate dataNasc;
    private long numCPF;
    private int idade;

    public Pessoa() {

    }

    public Pessoa(String nome, String sobreNome, String dia, String mes, String ano, String cpf) {
        Pessoa.contagemPessoas++;
        setNome(nome);
        setSobreNome(sobreNome);
        setIdade(dia, mes, ano);
        setCpf(cpf);
    }


    public static int getTotalPessoas() {
        return contagemPessoas;
    }

    public static boolean isApenasLetras(String texto) {
        return texto.matches("[a-zA-Z]+");
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio: " + nome);
        }
        if (!isApenasLetras(nome.trim())) {
            throw new IllegalArgumentException("O nome deve conter apenas letras: " + nome);
        }
        this.nome = nome;
    }

    public String getSobreNome() {
        return this.sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        if (sobreNome == null || sobreNome.trim().isEmpty()) {
            throw new IllegalArgumentException("O sobrenome não pode ser nulo ou vazio: " + sobreNome);
        }
        if (!isApenasLetras(sobreNome.trim())) {
            throw new IllegalArgumentException("O sobrenome deve conter apenas letras: " + sobreNome);
        }
        this.sobreNome = sobreNome;
    }

    public int getIdade() {
        return this.idade;
    }

    public LocalDate getDataNasc() {
        return this.dataNasc;
    }

    public void setIdade(String dia, String mes, String ano) {
        LocalDate hoje = LocalDate.now();
        LocalDate data;
        try {
            int diaInt = Integer.parseInt(dia);
            int anoInt = Integer.parseInt(ano);
            int mesInt;
            try {
                mesInt = Integer.parseInt(mes);
            } catch (NumberFormatException e) {
                try {
                    mesInt = ValidaData.mesDoAno.fazMes(mes).getNumDia();
                } catch (Exception ef) {
                    throw new IllegalArgumentException("Mes deve ser inteiro ou literal. Ex 8, 08 ou agosto: " + mes);
                }
            }
            data = LocalDate.of(anoInt, mesInt, diaInt);

        } catch (Exception e) {
            throw new IllegalArgumentException("Data Invalida: " + dia + "/" + mes + "/" + ano + ". " + e.getMessage());
        }

        if (data.isAfter(hoje)) {
            throw new IllegalArgumentException("Data invalida: Data de nascimento nao pode ser futura");
        }

        this.dataNasc = data;
        this.idade = Period.between(this.dataNasc, hoje).getYears();
    }

    public long getCpf() {
        return this.numCPF;
    }

    public String getCpfStr() {
        return this.cpfStr;
    }

    public void setCpf(String cpf) {
        if (ValidaCPF.isCPF(cpf)) {
            this.numCPF = ValidaCPF.toLong(cpf);
            this.cpfStr = cpf;
        } else
            throw new IllegalArgumentException("Cpf invalido: " + cpf);
    }

    public String toString() {
        return "Nome: " + getNome() + " " + getSobreNome() + "\n" +
                "Data de Nascimento: " + getDataNasc().getDayOfMonth() + "/" + getDataNasc().getMonthValue() + "/"
                + getDataNasc().getYear() + "\n" +
                "Idade: " + getIdade() + "\n" +
                "Cpf: " + getCpfStr();
    }

}
