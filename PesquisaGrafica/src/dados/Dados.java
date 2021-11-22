package dados;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import pesquisa.No;

public class Dados {
	private static ArrayList<Cidade> nos;
	private static ArrayList<Estrada> ramos;
	private static Dados dados;
	private String algoritmo = "Largura";
	private int cadencia = 1000;
	private static final String PATH = "..\\dados";

	public int getCadencia() {
		return cadencia;
	}

	public void setCadencia(int cadencia) {
		this.cadencia = cadencia;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	private Dados() {
		nos = new ArrayList<>();
		nos.add(new Cidade());
		ramos = new ArrayList<>();
		ramos.add(new Estrada());
		lerDados();
	}

	public static Dados getDados() {
		if (dados == null)
			dados = new Dados();
		return dados;
	}

	public ArrayList<Cidade> getNos() {
		return nos;
	}

	public Cidade getNo(int i) {
		return nos.get(i);
	}

	public void setNos(ArrayList<Cidade> nos) {
		Dados.nos = nos;
	}

	public void setNo(int i, Cidade no) {
		if (i > 0 && i < nos.size())
			nos.set(i, no);
	}

	public void juntaNo(Cidade no) {
		nos.add(no);
	}

	public static ArrayList<Cidade> getIniciais() {
		ArrayList<Cidade> lista = new ArrayList<>();
		lista.add(nos.get(Cidade.getNoInicial()));
		return lista;
	}

	public ArrayList<Estrada> getRamos() {
		return ramos;
	}

	public void limpaEstadoRamos() {
		for (Estrada r : ramos) {
			r.setEstado(Estrada.LIVRE);
		}
	}

	public ArrayList<Estrada> getRamosNo(No no) {
		ArrayList<Estrada> ar = new ArrayList<>();
		for (Estrada r : ramos) {
			if (r.getNoO() != null && r.getNoD() != null && r.getNoO().getNumero() == no.getEstado().getNumero() &&
					! no.passouPor( r.getNoD().getNumero()))
				ar.add(r);
		}
		return ar;
	}

	public Estrada getRamo(int i) {
		return ramos.get(i);
	}

	public void setRamos(ArrayList<Estrada> ramos) {
		Dados.ramos = ramos;
	}

	public void setRamo(int i, Estrada ramo) {
		if (i > 0 && i < ramos.size())
			ramos.set(i, ramo);
	}

	public void juntaRamo(Estrada ramo) {
		ramos.add(ramo);
	}

	public void lerDados() {
//		System.out.println("Working Directory = " +
//				 System.getProperty("user.dir"));
//		System.out.println(new File(".").getAbsolutePath());
		try {
			InputStream fis = getClass().getResourceAsStream("/resources/nos.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			String line;
			while ((line = br.readLine()) != null) {
				String[] c = line.trim().split("[\\p{Space}]+");
				if (c.length < 3)
					continue;
				Cidade n = new Cidade();
				n.setNumero(Integer.parseInt(c[0]));
				n.setX(Double.parseDouble(c[1]));
				n.setY(Double.parseDouble(c[2]));
//				n.setH(Double.parseDouble(c[3]));
				juntaNo(n);
			}
			br.close();
			fis = getClass().getResourceAsStream("/resources/ramos.txt");
			br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				String[] c = line.trim().split("[\\p{Space}]+");
				if (c.length < 3)
					continue;
				Estrada r = new Estrada();
				r.setNoO(getNo(Integer.parseInt(c[0])));
				r.setNoD(getNo(Integer.parseInt(c[1])));
//				r.setCusto(Double.parseDouble(c[2]));
				juntaRamo(r);
				r = new Estrada();
				r.setNoO(getNo(Integer.parseInt(c[1])));
				r.setNoD(getNo(Integer.parseInt(c[0])));
//				r.setCusto(Double.parseDouble(c[2]));
				juntaRamo(r);
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Done with the file
		Cidade.setNoInicial(3);
		Cidade.setNoFinal(13);
	}

	public void refresh() {
		for (Cidade n : nos) {
			n.setGraf();
		}
		for (Estrada r : ramos) {
			r.setGraf();
		}
	}

}
