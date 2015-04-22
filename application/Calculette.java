package application;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Calculette extends Application {

	private int premierChiffre = 0, secondChiffre = 0, resultat;
	private int nbclics = 0;
	private boolean addition; // =true pour addition , = false pour soustraction
	private String affichage;
	private TextField result = new TextField();
	private Timer timer;
	private int N =0;
	
	Task<Integer> task = new Task<Integer>() {

		@Override
		protected Integer call() throws InterruptedException {
			// TODO Auto-generated method stub
			int i = 0 ;
			while (i<1000) {
				Thread.sleep(5);
				i++;
				//System.out.println("i = "+i);
			}
			updateProgress(i,1000);
			return i;
		}
		
	};
	@Override
	public void start(Stage primaryStage) {

		BorderPane pub = new BorderPane();
		
		BackgroundImage myBI = new BackgroundImage(new Image("fond.jpg"), null, null, null, null);
		pub.setBackground(new Background(myBI));
		
		Text texte = new Text("La calculette sera affichée dans 5 secondes");                                                                                                                                                                                                                                                                                  
		pub.setBottom(texte);

		// Création de la scene, association du decors et de la scene, taille de
		// la scene
		Scene pubscene = new Scene(pub, 650, 450);
		// On place la scene dans la fenetre
		primaryStage.setScene(pubscene);
		primaryStage.setTitle("Pub");
		primaryStage.setResizable(false);
		
		BorderPane root = new BorderPane();
		GridPane grid = new GridPane();
		
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		task.setOnSucceeded((value)->{
			N = (int)value.getSource().getProgress();
			Scene scene = new Scene(root, 650, 450);
			// On place la scene dans la fenetre
			primaryStage.setScene(scene);
		});

		result = new TextField("0");
		result.setAlignment(Pos.CENTER_RIGHT);
		result.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
		// Le placer en haut du BorderPane
		root.setTop(result);

		root.setCenter(grid);
		//BackgroundImage 
		myBI = new BackgroundImage(new Image("fond2.jpg"), null, null, null, null);
		root.setBackground(new Background(myBI));

		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(25, 25, 25, 25));
		// Espacer les eléments contenus dans le GridPane
		grid.setHgap(20);
		grid.setVgap(20);
		// Largeur de l'espacement horizontal entre les cellules
		grid.setHgap(20);
		// Largeur de l'espacement vertical entre les cellules
		grid.setVgap(20);
		// Marge entre la grille et la bordure de la fen�tre
		grid.setPadding(new Insets(15, 15, 15, 15));

		Button bouton0 = new Button("0");
		bouton0.setPrefWidth(80);
		Button bouton1 = new Button("1");
		bouton1.setPrefWidth(80);
		Button bouton2 = new Button("2");
		bouton2.setPrefWidth(80);
		Button bouton3 = new Button("3");
		bouton3.setPrefWidth(80);
		Button bouton9 = new Button("9");

		bouton9.setPrefWidth(80);
		Button bouton5 = new Button("5");
		bouton5.setPrefWidth(80);
		Button bouton6 = new Button("6");
		bouton6.setPrefWidth(80);
		Button bouton7 = new Button("7");
		bouton7.setPrefWidth(80);
		Button bouton8 = new Button("8");
		bouton8.setPrefWidth(80);
		Button bouton4 = new Button("4");
		bouton4.setPrefWidth(80);
		Button plus = new Button("+");
		plus.setPrefWidth(80);
		Button egal = new Button("=");
		egal.setPrefWidth(80);
		Button moins = new Button("-");
		moins.setPrefWidth(80);

		grid.add(bouton0, 0, 0);
		grid.add(bouton1, 1, 0);
		grid.add(bouton2, 2, 0);
		grid.add(bouton3, 0, 1);
		grid.add(bouton9, 0, 3);
		grid.add(bouton5, 2, 1);
		grid.add(bouton6, 0, 2);
		grid.add(bouton7, 1, 2);
		grid.add(bouton8, 2, 2);
		grid.add(bouton4, 1, 1);
		grid.add(plus, 2, 3);
		grid.add(egal, 1, 4);
		grid.add(moins, 1, 3);

		bouton0.setOnMouseClicked(new Gestionnaire());
		bouton1.setOnMouseClicked(new Gestionnaire());
		bouton2.setOnMouseClicked(new Gestionnaire());
		bouton3.setOnMouseClicked(new Gestionnaire());
		bouton4.setOnMouseClicked(new Gestionnaire());
		bouton5.setOnMouseClicked(new Gestionnaire());
		bouton6.setOnMouseClicked(new Gestionnaire());
		bouton7.setOnMouseClicked(new Gestionnaire());
		bouton8.setOnMouseClicked(new Gestionnaire());
		bouton9.setOnMouseClicked(new Gestionnaire());

		plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				redemarrerTimer();
				if (nbclics == 1) {
					nbclics++;
					addition = true;
					affichage = affichage + "+";
					result.setText(affichage);
				} else {
					reset();
				}
			}
		});

		moins.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				redemarrerTimer();
				if (nbclics == 1) {
					nbclics++;
					addition = false;
					affichage = affichage + "-";
					result.setText(affichage);
				} else {
					reset();
				}
			}
		});

		egal.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				redemarrerTimer();
				if (nbclics == 3) {
					if (addition == true) {
						resultat = premierChiffre + secondChiffre;
					} else {
						resultat = premierChiffre - secondChiffre;
					}

					if (resultat > 10) {
						reset();
					} else {
						System.out.println("Résultat = " + resultat);
						String r = Integer.toString(resultat);
						affichage = affichage + "=" + r;
						result.setText(affichage);
					}
					nbclics = 0;
				} else {
					reset();
				}
			}
		});
  
		// Création de la scene, association du decors et de la scene, taille de
		// la scene
		//Scene scene = new Scene(c, 650, 600);
		// On place la scene dans la fenetre
		primaryStage.setScene(pubscene);
		primaryStage.setTitle("Une calculatrice simplifiée");
		primaryStage.setResizable(false);
		// On affiche la fenetre
		primaryStage.show();
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.exit(0);
			}
		}, 60*1000);
	}



	private void reset() {
		nbclics = 0;
		result.setText("Coucou tu peux recommencer");
	}

	public void redemarrerTimer() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.exit(0);
			}
		}, 60 * 1000);
	}

	class Gestionnaire implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			redemarrerTimer();
			Button boutonclique = (Button) event.getSource();
			String texteBouton = boutonclique.getText();
			System.out.println("Vous avez cliqué :" + texteBouton);
			int chiffreBouton = Integer.parseInt(texteBouton);

			if (nbclics == 0) {
				premierChiffre = chiffreBouton;
				nbclics++;
				System.out.println("premier chiffre = " + premierChiffre);
				affichage = texteBouton;
				result.setText(affichage);
				System.out.println("Bouton =" + chiffreBouton);
			} else if (nbclics == 2) {
				secondChiffre = chiffreBouton;
				nbclics++;
				System.out.println("second chiffre = " + secondChiffre);
				affichage = affichage + texteBouton;
				result.setText(affichage);
				System.out.println("Bouton =" + chiffreBouton);
			} else {
				reset();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
