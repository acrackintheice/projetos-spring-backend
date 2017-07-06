package br.ufsc.framework.services.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Compression {

    private Compression() {
    }

    public static byte[] zipBytes(byte[] input, String nomeArquivo) throws Exception {

        int count = 0;
        byte dados[] = new byte[512];
        ByteArrayInputStream entrada = new ByteArrayInputStream(input);
        ByteArrayOutputStream saida = new ByteArrayOutputStream();

        ZipOutputStream zout = new ZipOutputStream(saida);
        zout.setLevel(Deflater.BEST_COMPRESSION);
        ZipEntry entry = new ZipEntry(nomeArquivo);

        try {
            zout.putNextEntry(entry);
            while ((count = entrada.read(dados, 0, dados.length)) != -1) {
                zout.write(dados, 0, count);
            }
        } finally {
            if (zout != null) {
                zout.closeEntry();
                zout.close();
            }
        }

        return saida.toByteArray();
    }

    public static Map<String, byte[]> unzip(byte[] zipInput) throws Exception {
        return unzip(new ByteArrayInputStream(zipInput));
    }

    public static Map<String, byte[]> unzip(InputStream in) throws Exception {

        Map<String, byte[]> result = new HashMap<>();

        ZipInputStream zin = new ZipInputStream(in);

        ZipEntry e;
        ByteArrayOutputStream unzipedOutput = null;

        while ((e = zin.getNextEntry()) != null) {

            unzipedOutput = new ByteArrayOutputStream();
            byte[] b = new byte[512];
            int len = 0;
            while ((len = zin.read(b)) != -1) {
                unzipedOutput.write(b, 0, len);
            }

            result.put(e.getName(), unzipedOutput.toByteArray());
        }

        zin.close();

        return result;

    }

}
