package com.example.demo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.*;
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

	public void inc() {

		this.modele.ajouter();
		this.updateText();
		/*
		ArrayList<Cour> courList = new ArrayList<Cour>();
		courList =
		this.updateText();
		return courList;

		 */
	}

	public void dec() {
		try {
			Cour myCour = (Cour) vue.getSelectionModel().getSelectedItem();
			//System.out.println(myCour.getCode() + " " + myCour.getName());
			Course myCourse = new Course(myCour.getName(), myCour.getCode(), session.getValue().toString());
			if (prenomInput.getText().equals("")||nomInput.getText().equals("")
					||emailInput.getText().equals("")||matriculeInput.getText().equals("")){
				String[] alert = {"Error", "Tous les champs sont requis"};
				showAlert(alert);
			} else {
				try {
					if ((emailInput.getText().split("@",2)[1].equals("umontreal.ca"))){
						if (matriculeInput.getText().length() == 6){
							try {
								Integer.parseInt(matriculeInput.getText());
								RegistrationForm myReg= new RegistrationForm(prenomInput.getText(), nomInput.getText(),
										emailInput.getText(), matriculeInput.getText(), myCourse);
								this.modele.supprimer(myReg);
							} catch (Exception e){
								String[] alert = {"Error", "Veuillez indiquer un matricule valide"};
								showAlert(alert);
							}
						} else {
							String[] alert = {"Error", "Veuillez indiquer un matricule valide"};
							showAlert(alert);
						}
					} else {
						String[] alert = {"Error", "Veuillez indiquer un email valide"};
						showAlert(alert);
					}
				} catch (Exception e) {
					String[] alert = {"Error", "Veuillez indiquer un email valide"};
					showAlert(alert);
				}

			}
		} catch (Exception e){
			//System.out.println("Select a cour");
			String[] alert = {"Cour missing", "Please, select a cour"};
			showAlert(alert);

		}
		//this.updateText();
	}

	public void dub() {
		this.modele.multiplier(2);
		this.updateText();
	}

	public void div() {
		this.modele.diviser(2);
		this.updateText();
	}

	public void sessionBox() {
		this.modele.changerSession(session.getValue().toString());
		this.updateText();
	}

	private void updateText() {
		this.vue.getItems().clear();
		for (int i=0 ; i< modele.courList.size();i++){
			this.vue.getItems().add(new Cour(modele.courList.get(i).getCode(), modele.courList.get(i).getName()));
		}
		//this.vue.setText(String.valueOf(this.modele.getValeur()));
	}

	public void showAlert(String[] alert){
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(alert[0]);
		errorAlert.setContentText(alert[1]);
		errorAlert.showAndWait();
	}

}