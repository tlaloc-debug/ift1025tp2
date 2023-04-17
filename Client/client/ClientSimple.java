package client;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * La classe permet aux étudiants d'obtenir la liste des cours offerts par l'Université de Montréal 
 * pour les sessions d'automne, d'hiver et d'été ainsi que de s'inscrire à l'un ou l'autre de ces cours.
 * 
 * Cette classe crée une connexion avec le serveur de l'Université de Montréal afin de faciliter 
 * le processus de consultation et d'inscription.
 * 
 * Son exécution se fait sur la ligne de commande.
 * 
 */
public class ClientSimple {  

    /**
     * La méthode principale crée une connexion au serveur de l'Université de Montréal et permet d'obtenir les cours 
     * offerts pour les sessions mentionnées ainsi que de s'inscrire à l'un de ces cours.
     * 
     * Premièrement, il affiche un message de bienvenue puis il tombe dans une boucle do-while qui permet de 
     * sélectionner la session pour laquelle vous souhaitez obtenir la liste des cours offerts. Cette boucle 
     * permet d'afficher la liste des cours autant de fois que vous le souhaitez en sélectionnant l'option 1: 
     * "Consulter les cours offerts pour une autre session", ou de sortir en sélectionnant l'option 2: 
     * "Inscription à un cours".
     * 
     * La boucle do-while demande à l'utilisateur de sélectionner la session pour laquelle il souhaite obtenir 
     * la liste des cours, puis crée une commande en créant une instance de la classe Command dans laquelle 
     * elle place la session sélectionnée comme argument de la commande. Puis, il crée une connexion au serveur 
     * qui lui permet d'obtenir la liste des cours et entre dans une boucle for qui lui permet d'afficher 
     * chaque cours récupéré sur le serveur. Finalement, il montre à l'utilisateur la possibilité d'obtenir 
     * la liste des cours pour une autre session ou de procéder à l'inscription.
     * 
     * Le processus d'inscription se déroule comme suit. Tout d'abord, la méthode demande les données de l'élève. 
     * Ensuite, il demande à l'étudiant de sélectionner le cours auquel il souhaite s'inscrire et vérifie 
     * que le code correspond à l'une des options affichées. Puis, il crée la commande d'inscription en créant 
     * une instance de la classe Command. Apres, il crée un formulaire d'inscription en créant une instance 
     * de la classe RegistrationForm qui contient les données de l'étudiant ainsi que le cours sélectionné. 
     * Ensuite, il crée la connexion avec le serveur qui lui permet d'envoyer le formulaire d'inscription. 
     * Finalement, il obtient la réponse du serveur et l'affiche à l'utilisateur.
     * 
     * Tous les arguments que la méthode reçoit de l'utilisateur via la ligne de commande sont vérifiés 
     * à l'aide d'une boucle infinie qui permet à l'étudiant de saisir à nouveau les données au cas où 
     * elles ne seraient pas valides
     * 
     * @param args  arguments reçus via la ligne de commande lors de l'exécution du code
     */
    public static void main(String[] args) {  
        ArrayList<Object> coursesList = new ArrayList<Object>();
        boolean repeter = true;
        String choixInscription = "";
        String choixSession = "";  
        String session = "";
        Scanner input = new Scanner(System.in);  
        System.out.println("\n*** Bienvenue au portail d'inscription de cours de l'UDEM ***");

        do {

            coursesList.clear(); 
            System.out.println("\nVeuillez choisir la session pour laquelle vous voulez consulter la liste de cours:");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Ete");
            
            System.out.print("\n> Choix: ");
            choixSession = input.nextLine(); 
            
            repeter = true;
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
                    System.out.println("Choix non valide, veuillez réessayer");
                    System.out.print("\n> Choix: ");
                    choixSession = input.nextLine(); 
                }
            }
                    
            Command charger = new Command("CHARGER", session);

            try{      
                Socket s=new Socket("localhost",1337);  
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
                
                oos.writeObject(charger.toString());
                System.out.println("\nCours offerts par cette session:");
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
                        System.out.println("\t" + codeCourseTrim + "\t" + nomCourseTrim);
                        
                    }catch(EOFException e){
                        repeter = false;
                        if (coursesList.size() == 0){
                            System.out.println("\nAucun cours trouvé.");
                            System.out.println("Il semble y avoir une erreur de serveur.");
                            System.out.println("veuillez réessayer plus tard.");
                        }
                    }
                }

                oos.close(); 
                s.close(); 
            }catch(Exception e){
                System.out.println(e);
            }  
            System.out.println("");
            System.out.println("1. Consulter les cours offerts pour une autre session");
            System.out.println("2. Inscription à un cours");
            System.out.print("\n> Choix: ");
            choixInscription = input.nextLine(); 

            repeter = true;
            while (repeter){
                if ((Integer.parseInt(choixInscription) > 0) && ((Integer.parseInt(choixInscription) < 3))){
                    repeter = false;
                } else {
                    System.out.println("Choix non valide, veuillez réessayer");
                    System.out.print("\n> Choix: ");
                    choixInscription = input.nextLine(); 
                }
            }
        
        } while (Integer.parseInt(choixInscription) == 1);

        String prenom = "";
        String nom = "";
        String email = ""; 
        String matricule = "";
        String codeCours = "";

        System.out.println("");

        System.out.print("Prenom: ");
        prenom = input.nextLine();
        while(prenom.equals("")){
            System.out.println("Veuillez indiquer votre prenom.");
            System.out.print("Prenom: ");
            prenom = input.nextLine();
        }
        System.out.print("Nom: ");
        nom = input.nextLine();
        while(nom.equals("")){
            System.out.println("Veuillez indiquer votre nom.");
            System.out.print("Nom: ");
            nom = input.nextLine();
        }
        System.out.print("E-mail: ");
        email = input.nextLine();
        repeter = true;
        while(repeter){
            try {
                if(email.split("@",2)[1].equals("umontreal.ca")){
                    repeter = false;
                } else {
                    System.out.println("Veuillez indiquer un email valide.");
                    System.out.print("E-mail: ");
                    email = input.nextLine();
                }
            } catch (Exception e){
                System.out.println("Veuillez indiquer un email valide.");
                System.out.print("E-mail: ");
                email = input.nextLine();
            }

        }
        System.out.print("Matricule: ");
        matricule = input.nextLine();
        repeter = true;
        while (repeter){
            if (matricule.length() == 6){
                try {
                    Integer.parseInt(matricule);
                    repeter = false;
                } catch (Exception e){
                    System.out.println("Matricule invalide.");
                    System.out.print("Matricule: ");
                    matricule = input.nextLine();
                }
            } else {
                System.out.println("Matricule invalide");
                System.out.print("Matricule: ");
                matricule = input.nextLine();
            }
        }
        System.out.print("Code de cours: ");
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
                System.out.println("Le code doit correspondre à une des options montrées");
                System.out.print("Code de cours: ");
                codeCours = input.nextLine();
            }    
        }

        Course courseInscription = new Course(null, null, null);
        
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
                
            }
           
        }
        
        RegistrationForm newRegistration = new RegistrationForm(prenom, nom, email, matricule, courseInscription);
                
        Command inscrire = new Command("INSCRIRE", null);
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

            switch (objectInputStream.readObject().toString()){
                case "Succes":
                System.out.println("\nFélicitations! Inscription réussie.\n");    
                break;

                case "Error":
                System.out.println("\nQuelque chose nous empêche de finir l'inscription.");   
                System.out.println("Veuillez réessayer plus tard.\n"); 
            }    
            
            oos.close(); 
            s.close();  
        }catch(Exception e){
            System.out.println(e);
        }  
                
        input.close();
        
    }  
}  
