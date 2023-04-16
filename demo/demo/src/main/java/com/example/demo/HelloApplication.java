package com.example.demo;

import javafx.application.Application;
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
            HBox session = new HBox();

            verticalGauche.setMinWidth(280);
            verticalDroite.setMinWidth(280);

            verticalGauche.setAlignment(Pos.CENTER);
            verticalDroite.setAlignment(Pos.TOP_CENTER);

            Scene scene = new Scene(root, 600, 300);

            Text textValeur = new Text("Appuyer sur un bouton");

            Button inc = new Button("+1");
            Button dub = new Button("*2");
            Button div = new Button("/2");
            Button dec = new Button("-1");

            TableView table = new TableView<Cour>();
            table.setEditable(true);

            TableColumn codeCours = new TableColumn("Code");
            TableColumn nameCours = new TableColumn("Cours");
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            codeCours.setCellValueFactory(new PropertyValueFactory<Cour, String>("Code"));
            nameCours.setCellValueFactory(new PropertyValueFactory<Cour, String>("Name"));

            table.getColumns().addAll(codeCours, nameCours);

            Label prenomLabel = new Label("Prénom :");
            TextField prenomInput = new TextField ();
            Label nomLabel = new Label("Nom :");
            TextField nomInput = new TextField ();
            Label emailLabel = new Label("Email :");
            TextField emailInput = new TextField ();
            Label matriculeLabel = new Label("Matricule :");
            TextField matriculeInput = new TextField ();

            final ComboBox sessionBox = new ComboBox();
            sessionBox.getItems().addAll("Automne","Hiver","Ete");
            sessionBox.getSelectionModel().select(0);

            sessionBox.setMaxWidth(150);

            //prenomInput.setMaxWidth(100);

            verticalGauche.getChildren().add(table);
            verticalGauche.getChildren().add(session);
            session.getChildren().addAll(sessionBox,inc);

            verticalDroite.getChildren().add(prenomhbox);
            prenomhbox.getChildren().addAll(prenomLabel,prenomInput);
            verticalDroite.getChildren().add(nomhbox);
            nomhbox.getChildren().addAll(nomLabel,nomInput);
            verticalDroite.getChildren().add(emailhbox);
            emailhbox.getChildren().addAll(emailLabel,emailInput);
            verticalDroite.getChildren().add(matriculehbox);
            matriculehbox.getChildren().addAll(matriculeLabel,matriculeInput);
            verticalDroite.getChildren().add(dec);

            horizontal.getChildren().addAll(dub,div);
            root.setLeft(verticalGauche);
            root.setRight(verticalDroite);
            root.setTop(horizontal);
            root.setBottom(textValeur);

            //verticalGauche.getChildren().add();
            //verticalDroite.getChildren().add();
            // Le controleur manipule tout evenement.

            inc.setOnAction((action) -> {
                controleur.inc();
            });

            dec.setOnAction((action) -> {
                controleur.dec();
            });

            dub.setOnAction((action) -> {
                controleur.dub();
            });

            div.setOnAction((action) -> {
                controleur.div();
            });

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

    public static void main(String[] args) {
        launch(args);
    }
}