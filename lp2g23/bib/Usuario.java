package lp2g23.bib;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Pessoa {
    private static final long serialVersionUID = 1L;
    private String endereco;
    private ArrayList<Emprest> hist = new ArrayList<>();
    private int livrosEmprest;

    public Usuario(String nome, String sobreNome, String dia, String mes, String ano, String cpf, String endereco, ArrayList<Emprest> hist, int livrosEmprest) {
        super(nome, sobreNome, dia, mes, ano, cpf);
        setEndereco(endereco);
        setHist(hist);
        livrosEmprest = 0;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Emprest> getHist() {
        return hist;
    }

    public void setHist(ArrayList<Emprest> hist) {
        this.hist = hist;
    }

    public int getLivrosEmprest(){
        return livrosEmprest;
    }

    public void addLivroHist(LocalDate dataLocacao, int codigo){
        Emprest p = new Emprest(codigo, dataLocacao, null);
        this.hist.add(p);

    }

    public void setLivrosEmprest(int num){
        this.livrosEmprest =+ num;
    }

    public void registrarDevolucao(int codigoLivro) {
        for (int i = hist.size() - 1; i >= 0; i--) {
            Emprest e = hist.get(i);
            
            if (e.getCodigoLivro() == codigoLivro && e.getDataDevolucao() == null) {
                e.setDataDevolucao(LocalDate.now());
                this.livrosEmprest--; 
                return; 
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n"); 
        
        sb.append("Endereço: ").append(endereco).append("\n");
        sb.append("Livros em posse: ").append(livrosEmprest).append("\n");
        
        sb.append("--- Histórico do Usuário ---\n");
        if (hist.isEmpty()) {
            sb.append("Nenhum histórico registrado.\n");
        } else {
            for (Emprest registro : hist) {
                sb.append(registro.toString()).append("\n");
            }
        }
        return sb.toString();
    }

}
