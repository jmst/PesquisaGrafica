package pesquisa;

import java.util.ArrayList;
import java.util.Calendar;

import dados.Cidade;
import dados.Dados;
import dados.Estrada;

public class Pesquisa {
	private Fronteira f;
	private ArrayList<No> lno;
	private int contaNos;
	private boolean fim;
	private No n;

	public Pesquisa(ArrayList<Cidade> i, Algoritmo estrategia) {
		lno = new ArrayList<No>();
		for (Cidade c : i) {
			Estrada e = new Estrada();
			e.setNoO(null);
			e.setNoD(c);
			lno.add(new No(c, null, e, 0));
		}
		f = new Fronteira(estrategia);
		f.junta(lno);
		contaNos = 0;
		fim = false;
	}

	public No getN() {
		return n;
	}
	
	public void next() {
		n = f.cabeca();
		if ( n != null && !fim) {
			ArrayList<Estrada> le = Dados.getDados().getRamos();

			for (Estrada e : le) {
				e.setEstado(Estrada.LIVRE);
			}
			escreveRamosFronteira();
			escrevePais( n);
			System.out.println("Ciclo: "+n);
			if (n.getEstado().getNumero() == Cidade.getNoFinal()) {
				System.out.println("Fim: "+n);
				fim = true;
				return;
			}
			
			ArrayList<Estrada> suc = n.getSuc();
			ArrayList<No> nos = new ArrayList<>();
			for (Estrada e : suc) {
				if (!f.espera() && (e.getNoD().getNumero() == Cidade.getNoFinal())) {
					System.out.println("Fim: "+e);
					e.setEstado(Estrada.USADO);
					fim = true;
					return;
				}
				
				nos.add( new No(e.getNoD(), n, e, e.getCusto()));
			}
			f.junta(nos);
			contaNos++;
//			if (contaNos % 10000 == 0) {
//				System.out.println(n);
//				System.out.println("        nos expandidos: " + contaNos + "    fronteira: " + f.getContagem());
//			}
		} else {
			fim = true;
		}
	}
	
	private void escrevePais( No n) {
		ArrayList<Estrada> le = n.getPath();
		for (Estrada e : le) {
			e.setEstado(Estrada.USADO);
		}
	}
	
	private void escreveRamosFronteira() {
		for (No n : f.getNos()) {
			ArrayList<Estrada> le = n.getPath();
			for (Estrada e : le) {
				e.setEstado(Estrada.FRONTEIRA);
			}
		}
	}
	
	public boolean getFim() {
		return fim;
	}
	
	public boolean resolve() {
		boolean res;
		//
		System.out.println("#########################################################");
		No n = f.cabeca();
		while (n != null && !n.getEstado().goal()) {
			Dados.getDados().limpaEstadoRamos();
			ArrayList<Estrada> le = n.getPath();
			for (Estrada e : le) {
				e.setEstado(Estrada.LIVRE);
			}
			ArrayList<Estrada> suc = n.getSuc();
			ArrayList<No> nos = new ArrayList<>();
			for (Estrada e : suc) {
				nos.add( new No(e.getNoD(), n, e, 0));
			}
			f.junta(nos);
			// f.imprime();
			n = f.cabeca();
			contaNos++;
//			if (contaNos % 10000 == 0) {
//				System.out.println(n);
//				System.out.println("        nos expandidos: " + contaNos + "    fronteira: " + f.getContagem());
//			}
		}
		System.out.println("===========================");
		if (n != null) {
			ArrayList<Estrada> le = n.getPath();
			for (Estrada e : le) {
				e.setEstado(Estrada.USADO);
			}
			n.escrevePais();
			res = true;
		} else {
			System.out.println("Sem solução");
			res = false;
		}
		System.out.println("        nos expandidos: " + contaNos + "    fronteira: " + f.getContagem());
		System.out.println("~~~~~~~~ FIM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return res;
	}

	public void executa() {

		Dados.getDados().refresh();
		Calendar c = Calendar.getInstance();
		long t = c.getTimeInMillis();
		boolean res = resolve();
		c = Calendar.getInstance();
		System.out.println("Demorou: " + (c.getTimeInMillis() - t) + " ms");
	}
}
