package pesquisa;

import java.util.HashSet;
import java.util.List;

import dados.Cidade;


public class AStarMelhorado implements Algoritmo {

//    private HashMap<String,Cidade> estados = new HashMap<String,Cidade>();
    private HashSet<Cidade> estados = new HashSet<Cidade>();
    @Override
    public void insere(List<No> lista, No no) {
//        if (!estados.containsKey(no.getEstado().getChave())) {
//            estados.put(no.getEstado().getChave(), no.getEstado());
        if (!estados.contains(no.getEstado())) {
            estados.add(no.getEstado());
            for (int i=0; i< lista.size(); i++) {
                No n = lista.get(i);
                if (n.f() >= no.f()) {
                    lista.add( i, no);
                    return;
                }
            }
            lista.add( no);
        }
    }
	public boolean espera() {
		return true;
	}
}
