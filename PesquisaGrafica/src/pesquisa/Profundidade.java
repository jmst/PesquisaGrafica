package pesquisa;
import java.util.List;

public class Profundidade implements Algoritmo {
	public void insere(List<No> lista, No e) {
		lista.add( 0, e);
	}
	public boolean espera() {
		return false;
	}
}
