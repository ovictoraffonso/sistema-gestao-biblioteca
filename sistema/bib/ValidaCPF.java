package sistema.bib;

import java.io.Serializable;

public class ValidaCPF implements Serializable{

    public static final String FormatosValidos = "^\\d{3}\\.\\d{3}\\.\\d{3}[-/]\\d{2}$";

    public static final String ApenasNumeros = "^\\d{11}$";

    public static boolean isFormatoValido(String cpf) {
        if (cpf.length() == 11) {
            return cpf.matches(ApenasNumeros);
        }
        if (cpf.length() == 14) {
            return cpf.matches(FormatosValidos);
        }
        return false;
    }

    public static boolean isCPF(String cpf_sujo) {

        if (!isFormatoValido(cpf_sujo)) {
            System.out.println("cpf invalido: " + cpf_sujo);
            return false;
        }

        String CPF = cpf_sujo.replaceAll("\\D", "");

        if (CPF.matches("^(\\d)\\1{10}$"))
            throw new IllegalArgumentException("cpf invalido: "  + cpf_sujo);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;

            for (i = 0; i < 9; i++) {

                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);

            if ((r == 10) || (r == 11))
                dig10 = 0;
            else
                dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;

            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);

            if ((r == 10) || (r == 11))
                dig11 = 0;
            else
                dig11 = (char) (r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return true;
            else
                throw new IllegalArgumentException("cpf invalido: " + cpf_sujo);

        } catch (Exception e) {
            return false;
        }

    }

    public static long toLong(String cpf) {

        return Long.parseLong(cpf.replaceAll("\\D", ""));

    }
}
