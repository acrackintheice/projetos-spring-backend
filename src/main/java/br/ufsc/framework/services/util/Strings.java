package br.ufsc.framework.services.util;

import org.apache.commons.lang.WordUtils;
import org.apache.lucene.analysis.ASCIIFoldingFilter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Strings {

    private Strings() {

    }

    public static boolean isBlankOrNull(String s) {
        return s == null || s.trim().equals("");
    }

    public static String randomString(int lo, int hi) {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++)
            b[i] = (byte) rand('a', 'z');
        return new String(b);
    }

    public static String randomNumber(int lo, int hi) {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++)
            b[i] = (byte) rand('0', '9');
        return new String(b);
    }

    private static int rand(int lo, int hi) {
        java.util.Random rn = new java.util.Random();
        int n = hi - lo + 1;
        int i = rn.nextInt() % n;
        if (i < 0)
            i = -i;
        return lo + i;
    }

    public static boolean findInsideString(String originalString, String textToFind) {

        if (textToFind == null || textToFind.trim().length() == 0)
            return true;

        Pattern p = Pattern.compile("[,\\s]+");

        boolean result = true;

        for (String s : p.split(textToFind)) {
            result = originalString.toLowerCase().contains(s.toLowerCase()) && result;
        }

        return result;

    }

    static String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
    static String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
    static char[] tabela;

    static Set<Character> chars = new LinkedHashSet<>();

    static Set<Character> acentos = new LinkedHashSet<>();

    static {
        tabela = new char[256];
        for (int i = 0; i < tabela.length; ++i) {
            tabela[i] = (char) i;
        }
        for (int i = 0; i < acentuado.length(); ++i) {
            chars.add(acentuado.charAt(i));
            acentos.add(acentuado.charAt(i));
            tabela[acentuado.charAt(i)] = semAcento.charAt(i);
        }

        for (int i = 'A'; i <= 'Z'; i++)
            chars.add((char) i);

        for (int i = 'a'; i <= 'z'; i++)
            chars.add((char) i);

        chars.add('\'');
        chars.add(' ');

    }

    public static boolean possuiAcentos(String s) {
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (acentos.contains(ch))
                return true;
        }
        return false;
    }

    public static String onlyAlphabeticAndAccents(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (chars.contains(ch))
                sb.append(ch);
        }
        return sb.toString();
    }

    public static String trim(String str) {
        if (str == null)
            return null;

        return str.replace(String.valueOf((char) 160), " ").trim();
    }

    public static String concatenateByDelimiter(String delimiter, Object... values) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            String value = (values[i] != null) ? values[i].toString().replaceAll(delimiter, "").trim() : null;
            sb.append(value);
            if (i < values.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
    }

    public static String remover(final String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch < 256) {
                sb.append(tabela[ch]);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static String removeAccents(String str) {

        char[] output = new char[str.length()];

        ASCIIFoldingFilter.foldToASCII(str.toCharArray(), 0, output, 0, str.length());

        return new String(output);

    }

    public static String getBlankIfNull(String s) {
        if (s != null)
            return s;
        else
            return "";
    }

    public static String limparCPF(String cpf) {
        if (cpf != null)
            return cpf.trim().replaceAll(" ", "").replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "");
        return null;
    }

    public static String formatar(String valor, String mascara) {

        if (valor == null)
            return null;

        String dado = "";
        // remove caracteres nao numericos
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (Character.isDigit(c)) {
                dado += c;
            }
        }

        int indMascara = mascara.length();
        int indCampo = dado.length();

        for (; indCampo >= 0 && indMascara > 0; ) {
            if (mascara.charAt(--indMascara) == '#') {
                indCampo--;
            }
        }

        String saida = "";
        for (; indMascara < mascara.length(); indMascara++) {
            saida += ((mascara.charAt(indMascara) == '#') ? dado.charAt(indCampo++) : mascara.charAt(indMascara));
        }
        return saida;
    }

    public static String padronizaNome(String nome) {
        String nomePadronizado = WordUtils.capitalizeFully(nome.toLowerCase());
        nomePadronizado = Strings.formatarParticulasNome(nomePadronizado);
        return nomePadronizado;
    }

    public static boolean isParticulaPreposicao(String particula) {
        return particula != null && //
            (//
                particula.equalsIgnoreCase("de") || //
                    particula.equalsIgnoreCase("e") || //
                    particula.equalsIgnoreCase("dos") || //
                    particula.equalsIgnoreCase("da") || //
                    particula.equalsIgnoreCase("do") || //
                    particula.equalsIgnoreCase("das") || //
                    particula.equalsIgnoreCase("di"));

    }

    public static String formatarParticulasNome(String nome) {
        if (nome.contains(" De ")) {
            nome = nome.replaceAll(" De ", " de ");
        }
        if (nome.contains(" E ")) {
            nome = nome.replaceAll(" E ", " e ");
        }
        if (nome.contains(" Dos ")) {
            nome = nome.replaceAll(" Dos ", " dos ");
        }
        if (nome.contains(" Da ")) {
            nome = nome.replaceAll(" Da ", " da ");
        }
        if (nome.contains(" Do ")) {
            nome = nome.replaceAll(" Do ", " do ");
        }
        if (nome.contains(" Das ")) {
            nome = nome.replaceAll(" Das ", " das ");
        }
        if (nome.contains(" Di ")) {
            nome = nome.replaceAll(" Di ", " di ");
        }
        return nome;
    }

}
