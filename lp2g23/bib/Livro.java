package lp2g23.bib;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    private int codigo;
    private String titulo;
    private String categoria;
    private int disponiveis;
    private int emprestados;
    private ArrayList<EmprestaPara> hist = new ArrayList<>();

    public Livro(int codigo, String titulo, String categoria, int disponiveis, int emprestados) {
        setCategoria(categoria);
        setCodigo(codigo);
        setDisponiveis(disponiveis);
        setEmprestados(emprestados);
        setTitulo(titulo);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getDisponiveis() {
        return disponiveis;
    }

    public void setDisponiveis(int disponiveis) {
        this.disponiveis = disponiveis;
    }

    public int getEmprestados() {
        return emprestados;
    }

    public void setEmprestados(int emprestados) {
        this.emprestados = emprestados;
    }

    public ArrayList<EmprestaPara> getHist() {
        return hist;
    }

    public void setHist(ArrayList<EmprestaPara> hist) {
        this.hist = hist;
    }

    public void empresta() throws CopiaNaoDisponivelEx {
        if (getDisponiveis() > 0) {
            this.emprestados++;
            this.disponiveis--;
        }

        else {
            throw new CopiaNaoDisponivelEx("Nenhuma copia disponivel!");
        }
    }

    public void devolve() throws NenhumaCopiaEmprestadaEx {
        if (getEmprestados() > 0) {
            this.disponiveis++;
            this.emprestados--;
        } else {
            throw new NenhumaCopiaEmprestadaEx("Nenhuma copia emprestada!");
        }
    }

    public void addUsuarioHist(LocalDate dataEmprest, LocalDate dataDevolucao, String cpfStr) {
        EmprestaPara p = new EmprestaPara(dataEmprest, dataDevolucao, cpfStr);
        hist.add(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Detalhes do Livro ===\n");
        sb.append("Código: ").append(codigo).append("\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Categoria: ").append(categoria).append("\n");
        sb.append("Disponíveis: ").append(disponiveis).append(" | Emprestados: ").append(emprestados).append("\n");

        sb.append("--- Histórico de Empréstimos ---\n");
        if (hist.isEmpty()) {
            sb.append("Nenhum histórico registrado.\n");
        } else {
            for (EmprestaPara registro : hist) {
                sb.append(registro.toString()).append("\n");
            }
        }
        return sb.toString();
    }

}
