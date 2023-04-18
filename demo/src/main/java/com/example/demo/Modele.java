package com.example.demo;

import java.io.*;
import java.net.*;
import java.util.*;
/*
 *	Notez que cette classe est completement indépendante de la representation.
 *	On pourraît facilement definir un interface complètement different.
 */

/**
 * La classe Modele contient tous les actions (methodes) que le client GUI doit effectuer
 * pour permettre l'usager de consulter la liste de courses pour un session especifique
 * et de faire un inscripcion a un de ces courses.
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
	public boolean erreurFichier;
	public String response;

	/**
	 * Cette méthode permet d'obtenir la liste de tous les cours offerts par l'Université de Montréal
	 * pour une session spécifique en créant une connexion avec un serveur qui contient ces informations.
	 *
	 * Tout d'abord, la méthode crée une instance de la classe Commande. Puis il crée une connexion au serveur.
	 * Ensuite, il crée un canal de connexion pour envoyer des données et un autre pour recevoir des données.
	 * Apres, il envoie l'instance avec la session à partir de laquelle il faut consulter les cours, en paramètre.
	 * Finalement, il enregistre chacun des cours dans une liste.
	 *
	 *
	 */
	public void charger() {

		ArrayList<Object> coursesList = new ArrayList<Object>();
		courList.clear();
		erreurFichier = false;
		boolean repeter = true;
		String choixInscription = "";
		Scanner input = new Scanner(System.in);

		// Creation de commande
		Command charger = new Command("CHARGER", session);

		try{
			// création de connexion et canaux de communication
			Socket s=new Socket("localhost",1337);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

			oos.writeObject(charger.toString());

			repeter = true;
			while(repeter){
				try{
					Object coursParSession = objectInputStream.readObject();
					coursesList.add(coursParSession);
					// On separe les champs nécessaires a partir d'un String
					String[] courseStringList = coursParSession.toString().split(" ");
					String codeCourse = courseStringList[1].split("=")[1];
					String codeCourseTrim = codeCourse.substring(0, codeCourse.length()-1);
					String nomCourse = courseStringList[0].split("=")[1];
					String nomCourseTrim = nomCourse.substring(0, nomCourse.length()-1);
					courList.add(new Cour(codeCourseTrim, nomCourseTrim));
					//System.out.println(codeCourseTrim + "\t" + nomCourseTrim);
				}catch(EOFException ex){
					repeter = false;
					if (coursesList.size() == 0){
						erreurFichier = true;
					}
				}
			}

			oos.close();
			s.close();
		}catch(Exception ex){
			System.out.println(ex);
		}

	}

	/**
	 * Cette méthode permet à un étudiant de s'inscrire à un cours particulier en créant
	 * une connexion au serveur et en envoyant le formulaire d'inscription.
	 *
	 * Tout d'abord, la méthode crée une instance de la classe Commande. Puis il crée une connexion au serveur.
	 * Ensuite, il crée un canal de connexion pour envoyer des données et un autre pour recevoir des données.
	 * Puis, il envoie le formulaire d'inscription.
	 *
	 * Finalement, il enregistre la reponse du serveur dans un variable.
	 *
	 * @param RegForm	les donnnes de l'etudaint
	 */
	public void inscrire(RegistrationForm RegForm) {

		// Creation de commande
		Command inscrire = new Command("INSCRIRE", null);
        try{
			// création de connexion et canaux de communication
			Socket s=new Socket("localhost",1337);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

			oos.writeObject(inscrire.toString());
			try{
				oos.writeObject(RegForm.toString());
			}catch(Exception ex){
				System.out.println(ex);
			}

			response = objectInputStream.readObject().toString();

			oos.close();
			s.close();
		}catch(Exception ex){
			System.out.println(ex);
		}

	}

	/**
	 * Cette méthode vous permet de changer la session pour laquelle vous souhaitez consulter
	 * la liste des cours disponibles
	 *
	 * @param session	la nouvelle session
	 */
	public void changerSession(String session) {

		this.session = session;
	}

}
