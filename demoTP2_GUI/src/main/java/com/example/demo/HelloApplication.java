package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/*
 * Dans cette classe nous definissons les éléments graphiques de notre
 * application.
 *
 * NB: voir aussi lignes 64-74!
 */
public class HelloApplication extends Application {

    private static Controleur controleur;

    /**
     * Ce methode demarre le clienmt GUI.
     *
     * tout d'abord, il cree un BorderPane qui sera la base de linterface graphique.
     * Après, il cree un VBox pour les éléments qui seront placés à gauche et un VBox pour les
     * éléments qui seront placés à droite, ainsi que plusieurs HBox pour les éléments que seront placés
     * un a cote de l'outre, par exemple un label a coté d'un input.
     *
     * ensuite, il crée tous les éléments qui sont nécessaires pour permettre l'interaction
     * entre l'utilisateur et le serveur (buttons, table, labels...).
     *
     * Finalement, il met tous les éléments à sa place dans la distribution définie par les VBox et les HBox,
     * et il montre linterface graphique.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        try {
            BorderPane root = new BorderPane();

            VBox verticalGauche = new VBox();
            VBox verticalDroite = new VBox();
            HBox horizontal = new HBox();
            HBox prenomhbox = new HBox();
            HBox nomhbox = new HBox();
            HBox emailhbox = new HBox();
            HBox matriculehbox = new HBox();
            matriculehbox.setPadding(new Insets(0, 0, 20, 0));
            HBox session = new HBox();

            session.setAlignment(Pos.CENTER);
            session.setPadding(new Insets(20, 0, 20, 0));

            verticalGauche.setMinWidth(280);
            verticalDroite.setMinWidth(280);

            verticalGauche.setAlignment(Pos.CENTER);
            verticalDroite.setAlignment(Pos.TOP_CENTER);

            Scene scene = new Scene(root, 600, 300);

            Label liste = new Label("Liste de cours");
            liste.setPadding(new Insets(20, 0, 20, 0));
            Label inscription = new Label("Formulaire d'inscription");
            inscription.setPadding(new Insets(20, 0, 20, 0));

            Button charger = new Button("charger");
            Button envoyer = new Button("envoyer");

            TableView table = new TableView<Cour>();
            table.setEditable(true);

            TableColumn codeCours = new TableColumn("Code");
            TableColumn nameCours = new TableColumn("Cours");
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            codeCours.setCellValueFactory(new PropertyValueFactory<Cour, String>("Code"));
            nameCours.setCellValueFactory(new PropertyValueFactory<Cour, String>("Name"));

            table.getColumns().addAll(codeCours, nameCours);

            Label prenomLabel = new Label("Prenom:");
            prenomLabel.setMinWidth(80);
            TextField prenomInput = new TextField ();
            Label nomLabel = new Label("Nom:");
            nomLabel.setMinWidth(80);
            TextField nomInput = new TextField ();
            Label emailLabel = new Label("E-mail:");
            emailLabel.setMinWidth(80);
            TextField emailInput = new TextField ();
            Label matriculeLabel = new Label("Matricule");
            matriculeLabel.setMinWidth(80);
            TextField matriculeInput = new TextField ();

            final ComboBox sessionBox = new ComboBox();
            sessionBox.getItems().addAll("Automne","Hiver","Ete");
            sessionBox.getSelectionModel().select(0);

            sessionBox.setMaxWidth(150);

            verticalGauche.getChildren().add(liste);
            verticalGauche.getChildren().add(table);
            verticalGauche.getChildren().add(session);
            session.getChildren().addAll(sessionBox,charger);

            verticalDroite.getChildren().add(inscription);
            verticalDroite.getChildren().add(prenomhbox);
            prenomhbox.getChildren().addAll(prenomLabel,prenomInput);
            verticalDroite.getChildren().add(nomhbox);
            nomhbox.getChildren().addAll(nomLabel,nomInput);
            verticalDroite.getChildren().add(emailhbox);
            emailhbox.getChildren().addAll(emailLabel,emailInput);
            verticalDroite.getChildren().add(matriculehbox);
            matriculehbox.getChildren().addAll(matriculeLabel,matriculeInput);
            verticalDroite.getChildren().add(envoyer);

            root.setLeft(verticalGauche);
            root.setRight(verticalDroite);
            root.setTop(horizontal);

            /**
             * Ce methode fait un appel au methode charger de la classe Controleur.
             */
            charger.setOnAction((action) -> {
                controleur.charger();
            });

            /**
             * Ce methode fait un appel au methode envoyer de la classe Controleur.
             */
            envoyer.setOnAction((action) -> {
                controleur.envoyer();
            });

            /**
             * Ce methode fait un appel au methode sessionBox de la classe Controleur.
             */
            sessionBox.setOnAction((action) -> {
                controleur.sessionBox();
            });

            /*
             * En raison de la conception des applications JavaFX, nous sommes obligés de
             * créer le modèle et le controleur ici.
             *
             * Notez cependant que nous passons l'instance du modèle directement dans le
             * constructeur du controleur; nous n'y avons pas d'autre accès.
             *
             * Pour faciliter les choses, ici le constructeur ne prend pas la Vue entière,
             * mais juste le sous-ensemble de la Vue (l'objet Text) qu'il doit manipuler.
             */
            controleur = new Controleur(new Modele(), table,
                                        prenomInput, nomInput, emailInput, matriculeInput,
                                        sessionBox);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ce methode fait un appel au methode qui demarre l'interface graphique.
     * @param args  arguments passes par la ligne de commandes lors de l'execution du methode
     */
    public static void main(String[] args) {
        launch(args);
    }
}