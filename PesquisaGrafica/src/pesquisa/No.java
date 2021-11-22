package pesquisa;
import java.util.ArrayList;

import dados.Cidade;
import dados.Dados;
import dados.Estrada;

public class No
{
    private int profundidade;
    private Cidade estado;
    private double g;
    private double f;
    private No pai;
    private ArrayList<Estrada> path;
    
    public No( Cidade estado, No pai, Estrada e, double custo)
    {
        this.estado = estado;
        this.pai = pai;
        if (pai == null) {
            g = custo;
            profundidade = 1;
        	f = g() + h();
        	path = new ArrayList<Estrada>();
        }
        else {
            g = pai.g() + custo;
            profundidade = pai.getProfundidade() + 1;
        	f = g() + h();
            if (pai.f() > f)
            	f = pai.f();
            path = copiaPath(pai.getPath());
            path.add(e);
        }
    }
    
    private ArrayList<Estrada> copiaPath( ArrayList<Estrada> p) {
    	ArrayList<Estrada> n = new ArrayList<>();
    	for (Estrada e : p)
    		n.add( e);
    	return n;
    }

    public ArrayList<Estrada> getPath() {
    	return path;
    }
    
    public int getProfundidade() {
        return profundidade;
    }
    
    public double g() {
        return g;
    }
    
    public double h() {
        return estado.getH();
    }
    
    public double f() {
        return f;
    }
    
    public No getPai() {
        return pai;
    }
    
    public Cidade getEstado() {
        return estado;
    }
    
    public ArrayList<Estrada> getSuc() {
        ArrayList<Estrada> s = Dados.getDados().getRamosNo(this);
        return s;
    }
    
    public boolean passouPor( int numero) {
    	for (Estrada e : path) {
    		if ( e.getNoO().getNumero() == numero || e.getNoD().getNumero() == numero)
    			return true;
    	}
    	return false;
    }
    
    public String toString() {
        return "" + getEstado()+"   g= "+g()+"   h= "+h()+"   f= "+f()+"   prof= "+getProfundidade();
    }

    public void escrevePais() {
        if (pai != null)
            pai.escrevePais();
        System.out.println( this);
    }
}
