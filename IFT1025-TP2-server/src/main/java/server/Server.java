package server;

import javafx.util.Pair;
import server.models.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        try {
            FileReader fr = new FileReader("cours.txt");
            BufferedReader reader = new BufferedReader(fr);
            String courString;
            while ((courString = reader.readLine()) != null) {
                String[] courListe = courString.split("\t");
                Course courObjet = new Course(courListe[1], courListe[0], courListe[2]);
                if (courObjet.getSession().equals(arg)){
                    //System.out.println(courListe[0]);
                    try {
                        this.objectOutputStream.writeObject(courObjet.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            reader.close();
        }catch (Exception e){
            System.out.println("Error lecture fichier");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        Course curso = new Course("IFT1015", "Programmation1", "Automne");
        try {
            String registration = this.objectInputStream.readObject().toString();
            this.objectOutputStream.writeObject(curso.toString());
            String resitrationList[] = registration.split(" ");
            String session = resitrationList[6].split("=")[1];
            String sessionTrim = session.substring(0, session.length()-3);
            String code = resitrationList[5].split("=")[1];
            String codeTrim = code.substring(0, code.length()-1);
            String matricule = resitrationList[3].split("=")[1];
            String matriculeTrim = matricule.substring(1, matricule.length()-2);
            String prenom = resitrationList[0].split("=")[1];
            String prenomTrim = prenom.substring(1, prenom.length()-2);
            String nom = resitrationList[1].split("=")[1];
            String nomTrim = nom.substring(1, nom.length()-2);
            String email = resitrationList[2].split("=")[1];
            String emailTrim = email.substring(1, email.length()-2);
            String registrationString = sessionTrim + "\t" + codeTrim + "\t"
                                        + matriculeTrim + "\t" + prenomTrim + "\t"
                                        + nomTrim + "\t" + emailTrim;
            try {
                FileWriter fw = new FileWriter("inscription.txt", true);
                BufferedWriter writer = new BufferedWriter(fw);
                writer.newLine();
                writer.append(registrationString);
                writer.close();
            } catch (IOException ex) {
                System.out.println("Erreur à l'écriture du fichier");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

