package pesquisa;
import java.util.List;

public class Largura implements Algoritmo {
	public void insere(List<No> lista, No e) {
		lista.add( e);
	}
	public boolean espera() {
		return false;
	}
}
