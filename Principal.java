import sistema.bib.*;

public class Principal {
    public static void main(String[] args) {

        try {
            Biblioteca biblioteca = new Biblioteca("u.dat", "l.dat");
            Janela janela = new Janela(biblioteca.getUsuariosMap(), biblioteca.getLivrosMap());
            janela.MenuInicial();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Biblioteca não iniciada: " + e.getMessage());
        }

    }

}
