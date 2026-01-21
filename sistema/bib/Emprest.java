package sistema.bib;

import java.io.Serializable;
import java.time.LocalDate;

public class Emprest implements Serializable{
    private LocalDate dataEmprest;
    private LocalDate dataDevolucao;
    private LocalDate dataLimite;
    private int codigoLivro;
 
    public Emprest(int codigoLivro, LocalDate dataEmprest, LocalDate dataDevolucao){
        setCodigoLivro(codigoLivro);
        setDataDevolucao(dataDevolucao);
        setDataEmprest(dataEmprest);
        setDataLimite(dataDevolucao);
    }

    public LocalDate getDataEmprest() {
        return dataEmprest;
    }

    public void setDataEmprest(LocalDate dataEmprest) {
        this.dataEmprest = dataEmprest;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(int codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataEmprest.plusWeeks(3);
    }
    
    @Override
    public String toString() {
        return "Livro (Cód): " + codigoLivro + 
               " | Empréstimo: " + dataEmprest + 
               " | Devolução: " + (dataDevolucao != null ? dataDevolucao : "Pendente") +
               " | Limite: " + dataLimite;
    }
}
