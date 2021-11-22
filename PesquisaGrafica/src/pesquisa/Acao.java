package pesquisa;

import dados.Cidade;
import dados.Estrada;

public class Acao {
	private Cidade estado;
	private Estrada estrada;
	private double custo;
	
	public Acao( Estrada e, double custo) {
		this.estado = e.getNoD();
		this.estrada = e;
		this.custo = custo;
	}
	
	public Cidade getEstado() {
		return estado;
	}
	
	public double getCusto() {
		return custo;
	}
}
