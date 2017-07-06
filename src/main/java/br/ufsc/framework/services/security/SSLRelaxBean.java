package br.ufsc.framework.services.security;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.cert.CertificateException;

public class SSLRelaxBean {

    public SSLRelaxBean() throws Exception {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @SuppressWarnings("unused")
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @SuppressWarnings("unused")
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }


            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
                // TODO Auto-generated method stub

            }


            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
                // TODO Auto-generated method stub

            }
        }};

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        SSLContext.setDefault(sc);

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

}
