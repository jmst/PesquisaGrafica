package dados;


import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Estrada {
	private Cidade noO;
	private Cidade noD;
//	private double custo;
	private int estado;
	public static final int LIVRE = 0;
	public static final int USADO = 1;
	public static final int FRONTEIRA = 2;
	private Line graf;

	public Estrada() {
		noO = null;
		noD = null;
		estado = LIVRE;
		graf = new Line(0,0,0,0);
	}
	
	public Cidade getNoO() {
		return noO;
	}
	public void setNoO(Cidade noO) {
		this.noO = noO;
		setGraf();
	}
	public Cidade getNoD() {
		return noD;
	}
	public void setNoD(Cidade noD) {
		this.noD = noD;
		setGraf();
	}
	public double getCusto() {
		double x1 = noO.getX();
		double y1 = noO.getY();
		double x2 = noD.getX();
		double y2 = noD.getY();
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
//	public void setCusto(double custo) {
//		this.custo = custo;
//	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
		setGraf();
	}

	public Line getGraf() {
		return graf;
	}

	public void setGraf() {
		if (noO != null && noD != null) {
			graf.setStartX(noO.getX());
			graf.setStartY(noO.getY());
			graf.setEndX(noD.getX());
			graf.setEndY(noD.getY());
			if (estado == LIVRE) {
				graf.setStrokeWidth(1);
				graf.setStroke( Color.GRAY);
			} else if (estado == FRONTEIRA){
				graf.setStrokeWidth(3);
				graf.setStroke( Color.BLUE);
			} else {
				graf.setStrokeWidth(3);
				graf.setStroke( Color.RED);
			}
		}
	}
	
	public boolean pertence( ArrayList<Estrada> p) {
		for (Estrada e : p) {
			if (e.getNoO() == noO || e.getNoD() == noO || e.getNoD() == noO || e.getNoD() == noD)
				return true;
		}
		return false;
	}
	
	public String toString() {
		return "Nos ("+noO+") ("+noD+")  custo: "+getCusto();
	}
	
}
