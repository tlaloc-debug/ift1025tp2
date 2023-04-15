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

/**
 * La classe Serveur crée un serveur qui permet d'afficher les cours offerts par l'Université de Montréal
 * pour différentes sessions (automne, été et hiver)
 *
 * De plus, cette classe permet à un étudiant de s'inscrire aux différents cours offerts.
 *
 * Pour cela, l'utilisateur doit se connecter via un client CLI ou un client GUI.
 * Ce client facilite la communication entre les étudiants et le serveur.
 */
public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Cette méthode crée un serveur à l'aide de la méthode ServerSocket de la bibliothèque java.net
     * et accepte au maximum un client.
     *
     * Ensuite, cette méthode ajoute une liste contenant les eventhamdlers qui permettront
     * de décrire le comportement du serveur en fonction des interactions de l'utilisateur (client).
     *
     * @param port          le port de connexion du serveur
     * @throws IOException  exception s'il y a un problème en essayant d'ouvrir ou de connecter le socket
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Cette méthode ajoute un eventhandler à la liste des eventhandlers de la classe Server
     *
     * @param h     le eventhandler a ajouter
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Cette méthode permet d'implémenter chaque eventhandler dans la liste des eventhandlers de la classe Server
     * à une Pair formée par cmd et arg, en utilisant l'interface fonctionnelle de la classe EventHandler.
     *
     * @param cmd   la commande, soit CHARGER soit INSCRIRE
     * @param arg   les arguments de la commande, soit la session soit null
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * cette méthode vous permet d'accepter un nouveau client. une fois la connexion établie,
     * il crée un canal de communication à la fois pour recevoir des données,
     * en utilisant la classe ObjectInputStream, et pour les envoyer,
     * en utilisant la classe ObjectOutputStream
     *
     * Finalement, il appelle les méthodes listen() et disconnet()
     *
     * @throws  Exception   exception si un problème survient lors de connexion avec le client
     */
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

    /**
     * Cette méthode crée une Paire à partir de la commande reçue du client via l'objectInputStream.
     * Le Key de cette Paire est la commande elle-même et le Value de cette Paire sont les arguments de la commande,
     * (session pour la commande "CHARGER" et null pour la commande "INSCRIPTION").
     *
     * Ensuite, Cette méthode permet d'implémenter tous les eventhandlers contenus dans la liste des eventhandlers
     * de la classe Server en appelant la fonction alertHandlers()
     *
     * @throws IOException              exception s'il y a un problème avec le objectInputStream
     * @throws ClassNotFoundException   exception si la classe EventHandler est introuvable
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Cette méthode vous permet de créer un Pair à partir d'une String.
     *
     * Il divise la String en deux parties et affecte au champ Key du Pair la valeur de la première partie
     * de la String (commande) et au champ Value du Pair la valeur de la deuxième partie de la String (arguments).
     *
     * Finalement, il retourne le Pair cree.
     *
     * @param line  le String a partir duquel le methode cree le Pair
     * @return      le Pair cree
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Cette méthode permet de fermer la connexion établie avec le client ainsi que les canaux de communication.
     *
     * @throws IOException  exception lors de la tentative de fermeture de connexions qui n'existent pas
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Cette méthode permet de définir les actions que le serveur doit effectuer en fonction
     * de la commande reçue du client.
     *
     * Si la commande reçue est de type "CHARGER", le serveur exécute la méthode handleLoadCourses(arg).
     * Si la commande reçue est de type "INSCRIRE" le serveur exécute la méthode handleRegistration().
     *
     * @param cmd   la commande qui permet de décider quelle méthode appeler
     * @param arg   la session pour la commande "CHARGER"
     */
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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            reader.close();
        }catch (Exception ex){
            System.out.println("Error lecture fichier");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {

        try {
            String registration = this.objectInputStream.readObject().toString();
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
                this.objectOutputStream.writeObject(new Response("Succes").getMessage());
            } catch (IOException ex) {
                this.objectOutputStream.writeObject(new Response("Error").getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

