package com.example.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.*;

/**
 * La classe Controleur représente le pont entre le Vue et le Modele dans le modèle MVC.
 * Cela signifie que lorsque l'utilisateur interagit avec Vue, la classe Controleur décide
 * quelles actions la classe Modele doit effectuer.
 */
public class Controleur {

	private Modele modele;
	private TableView vue;
	private TextField prenomInput;
	private TextField nomInput;
	private TextField emailInput;
	private TextField matriculeInput;

	private ComboBox session;


	public Controleur(Modele m, TableView v,
					  TextField prenomInput, TextField nomInput, TextField emailInput, TextField matriculeInput,
					  ComboBox session) {
		this.modele = m;
		this.vue = v;
		this.prenomInput = prenomInput;
		this.nomInput = nomInput;
		this.emailInput = emailInput;
		this.matriculeInput = matriculeInput;
		this.session = session;
	}

	/**
	 * Ce méthode fait un appel au methode charger de la clase Modele, ensuite fait un appel au methode updateText.
	 */
	public void charger() {

		this.modele.charger();
		this.updateText();

	}

	/**
	 * Ce méthode fait un validation des données inseres par l'utilisateur et ensuite, il fait
	 * un appel au methode inscrire de la classe Modele en metant in instance de la classe
	 * RegistrationForme en parametre.
	 *
	 * Tout d'abord, il confirme que l'utilisateur a sélectionné un cours pour procéder à l'inscription.
	 * Ensuite, il vérifie que tous les champs sont remplis. Ensuite, il vérifie que l'adresse courriel soit
	 * valide (de la forme xxx@umontreal.ca). Ensuite, il vérifie que la matricule soit valide (un numéro à 6 chiffres).
	 *
	 * Si tout est correct, il crée une instance de la classe RegistrationForme qui contient toutes
	 * les informations nécessaires pour effectuer l'inscription.
	 *
	 * Finalement, il fair un appel au methode inscrire de la classe Modele, en metant la forme
	 * d'inscription en parametre.
	 *
	 * Dans le cas ou un valeur ne soit pas valide, il fait un appel au methode showAlert, en metant
	 * en parametre le String correspondant.
	 *
	 */
	public void envoyer() {
		try {
			Cour myCour = (Cour) vue.getSelectionModel().getSelectedItem();
			Course myCourse = new Course(myCour.getName(), myCour.getCode(), session.getValue().toString());
			// Si un champ est vide montre erreur
			if (prenomInput.getText().equals("")||nomInput.getText().equals("")
					||emailInput.getText().equals("")||matriculeInput.getText().equals("")){
				String[] alert = {"Champs vides", "Tous les champs sont requis"};
				showAlert(alert);
			} else {
				try {
					// verification de emial de la forme xxx@umontreal.ca
					if ((emailInput.getText().split("@",2)[1].equals("umontreal.ca"))){
						// verification de la taille de la matricule
						if (matriculeInput.getText().length() == 6){
							try {
								Integer.parseInt(matriculeInput.getText());
								RegistrationForm Registration = new RegistrationForm(prenomInput.getText(),
																					nomInput.getText(),
																					emailInput.getText(),
																					matriculeInput.getText(),
																					myCourse);
								this.modele.inscrire(Registration);
								switch (modele.response){
									case "Succes":
										String[] succes = {"Succes", "Félicitations! Inscription réussie"};
										showAlert(succes);
										break;

									case "Error":
										String[] error = {"Erreur de serveur", "Un erreur est survenu, veuillez réessayer plus tard"};
										showAlert(error);
										break;
								}
							} catch (NumberFormatException ex){
								String[] alert = {"Matricule invalide", "Veuillez indiquer un matricule valide"};
								showAlert(alert);
							}
						} else {
							String[] alert = {"Matricule invalide", "Veuillez indiquer un matricule valide"};
							showAlert(alert);
						}
					} else {
						String[] alert = {"E-mail invalide", "Veuillez indiquer un email valide"};
						showAlert(alert);
					}
				} catch (Exception ex) {
					String[] alert = {"E-mail invalide", "Veuillez indiquer un email valide"};
					showAlert(alert);
				}

			}
		} catch (Exception ex){
			String[] alert = {"Cours manquant", "Veuillez indiquer un cours auquel s'inscrire"};
			showAlert(alert);

		}

	}

	/**
	 * Ce methode fait un appel au methode changerSession de la classe Modele en metant le valeur
	 * obtenu du ComboBox de la Vue.
	 */
	public void sessionBox() {
		this.modele.changerSession(session.getValue().toString());
		//this.updateText();
	}

	/**
	 * Cette méthode permet de mettre à jour les informations affichées dans le tableau de la Vue.
	 *
	 * Tout d'abord, il supprime les données de la table. Il entre alors dans une boucle for
	 * qui lui permet de remplir une ligne pour chaque cours contenu dans la liste des cours.
	 *
	 * S'il détecte une erreur, au lieu de modifier la table, il affiche un message d'erreur.
	 */
	private void updateText() {
		this.vue.getItems().clear();
		if (modele.erreurFichier){
			String[] alert = {"Erreur de serveur", "Un erreur est survenu, veuillez réessayer plus tard"};
			showAlert(alert);
		} else {
			for (int i=0 ; i< modele.courList.size();i++){
				this.vue.getItems().add(new Cour(modele.courList.get(i).getCode(), modele.courList.get(i).getName()));
			}
		}

	}

	/**
	 * Ce methode permet d'afficher un message d'alerte.
	 *
	 * @param alert		l'alerte a afficher
	 */
	public void showAlert(String[] alert){
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(alert[0]);
		errorAlert.setContentText(alert[1]);
		errorAlert.showAndWait();
	}

}