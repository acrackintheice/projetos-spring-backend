package br.ufsc.framework.services.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Passwords {

    private Passwords() {

    }

    public static String hashPassword(String password) {
        return DigestUtils.shaHex(password);
    }

}
