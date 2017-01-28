package hello.util;

import hello.model.Candidato;

import java.util.Comparator;

/**
 * Created by DU on 28/01/2017.
 */
public class CandidatoComparator implements Comparator<Candidato> {

        private final String campo;

        public CandidatoComparator (String campo) {
            this.campo = campo;
        }

        public int compare(Candidato e1, Candidato e2) {
            return (campo.equals("cpf"))            ? e1.getCpf().compareTo(e2.getCpf())  :
                   (campo.equals("acertos_total"))  ? e1.getAcertos_total().compareTo(e2.getAcertos_total())
                                                    : e1.getNome().compareTo(e2.getNome());
        }


}
