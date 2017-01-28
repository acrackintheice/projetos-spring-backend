package hello.model;
import java.io.Serializable;
/**
 * Created by DU on 27/01/2017.
 */
public class TotalCandidatos implements Serializable {

    int totalDeCandidatos;

    public TotalCandidatos(int totalDeCandidatos){
        this.totalDeCandidatos = totalDeCandidatos;
    }

    public int gettotalDeCandidatos() {
        return totalDeCandidatos;
    }

    public void settotalDeCandidatos(int totalDeCandidatos) {
        this.totalDeCandidatos = totalDeCandidatos;
    }
}
