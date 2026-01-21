package sistema.bib;

import java.io.Serializable;
import java.time.LocalDate;

public class EmprestaPara implements Serializable{

    private LocalDate dataEmprest;
    private LocalDate dataDevolucao;
    private String cpfString;

    public EmprestaPara(LocalDate dataEmprest, LocalDate dataDevolucao, String cpfStr) {
        setCpfString(cpfStr);
        setDataDevolucao(dataDevolucao);
        setDataEmprest(dataEmprest);
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

    public String getCpfString() {
        return cpfString;
    }

    public void setCpfString(String cpfString) {
        this.cpfString = cpfString;
    }

    @Override
    public String toString() {
        return "CPF Usuário: " + cpfString +
                " | Empréstimo: " + dataEmprest +
                " | Devolução: " + (dataDevolucao != null ? dataDevolucao : "Pendente");
    }

}
