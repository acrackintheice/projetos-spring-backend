package br.ufsc.framework.services.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class Validation {

    private Validation() {
    }

    public static boolean validateBlank(String string) {
        return string != null && !string.trim().equals("");
    }

    public static boolean validateNull(Object o) {
        return o != null;
    }

    public static boolean validateIP(String ip) {
        try {
            return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateLength(String string, Integer min, Integer max) {
        try {
            return ((min != null) ? string.length() >= min : true) &&

                ((max != null) ? string.length() <= max : true);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateBetween(Integer num, Integer min, Integer max) {
        try {
            return ((min != null) ? num >= min : true) && ((max != null) ? num <= max : true);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateAlphaNumeric(String str) {
        return str.matches("[\\p{L}\\p{N}]+");
    }

    public static boolean compareBeans(Object o1, Object o2) {
        try {
            return PropertyUtils.describe(o1).equals(PropertyUtils.describe(o2));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateCPF(String cpf) {

        if (cpf == null)
            return false;

        if (cpf.trim().equals("00000000000"))
            return false;

        if (cpf.trim().equals("11111111111"))
            return false;

        if (cpf.trim().equals("22222222222"))
            return false;

        if (cpf.trim().equals("33333333333"))
            return false;

        if (cpf.trim().equals("44444444444"))
            return false;

        if (cpf.trim().equals("55555555555"))
            return false;

        if (cpf.trim().equals("66666666666"))
            return false;

        if (cpf.trim().equals("77777777777"))
            return false;

        if (cpf.trim().equals("88888888888"))
            return false;

        if (cpf.trim().equals("99999999999"))
            return false;

        if (NumberUtils.isDigits(cpf) && new Long(cpf) < 1000)
            return false;

        if (cpf.length() == 11) {
            int d1, d2;
            int digito1, digito2, resto;
            int digitoCPF;
            String nDigResult;
            d1 = d2 = 0;
            digito1 = digito2 = resto = 0;
            for (int n_Count = 1; n_Count < cpf.length() - 1; n_Count++) {
                digitoCPF = Integer.valueOf(cpf.substring(n_Count - 1, n_Count));
                // --------- Multiplique a ultima casa por 2 a seguinte por 3 a
                // seguinte por 4 e assim por diante.
                d1 = d1 + (11 - n_Count) * digitoCPF;
                // --------- Para o segundo digito repita o procedimento
                // incluindo o primeiro digito calculado no passo anterior.
                d2 = d2 + (12 - n_Count) * digitoCPF;
            }
            ;
            // --------- Primeiro resto da divisão por 11.
            resto = (d1 % 11);
            // --------- Se o resultado for 0 ou 1 o digito é 0 caso contrário o
            // digito é 11 menos o resultado anterior.
            if (resto < 2)
                digito1 = 0;
            else
                digito1 = 11 - resto;
            d2 += 2 * digito1;
            // --------- Segundo resto da divisão por 11.
            resto = (d2 % 11);
            // --------- Se o resultado for 0 ou 1 o digito é 0 caso contrário o
            // digito é 11 menos o resultado anterior.
            if (resto < 2)
                digito2 = 0;
            else
                digito2 = 11 - resto;
            // --------- Digito verificador do CPF que está sendo validado.
            String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
            // --------- Concatenando o primeiro resto com o segundo.
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
            // --------- Comparar o digito verificador do cpf com o primeiro
            // resto + o segundo resto.
            return nDigVerific.equals(nDigResult);
        } else {
            return false;
        }

    }

    public static boolean validateCNPJ(String cnpj) {
        if (cnpj.length() == 14) {
            int soma = 0, dig;
            String cnpj_calc = cnpj.substring(0, 12);
            char[] chr_cnpj = cnpj.toCharArray();
            // --------- Primeira parte
            for (int i = 0; i < 4; i++)
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            for (int i = 0; i < 8; i++)
                if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
            // --------- Segunda parte
            soma = 0;
            for (int i = 0; i < 5; i++)
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                    soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            for (int i = 0; i < 8; i++)
                if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                    soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
            return cnpj.equals(cnpj_calc);
        } else {
            return false;
        }
    }


    //Validar Título de Eleitor
    public static boolean validateTituloEleitor(String titulo) {

        try {
            titulo = new DecimalFormat("000000000000").format(new Long(titulo));


            Integer[] fatores_te_dv1 = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2};
            Integer[] fatores_te_dv2 = new Integer[]{4, 3, 2};


            Integer uf = Integer.parseInt(titulo.substring(8, 10));

            String valor = titulo.substring(0, 8);
            Integer soma, resto, digito;
            soma = 0;
            for (int i = 0; i < valor.length(); i++) {
                soma += (Integer.parseInt(valor.substring(i, i + 1)) * fatores_te_dv1[i]);
            }
            resto = soma % 11;
            digito = resto <= 1 ? 0 : 11 - resto;

            Integer r1 = resto;
            Integer d1 = digito;

            // Se for SP ou MG
            if ((uf == 1 || uf == 2) && r1 == 0) {
                d1 = 1;
            }


            //--------
            valor = titulo.substring(8, 10) + d1;
            soma = 0;
            for (int i = 0; i < valor.length(); i++) {
                soma += (Integer.parseInt(valor.substring(i, i + 1)) * fatores_te_dv2[i]);
            }
            resto = soma % 11;
            digito = resto <= 1 ? 0 : 11 - resto;

            Integer r2 = resto;
            Integer d2 = digito;


            // Se for SP ou MG
            if ((uf == 1 || uf == 2) && r2 == 0) {
                d2 = 1;
            }

            return (d1.equals(Integer.parseInt(titulo.substring(10, 11))) && d2.equals(Integer.parseInt(titulo.substring(11, 12))));

        } catch (Exception e) {
            return false;
        }
    }


    public static boolean validatePIS(String strPIS) {
        char i, j, somatorio = 0;
        char chDigitoVerificador;
        char chPISAux;

        try {
            for (i = 0, j = 4; j >= 2; i++, j--)
                somatorio += ((strPIS.charAt(i) - 0x30) * j);
            for (j = 9; j >= 2; i++, j--)
                somatorio += ((strPIS.charAt(i) - 0x30) * j);
            if ((somatorio % 11) < 2) {
                chDigitoVerificador = 0;
            } else {
                chDigitoVerificador = (char) (11 - (somatorio % 11));
            }
            chPISAux = (char) (chDigitoVerificador + '0');
            if (strPIS.charAt(11) == chPISAux)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
}
