package pesquisa;
import java.util.ArrayList;
import java.util.List;

public class Fronteira
{
    private ArrayList<No> lista;
    private Algoritmo alg;
    
    public Fronteira( Algoritmo alg)
    {
        this.alg = alg;
        lista = new ArrayList<No>();
    }
    
    public boolean espera() {
    	return alg.espera();
    }
    
    public void junta( List<No> le) {
        for (No e : le) {
            alg.insere( lista, e);
        }
    }
    
    public No cabeca() {
        if (lista.isEmpty()) {
            return null;
        }
        else {
            return lista.remove( 0);
        }
    }
    
    public ArrayList<No> getNos() {
    	return lista;
    }
    
    public int getContagem() {
        return lista.size();
    }
    
    public void imprime() {
        System.out.println("\n..................");
        for (No n : lista) {
            System.out.println(n);
        }
    }
    

}
