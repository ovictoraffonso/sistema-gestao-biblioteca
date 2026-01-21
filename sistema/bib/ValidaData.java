package sistema.bib;


import java.io.Serializable;
import java.time.LocalDate;


public class ValidaData implements Serializable {

    public static enum mesDoAno {
        JANEIRO(1, 31),
        FEVEREIRO(2, 28),
        MARCO(3, 31),
        ABRIL(4, 30),
        MAIO(5, 31),
        JUNHO(6, 30),
        JULHO(7, 31),
        AGOSTO(8, 31),
        SETEMBRO(9, 30),
        OUTUBRO(10, 31),
        NOVEMBRO(11, 30),
        DEZEMBRO(12, 31);

        private final int mesInteiro;

        mesDoAno(int numMes, int numDias) {
            this.mesInteiro = numMes;
        }

        

        public int getNumDia() {
            return this.mesInteiro;
        }

        public static mesDoAno fazMes(String nomeMes) {
            try {
                mesDoAno mes = mesDoAno.valueOf(nomeMes.toUpperCase());
                return mes;
            } catch (Exception e) {
                throw new IllegalArgumentException("Mes invalido: " + nomeMes);
            }
        }

        public static mesDoAno deNumero(int mesNumero) {
            for (mesDoAno mes : mesDoAno.values()) {
                if (mes.mesInteiro == mesNumero) {
                    return mes;
                }
            }
            throw new NumberFormatException("Número de mês inválido: " + mesNumero);
        }

    }

    public static boolean isDia(String dia) {
        try {

            int diaInt = Integer.parseInt(dia);
            return diaInt >= 1 && diaInt <= 31;

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean isMes(String mes) {
        try {
            int mesInt = Integer.parseInt(mes);
            return mesInt >= 1 && mesInt <= 12;

        } catch (NumberFormatException e) {
            try {
                mesDoAno.fazMes(mes);
                return true;
            } catch (Exception f) {
                return false;
            }
        }
    }

    public static boolean isAno(String ano) {
        int anoAtual = LocalDate.now().getYear();
        try {
            int intAno = Integer.parseInt(ano);
            return (anoAtual - intAno <= 120 && intAno > 1);

        } catch (NumberFormatException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    public static boolean isDataValida(String dia, String mesNome, String ano) {
        try {
            
            int anoInt = Integer.parseInt(ano);
            int diaInt = Integer.parseInt(dia);
            int mesInt;

           
            try {
                mesInt = Integer.parseInt(mesNome);
            } catch (NumberFormatException e) {
                
                mesInt = mesDoAno.fazMes(mesNome).getNumDia();
            }

            
            LocalDate.of(anoInt, mesInt, diaInt);

           
            LocalDate dataNascimento = LocalDate.of(anoInt, mesInt, diaInt);
            if (dataNascimento.isAfter(LocalDate.now())) {
                
                return false;
            }
            if (anoInt < LocalDate.now().getYear() - 120) {
                
                return false;
            }

            
            return true;

        } catch (Exception e) {

            return false;
        }

    }

}
