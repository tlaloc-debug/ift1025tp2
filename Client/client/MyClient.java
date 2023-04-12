package client;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyClient {  

    public static void main(String[] args) {  
        ArrayList<Object> coursesList = new ArrayList<Object>();
        boolean repeter = true;
        String choixInscription = "";
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");

        do {
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours:");
        System.out.println("1. Automne");
        System.out.println("2. Hiver");
        System.out.println("3. Ete");
        
        System.out.print("> Choix: ");
        String choixSession = input.nextLine();  // Read user input

        String session = "";
        
        while (repeter){
            if ((Integer.parseInt(choixSession) > 0) && ((Integer.parseInt(choixSession) < 4))){
                
                switch(choixSession) {
                    case "1":
                        session = "Automne";
                        break;
                    case "2":
                        session = "Hiver";
                        break;
                    case "3":
                        session = "Ete";
                      break;
                }
                repeter = false;
            } else {
                System.out.println("Invalide");
                System.out.print("> Choix: ");
                choixSession = input.nextLine(); 
            }
        }
                 
        client.Command charger = new client.Command("CHARGER", session);

        //connect au serveur
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

        System.out.println("1. Consulter les cours offerts pour une autre session");
        System.out.println("2. Inscription à un cours");
        System.out.print("> Choix: ");
        choixInscription = input.nextLine(); 

        repeter = true;
        //Boucle limitant les numéros disponibles pour les choix de cours
            while (repeter){
                if ((Integer.parseInt(choixInscription) > 0) && ((Integer.parseInt(choixInscription) < 3))){
                    repeter = false;
                } else {
                    System.out.println("Invalide");
                    System.out.print("> Choix: ");
                    choixInscription = input.nextLine();
                }
            }
        
        } while (Integer.parseInt(choixInscription) == 1);

        String prenom = "";
        String nom = "";
        String email = ""; 
        String matricule = "";
        String codeCours = "";

        //Recevoir le nom et prénom du client
        System.out.print("prenom: ");
        prenom = input.nextLine();
        while(prenom.equals("")){
            System.out.println("Veuillez indiquer votre prenom.");
            System.out.print("prenom: ");
            prenom = input.nextLine();
        }
        System.out.print("nom: ");
        nom = input.nextLine();
        while(nom.equals("")){
            System.out.println("Veuillez indiquer votre nom.");
            System.out.print("nom: ");
            nom = input.nextLine();
        }
        //System.out.print(nom + prenom);
        System.out.print("email: ");
        email = input.nextLine();
        repeter = true;

        // Recevoir le courriel du client avec des restrictions concernant le format
        while(repeter){
            try {
                if(email.split("@",2)[1].equals("umontreal.ca")){
                    repeter = false;
                } else {
                    System.out.println("Veuillez indiquer un email valide.");
                    System.out.print("email: ");
                    email = input.nextLine();
                }
            } catch (Exception e){
                System.out.println("Veuillez indiquer un email valide.");
                System.out.print("email: ");
                email = input.nextLine();
            }

        }
        System.out.print("matricule: ");
        matricule = input.nextLine();
        repeter = true;
        while (repeter){
            if (matricule.length() == 6){
                try {
                    Integer.parseInt(matricule);
                    repeter = false;
                } catch (Exception e){
                    System.out.println("Mat invalide");
                    System.out.print("matricule: ");
                    matricule = input.nextLine();
                }
            } else {
                System.out.println("Mat invalide");
                System.out.print("matricule: ");
                matricule = input.nextLine();
            }
        }
        System.out.print("code de cours: ");
        codeCours = input.nextLine();

        repeter = true;
        while (repeter){
            for (int i = 0; i < coursesList.size(); i++){
                String[] coursListListe = coursesList.get(i).toString().split(" ");
                if (coursListListe[1].split("=")[1].equals(codeCours + ",")){
                    repeter = false;
                } 
            }
            if (repeter){
                System.out.println("Ce code ne corresponde pas au aucune choix montre");
                System.out.print("code de cours: ");
                codeCours = input.nextLine();
            }    
        }

        client.Course courseInscription = new client.Course(null, null, null);
        
        for (int i = 0; i < coursesList.size(); i++){
            String[] coursListListe = coursesList.get(i).toString().split(" ");
            if (coursListListe[1].split("=")[1].equals(codeCours + ",")){
                String codeInscription = coursListListe[1].split("=")[1];
                String codeInscriptionTrim = codeInscription.substring(0, codeInscription.length()-1);
                String nomInscription = coursListListe[0].split("=")[1];
                String nomInscriptionTrim = nomInscription.substring(0, nomInscription.length()-1);
                String sessionInscription = coursListListe[2].split("=")[1];
                String sessionInscriptionTrim = sessionInscription.substring(0, sessionInscription.length()-1);
                courseInscription.setCode(codeInscriptionTrim);
                courseInscription.setName(nomInscriptionTrim);
                courseInscription.setSession(sessionInscriptionTrim);
                //System.out.println(codeInscriptionTrim);
            }
           
        }
        
        client.RegistrationForm newRegistration = new client.RegistrationForm(prenom, nom, email, matricule, courseInscription);
        //System.out.println(newRegistration.toString());
        
        client.Command inscrire = new client.Command("INSCRIRE", newRegistration.toString());
        try{      
            Socket s=new Socket("localhost",1337);  
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            
            oos.writeObject(inscrire.toString());
            try{
                oos.writeObject(newRegistration.toString());
            }catch(Exception e){
                System.out.println(e);
            }  
            Object coursParSession = objectInputStream.readObject();
                      
            
            oos.close(); 
            s.close();  
        }catch(Exception e){
            System.out.println(e);
        }  
        System.out.println("Félicitations, inscription réussi de " + prenom + " au cours " + codeCours + ".");
        input.close();

    }  
}  
