package dados;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cidade {
	private double x;
	private double y;
	private int numero;
	private int estado;
//	private double h;
	private Circle graf;
	private static int noInicial = 0;
	private static int noFinal = 0;

	public static int getNoInicial() {
		return noInicial;
	}

	public static void setNoInicial(int noInicial) {
		Cidade.noInicial = noInicial;
	}

	public static int getNoFinal() {
		return noFinal;
	}

	public static void setNoFinal(int noFinal) {
		Cidade.noFinal = noFinal;
	}

	public Cidade() {
		x = 0;
		y = 0;
		numero = 0;
		estado = 0;
		graf = new Circle(0,0,0);
		graf.setOnMouseClicked( new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	if (! event.isControlDown())
            		Cidade.setNoInicial(numero);
            	else
                    Cidade.setNoFinal(numero);
            	Dados.getDados().limpaEstadoRamos();
                Dados.getDados().refresh();
            }
        });
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		setGraf();
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		setGraf();
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
		setGraf();
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public double getH() {
		double hh = 0;
		double x1 = Dados.getDados().getNo(getNoFinal()).getX();
		double y1 = Dados.getDados().getNo(getNoFinal()).getY();
		hh = Math.sqrt((x1-getX())*(x1-getX())+(y1-getY())*(y1-getY()));
		return hh;
	}
//
//	public void setH(double h) {
//		this.h = h;
//	}

	public String getChave() {
		return ""+numero;
	}

	public int hashCode() {
		return numero;
	}
	
	public boolean equals( Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		Cidade oc = (Cidade) o;
		return oc.getEstado() == getEstado();
	}
	
	public boolean goal() {
		return numero == noFinal;
	}

	public void setGraf() {
		graf.setCenterX(x);
		graf.setCenterY(y);
		if (numero == noInicial) {
			graf.setRadius(8);
			graf.setFill(Color.BLUE);
		} else if (numero == noFinal) {
			graf.setRadius(8);
			graf.setFill(Color.GREEN);
		} else {
			graf.setRadius( 4);
			graf.setFill(Color.DARKBLUE);
		}
	}
	
	public Circle getGraf() {
		return graf;
	}

	public String toString() {
		return "No " + numero + "  x: " + x + "  y: " + y;
	}
}
