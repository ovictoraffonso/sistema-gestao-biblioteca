import java.util.HashMap;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import lp2g23.bib.*;

public class Janela {
    private JFrame home = new JFrame();
    private HashMap<String, Usuario> usu;
    private HashMap<Integer, Livro> liv;

    private final String ARQ_USUARIOS = "u.dat";
    private final String ARQ_LIVROS = "l.dat";

    public Janela(HashMap<String, Usuario> usu, HashMap<Integer, Livro> liv) {
        this.usu = usu;
        this.liv = liv;
        home.setSize(800, 600);
        home.setLocationRelativeTo(null);
    }

    public void MenuInicial() {
        home.setSize(800, 600);
        home.setLocationRelativeTo(null);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        montarMenuPrincipal();
        home.setVisible(true);
    }

    public void montarMenuPrincipal() {
        home.getContentPane().removeAll();

        JLabel titulo = new JLabel("BIBLIOTECA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        home.add(titulo, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1, 0, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));

        JButton bManutencao = new JButton("MANUTENÇÃO");
        JButton bCadastro = new JButton("CADASTRO");
        JButton bEmprestimo = new JButton("EMPRÉSTIMO");
        JButton bRelatorio = new JButton("RELATÓRIO");

        bManutencao.addActionListener(e -> abrirTelaManutencao());
        bCadastro.addActionListener(e -> abrirTelaCadastro());
        bEmprestimo.addActionListener(e -> abrirTelaEmprestimo());
        bRelatorio.addActionListener(e -> abrirTelaRelatorio());

        menuPanel.add(bManutencao);
        menuPanel.add(bCadastro);
        menuPanel.add(bEmprestimo);
        menuPanel.add(bRelatorio);

        home.add(menuPanel, BorderLayout.CENTER);
        home.revalidate();
        home.repaint();
    }

    public void abrirTelaManutencao() {
        home.getContentPane().removeAll();

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(7, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JLabel lTitulo = new JLabel("MANUTENÇÃO", JLabel.CENTER);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel lSubtitulo = new JLabel("Escolha uma opção:", JLabel.CENTER);

        JButton bZerar = new JButton("ZERAR ARQUIVOS");
        JButton bCarregar = new JButton("CARREGAR ARQUIVOS");
        JButton bPolitica = new JButton("POLÍTICA DA BIBLIOTECA");
        JButton bSalvar = new JButton("SALVAR ARQUIVOS");
        JButton bVoltar = new JButton("VOLTAR");

        bZerar.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(home,
                    "ATENÇÃO: Isso apagará todos os dados da memória.\nDeseja continuar?",
                    "Zerar Dados", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                usu.clear();
                liv.clear();
                JOptionPane.showMessageDialog(home, "Memória limpa!");
            }
        });

        bCarregar.addActionListener(e -> {
            try {
                FileInputStream fisU = new FileInputStream(ARQ_USUARIOS);
                ObjectInputStream oisU = new ObjectInputStream(fisU);

                @SuppressWarnings("unchecked")
                HashMap<String, Usuario> uLido = (HashMap<String, Usuario>) oisU.readObject();

                usu.putAll(uLido);
                oisU.close();

                FileInputStream fisL = new FileInputStream(ARQ_LIVROS);
                ObjectInputStream oisL = new ObjectInputStream(fisL);

                @SuppressWarnings("unchecked")
                HashMap<Integer, Livro> lLido = (HashMap<Integer, Livro>) oisL.readObject();

                liv.putAll(lLido);
                oisL.close();

                JOptionPane.showMessageDialog(home, "Dados carregados com sucesso!");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(home, "Arquivos não encontrados. (Ainda não foram salvos?)");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(home, "Erro ao carregar: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        bSalvar.addActionListener(e -> {
            try {
                FileOutputStream fosU = new FileOutputStream(ARQ_USUARIOS);
                ObjectOutputStream oosU = new ObjectOutputStream(fosU);
                oosU.writeObject(usu);
                oosU.close();

                FileOutputStream fosL = new FileOutputStream(ARQ_LIVROS);
                ObjectOutputStream oosL = new ObjectOutputStream(fosL);
                oosL.writeObject(liv);
                oosL.close();

                JOptionPane.showMessageDialog(home, "Dados salvos com sucesso em " + ARQ_USUARIOS + " e " + ARQ_LIVROS);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(home, "Erro ao salvar: " + ex.getMessage());
            }
        });

        bPolitica.addActionListener(e -> JOptionPane.showMessageDialog(home,
                "Política da Biblioteca:\n\n- Prazo de devolução: 10 dias\n- Multa: R$ 0,25/dia\n- Limite: 3 livros por usuário",
                "Política", JOptionPane.INFORMATION_MESSAGE));

        bVoltar.addActionListener(e -> montarMenuPrincipal());

        painel.add(lTitulo);
        painel.add(lSubtitulo);
        painel.add(bZerar);
        painel.add(bCarregar);
        painel.add(bPolitica);
        painel.add(bSalvar);
        painel.add(bVoltar);

        home.add(painel);
        home.revalidate();
        home.repaint();
    }

    public void abrirTelaCadastro() {
        home.getContentPane().removeAll();

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        JLabel lTitulo = new JLabel("CADASTRO", JLabel.CENTER);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton bCadLivro = new JButton("Cadastrar Livro");
        JButton bCadUsuario = new JButton("Cadastrar Usuário");
        JButton bVoltar = new JButton("VOLTAR");

        bVoltar.addActionListener(e -> montarMenuPrincipal());

        bCadLivro.addActionListener(e -> {
            JTextField txtCod = new JTextField();
            JTextField txtTit = new JTextField();
            JTextField txtCat = new JTextField();
            JTextField txtQtd = new JTextField();

            Object[] form = {
                    "Código (int):", txtCod,
                    "Título:", txtTit,
                    "Categoria:", txtCat,
                    "Quantidade Total:", txtQtd
            };

            int op = JOptionPane.showConfirmDialog(home, form, "Novo Livro", JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                try {
                    int codigo = Integer.parseInt(txtCod.getText());
                    int qtd = Integer.parseInt(txtQtd.getText());
                    String titulo = txtTit.getText();
                    String categoria = txtCat.getText();

                    if (liv.containsKey(codigo)) {
                        JOptionPane.showMessageDialog(home, "Erro: Já existe um livro com este código!");
                    } else {
                        Livro novo = new Livro(codigo, titulo, categoria, qtd, 0);
                        liv.put(codigo, novo);
                        JOptionPane.showMessageDialog(home, "Livro cadastrado!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(home, "Erro: Código e Quantidade devem ser números inteiros.");
                }
            }
        });

        bCadUsuario.addActionListener(e -> {
            JTextField txtNome = new JTextField();
            JTextField txtSobre = new JTextField();
            JTextField txtCpf = new JTextField();
            JTextField txtNasc = new JTextField("dd/mm/aaaa");
            JTextField txtEnd = new JTextField();

            Object[] form = {
                    "Nome:", txtNome,
                    "Sobrenome:", txtSobre,
                    "CPF:", txtCpf,
                    "Data Nasc (dd/mm/aaaa):", txtNasc,
                    "Endereço:", txtEnd
            };

            int op = JOptionPane.showConfirmDialog(home, form, "Novo Usuário", JOptionPane.OK_CANCEL_OPTION);
            if (op == JOptionPane.OK_OPTION) {
                try {
                    String nome = txtNome.getText();
                    String sobre = txtSobre.getText();
                    String cpf = txtCpf.getText();
                    String endereco = txtEnd.getText();

                    String[] dataParts = txtNasc.getText().split("/");
                    if (dataParts.length != 3)
                        throw new Exception("Data inválida! Use dd/mm/aaaa");
                    String dia = dataParts[0];
                    String mes = dataParts[1];
                    String ano = dataParts[2];

                    if (usu.containsKey(cpf)) {
                        JOptionPane.showMessageDialog(home, "Erro: CPF já cadastrado!");
                    } else {
                        Usuario novo = new Usuario(nome, sobre, dia, mes, ano, cpf, endereco, new ArrayList<Emprest>(),
                                0);
                        usu.put(cpf, novo);
                        JOptionPane.showMessageDialog(home, "Usuário cadastrado!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(home, "Erro no cadastro: " + ex.getMessage());
                }
            }
        });

        painel.add(lTitulo);
        painel.add(bCadLivro);
        painel.add(bCadUsuario);
        painel.add(bVoltar);

        home.add(painel);
        home.revalidate();
        home.repaint();
    }

    public void abrirTelaEmprestimo() {
        home.getContentPane().removeAll();

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

        JLabel lTitulo = new JLabel("EMPRÉSTIMO", JLabel.CENTER);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField txtLivro = new JTextField();
        txtLivro.setBorder(BorderFactory.createTitledBorder("Código do Livro:"));

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("CPF do Usuário:"));

        JPanel pBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton bEmprestar = new JButton("Emprestar");
        JButton bDevolver = new JButton("Devolver");
        pBotoes.add(bEmprestar);
        pBotoes.add(bDevolver);

        JButton bVoltar = new JButton("VOLTAR");
        bVoltar.addActionListener(e -> montarMenuPrincipal());

        bEmprestar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtLivro.getText());
                String cpf = txtUsuario.getText();

                Livro l = liv.get(cod);
                Usuario u = usu.get(cpf);

                if (l == null)
                    throw new Exception("Livro não encontrado.");
                if (u == null)
                    throw new Exception("Usuário não encontrado.");

                l.empresta();
                u.addLivroHist(LocalDate.now(), cod);
                u.setLivrosEmprest(u.getLivrosEmprest() + 1);

                l.addUsuarioHist(LocalDate.now(), LocalDate.now().plusDays(10), cpf);

                JOptionPane.showMessageDialog(home, "Empréstimo realizado com sucesso!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(home, "Código do livro deve ser um número.");
            } catch (Exception ex) {

                JOptionPane.showMessageDialog(home, "Erro: " + ex.getMessage());
            }
        });

        bDevolver.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtLivro.getText());
                String cpf = txtUsuario.getText();

                Livro l = liv.get(cod);
                Usuario u = usu.get(cpf);

                if (l == null)
                    throw new Exception("Livro não encontrado.");
                if (u == null)
                    throw new Exception("Usuário não encontrado.");
                u.registrarDevolucao(cod);

                l.devolve();

                JOptionPane.showMessageDialog(home, "Livro devolvido com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(home, "Erro: " + ex.getMessage());
                ex.printStackTrace(); 
            }
        });

        painel.add(lTitulo);
        painel.add(txtLivro);
        painel.add(txtUsuario);
        painel.add(pBotoes);
        painel.add(new JLabel(""));
        painel.add(bVoltar);

        home.add(painel);
        home.revalidate();
        home.repaint();
    }

    public void abrirTelaRelatorio() {
        home.getContentPane().removeAll();

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        JLabel lTitulo = new JLabel("RELATÓRIO", JLabel.CENTER);
        lTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton bGeral = new JButton("Listar Tudo (Geral)");
        JButton bHistUser = new JButton("Histórico de Usuário");
        JButton bHistLivro = new JButton("Histórico de Livro");
        JButton bVoltar = new JButton("VOLTAR");

        bVoltar.addActionListener(e -> montarMenuPrincipal());

        bGeral.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("===== RELATÓRIO GERAL =====\n\n");

            sb.append("--- LIVROS CADASTRADOS ---\n");
            for (Livro l : liv.values()) {
                sb.append(l.toString()).append("\n-----------------\n");
            }

            sb.append("\n--- USUÁRIOS CADASTRADOS ---\n");
            for (Usuario u : usu.values()) {
                sb.append(u.toString()).append("\n-----------------\n");
            }

            mostrarTextoScroll(sb.toString(), "Relatorio Geral");
        });

        bHistUser.addActionListener(e -> {
            String cpf = JOptionPane.showInputDialog(home, "Digite o CPF do usuario:");
            if (cpf != null && usu.containsKey(cpf)) {
                mostrarTextoScroll(usu.get(cpf).toString(), "Detalhes do usuario");
            } else if (cpf != null) {
                JOptionPane.showMessageDialog(home, "Usuario nao encontrado.");
            }
        });

        bHistLivro.addActionListener(e -> {
            String codStr = JOptionPane.showInputDialog(home, "Digite o codigo do livro:");
            try {
                if (codStr != null) {
                    int cod = Integer.parseInt(codStr);
                    if (liv.containsKey(cod)) {
                        mostrarTextoScroll(liv.get(cod).toString(), "Detalhes do Livro");
                    } else {
                        JOptionPane.showMessageDialog(home, "Livro não encontrado.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(home, "Codigo invalido.");
            }
        });

        painel.add(lTitulo);
        painel.add(bGeral);
        painel.add(bHistUser);
        painel.add(bHistLivro);
        painel.add(bVoltar);

        home.add(painel);
        home.revalidate();
        home.repaint();
    }

    private void mostrarTextoScroll(String texto, String titulo) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(home, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}