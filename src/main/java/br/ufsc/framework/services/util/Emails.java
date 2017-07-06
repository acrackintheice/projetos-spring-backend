package br.ufsc.framework.services.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Emails {

    private Emails() {

    }


    public static boolean validarEmail(String email) {
        if (email == null)
            return false;

        // Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        // Match the given string with the pattern
        Matcher m = p.matcher(email.toLowerCase());

        // check whether match is found
        boolean matchFound = m.matches();

        if (matchFound)
            return true;
        else
            return false;

    }


    public static String escondeCaracteresEmail(String email, Integer qtdeVisivel) {

        String emailMontado = null;
        String[] separados = email.split("@");

        if (separados.length == 2) {
            if (separados[0].length() >= qtdeVisivel) {
                String primeirasLetras = separados[0].substring(0, qtdeVisivel);
                emailMontado = separados[0].replaceAll(".", "*");
                emailMontado = emailMontado.replaceFirst("\\*{" + qtdeVisivel + "}", primeirasLetras);
            } else {
                return email;
            }

            emailMontado += "@" + separados[1];
            return emailMontado;
        }

        return null;

    }
}
