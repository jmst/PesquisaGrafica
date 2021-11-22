package application;

import java.util.ArrayList;

import dados.Cidade;
import dados.Dados;
import dados.Estrada;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pesquisa.AStar;
import pesquisa.AStarMelhorado;
import pesquisa.Algoritmo;
import pesquisa.CustoUniforme;
import pesquisa.Largura;
import pesquisa.Pesquisa;
import pesquisa.Profundidade;
import pesquisa.Sofrega;

public class PesquisaGraficaRomenia extends Application {
	Dados dados;
	Group c = new Group();
//	MyEvent meuEvento = new MyEvent();

	@Override
	public void start(Stage primaryStage) {
		dados = Dados.getDados();
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 800, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GridPane opcoes = new GridPane();
	        opcoes.setVgap(4);
	        opcoes.setHgap(10);
	        opcoes.setPadding(new Insets(5, 5, 5, 5));
			opcoes.add(new Label("Algoritmo: "), 0, 0);
	        ObservableList<String> algs = 
				    FXCollections.observableArrayList(
				        "Profundidade",
				        "Sôfrega",
				        "Largura",
				        "Custo Uniforme",
				        "A*",
				        "A* grafo"
				    );
			final ComboBox<String> algoritmo = new ComboBox<>(algs);
			opcoes.add( algoritmo, 1, 0, 2, 1);
			algoritmo.valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String t, String t1) {                
	                Dados.getDados().setAlgoritmo(t1);                
	            }    
	        });
	        
			opcoes.add(new Label("Cadência: "), 3, 0);
			ObservableList<String> tempos = 
				    FXCollections.observableArrayList(
				        "100 ms",
				        "200 ms",
				        "500 ms",
				        "1000 ms",
				        "2000 ms"
				    );
			final ComboBox<String> tempo = new ComboBox<>(tempos);
			opcoes.add(tempo, 4, 0, 2, 1);
			tempo.valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String t, String t1) {                
	                Dados.getDados().setCadencia(Integer.parseInt(t1.split(" ")[0]));                
	            }    
	        });

			Button inicia = new Button("Iniciar");
			inicia.setOnAction((ActionEvent e) -> {
				Thread th = new Thread(new Executa());
				th.setDaemon(true);
				th.start();
			});
			opcoes.add( inicia,  6, 0);
			root.setTop(opcoes);
			Node c = criaGrafico();
			root.setCenter(c);
			Dados.getDados().refresh();
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Node criaGrafico() {
		ArrayList<Estrada> ramos = dados.getRamos();
		for (Estrada r : ramos) {
			if (r.getNoO() == null)
				continue;
			c.getChildren().add(r.getGraf());

		}
		ArrayList<Cidade> nos = dados.getNos();
		for (Cidade n : nos) {
			if (n.getNumero() == 0)
				continue;
			c.getChildren().add(n.getGraf());
		}

		// -------------------------------------

		c.addEventHandler(MyEvent.CICLO_TERMINADO, new EventHandler<MyEvent>() {
			@Override
			public void handle(MyEvent event) {

				if (event.getEventType().equals(MyEvent.CICLO_TERMINADO)) {
					Platform.runLater(new Runnable() {
						public void run() {
							Dados.getDados().refresh();
						}
					});
				}
			}
		});

		// -------------------------------------------------------

		return c;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private class Executa extends Task<Object> {
		@Override
		public Object call() {
			Algoritmo aa = null;
			switch (Dados.getDados().getAlgoritmo()) {
			case "Largura":
				aa = new Largura();
				break;
			case "Profundidade":
				aa = new Profundidade();
				break;
			case "Custo Uniforme":
				aa = new CustoUniforme();
				break;
			case "A*":
//				Cidade.setNoFinal(13);
				aa = new AStar();
				break;
			case "A* grafo":
//				Cidade.setNoFinal(13);
				aa = new AStarMelhorado();
				break;
			case "Sôfrega":
//				Cidade.setNoFinal(13);
				aa = new Sofrega();
				break;
			default:
			}
			Pesquisa p = new Pesquisa(Dados.getIniciais(), aa);
			while (!p.getFim()) {
				c.fireEvent(new MyEvent());
				try {
					Thread.sleep(Dados.getDados().getCadencia());
				} catch (Exception ex) {
				}
				Platform.runLater(new Runnable() {
					public void run() {
						p.next();
					}
				});
			}
			c.fireEvent(new MyEvent());
			return null;
		}
	}
}
