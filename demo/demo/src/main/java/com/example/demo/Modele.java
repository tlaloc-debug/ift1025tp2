package com.example.demo;

import java.io.*;
import java.net.*;
import java.util.*;
/*
 *	Notez que cette classe est completement indépendante de la representation.
 *	On pourraît facilement definir un interface complètement different.
 */
public class Modele {

	private int valeur;
	public String session = "Automne";

	public Modele() {
		this.valeur = 0;
	}

	public Modele(int v) {
		this.valeur = v;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public ArrayList<Cour> courList = new ArrayList<Cour>();

	public void ajouter() {

		ArrayList<Object> coursesList = new ArrayList<Object>();
		courList.clear();
		boolean repeter = true;
		String choixInscription = "";
		Scanner input = new Scanner(System.in);  // Create a Scanner object
		//System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");

		Command charger = new Command("CHARGER", session);

		try{
			Socket s=new Socket("localhost",1337);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

			oos.writeObject(charger.toString());

			repeter = true;
			while(repeter){
				try{
					Object coursParSession = objectInputStream.readObject();
					coursesList.add(coursParSession);
					String[] courseStringList = coursParSession.toString().split(" ");
					String codeCourse = courseStringList[1].split("=")[1];
					String codeCourseTrim = codeCourse.substring(0, codeCourse.length()-1);
					String nomCourse = courseStringList[0].split("=")[1];
					String nomCourseTrim = nomCourse.substring(0, nomCourse.length()-1);
					courList.add(new Cour(codeCourseTrim, nomCourseTrim));
					System.out.println(codeCourseTrim + "\t" + nomCourseTrim);
				}catch(EOFException e){
					repeter = false;
				}
			}

			oos.close();
			s.close();
		}catch(Exception e){
			System.out.println(e);
		}

		//return courList;

	}

	public void supprimer(RegistrationForm myForm) {

		Command inscrire = new Command("INSCRIRE", myForm.toString());
        try{
		Socket s=new Socket("localhost",1337);
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

		oos.writeObject(inscrire.toString());
		try{
			oos.writeObject(myForm.toString());
		}catch(Exception e){
			System.out.println(e);
		}
		Object coursParSession = objectInputStream.readObject();


		oos.close();
		s.close();
	}catch(Exception e){
		System.out.println(e);
	}

	}

	public void multiplier(int fois) {
		this.valeur = this.valeur * fois;
	}

	public void diviser(int fois) {
		this.valeur = this.valeur / fois;
	}

	public void changerSession(String session) {

		this.session = session;
	}

}
