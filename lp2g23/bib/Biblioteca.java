package lp2g23.bib;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.time.LocalDate;

@SuppressWarnings("unchecked")
public class Biblioteca implements Serializable {
    private static final long serialVersionUID = 1L; 
    private HashMap<String, Usuario> usuarios;
    private HashMap<Integer, Livro> livros;

    public Biblioteca() {
        usuarios = new HashMap<>();
        livros = new HashMap<>();
    }

    public Biblioteca(String arquivoUsuario, String arquivoLivros) throws IOException, ClassNotFoundException {
        usuarios = new HashMap<>();
        livros = new HashMap<>();
        
        File Fusuarios = new File(arquivoUsuario);
        File Flivros = new File(arquivoLivros);

        if (Fusuarios.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Fusuarios));
            this.usuarios = (HashMap<String, Usuario>) ois.readObject();
            ois.close();
        } else {
             throw new FileNotFoundException("Arquivo de usuários não encontrado: " + arquivoUsuario);
        }

        if (Flivros.exists()) {
            ObjectInputStream oil = new ObjectInputStream(new FileInputStream(Flivros));
            this.livros = (HashMap<Integer, Livro>) oil.readObject();
            oil.close();
        } else {
             throw new FileNotFoundException("Arquivo de livros não encontrado: " + arquivoLivros);
        }
    }

    public HashMap<String, Usuario> getUsuariosMap() { return this.usuarios; }
    public HashMap<Integer, Livro> getLivrosMap() { return this.livros; }

    public Usuario getUsuario(String cpf) throws UsuarioNaoCadastradoEx {
        if (!this.usuarios.containsKey(cpf)) {
            throw new UsuarioNaoCadastradoEx("Usuário com CPF " + cpf + " não encontrado.");
        }
        return this.usuarios.get(cpf);
    }

    public Livro getLivro(int cod) throws LivroNaoCadastradoEx {
        if (!this.livros.containsKey(cod)) {
            throw new LivroNaoCadastradoEx("Livro com código " + cod + " não encontrado.");
        }
        return this.livros.get(cod);
    }

    public void cadastraUsuário(Usuario usu) {
        String Chavecpf = usu.getCpfStr();
        if (this.usuarios.containsKey(Chavecpf)) {
            throw new IllegalArgumentException("Usuário já cadastrado com o CPF " + Chavecpf);
        }
        this.usuarios.put(Chavecpf, usu);
        System.out.println("Usuario cadastrado!");
    }

    public void cadastraLivro(Livro liv) {
        int ChaveCodigo = liv.getCodigo();
        if (this.livros.containsKey(ChaveCodigo)) {
            throw new IllegalArgumentException("Livro já cadastrado com o código " + ChaveCodigo);
        }
        this.livros.put(ChaveCodigo, liv);
        System.out.println("Livro cadastrado!");
    }

    public void salvaArquivo(HashMap<?, ?> hash, String nomeArquivo) throws IOException {
        File f = new File(nomeArquivo);
        ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(f));
        ois.writeObject(hash);
        ois.close();
        System.out.println("Arquivo salvo: " + nomeArquivo);
    }

    public void lêArqUsu(String nomeArquivo) throws IOException, ClassNotFoundException {
        File f = new File(nomeArquivo);
        ObjectInputStream out = new ObjectInputStream(new FileInputStream(f));
        this.usuarios = (HashMap<String, Usuario>) out.readObject();
        out.close();
    }

    public void lêArqLiv(String nomeArquivo) throws IOException, ClassNotFoundException {
        File f = new File(nomeArquivo);
        ObjectInputStream out = new ObjectInputStream(new FileInputStream(f));
        this.livros = (HashMap<Integer, Livro>) out.readObject();
        out.close();
    }

    public void emprestaLivro(Usuario uso, Livro liv) throws CopiaNaoDisponivelEx {
        liv.empresta();
        liv.addUsuarioHist(LocalDate.now(), null, uso.getCpfStr());
        uso.addLivroHist(LocalDate.now(), liv.getCodigo());
        uso.setLivrosEmprest(1);
    }

    public void devolveLivro(Usuario uso, Livro liv) throws NenhumaCopiaEmprestadaEx {
        liv.devolve();
        LocalDate hoje = LocalDate.now();

        for (Emprest emp : uso.getHist()) {
            if (emp.getCodigoLivro() == liv.getCodigo() && emp.getDataDevolucao() == null) {
                emp.setDataDevolucao(hoje);
                break;
            }
        }

        for (EmprestaPara ep : liv.getHist()) {
            if (ep.getCpfString().equals(uso.getCpfStr()) && ep.getDataDevolucao() == null) {
                ep.setDataDevolucao(hoje);
                break;
            }
        }
        System.out.println("Livro devolvido e registros atualizados.");
    }

    public String imprimeLivros() {
        ArrayList<Livro> lista = new ArrayList<>(this.livros.values());
        Collections.sort(lista, (l1, l2) -> Integer.compare(l1.getCodigo(), l2.getCodigo()));
        StringBuilder resultado = new StringBuilder("=== RELATÓRIO DE LIVROS ===\n");
        for (Livro l : lista) {
            resultado.append(l.toString()).append("\n");
        }
        return resultado.toString();
    }

    public String imprimeUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>(this.usuarios.values());
        Collections.sort(lista, (u1, u2) -> u1.getCpfStr().compareTo(u2.getCpfStr()));
        StringBuilder resultado = new StringBuilder("=== RELATÓRIO DE USUÁRIOS ===\n");
        for (Usuario u : lista) {
            resultado.append(u.toString()).append("\n");
        }
        return resultado.toString();
    }
}