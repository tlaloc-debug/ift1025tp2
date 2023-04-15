package client;

import java.io.Serializable;

/**
 * La classe Course vous permet de créer des instances de cours qui 
 * contiennent le code, le nom et la session de chaque cours offert 
 * par l'Université de Montréal.
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * Cette méthode permet d'obtenir le nom du cours.
     * 
     * @return  le nom de cours
     */
    public String getName() {
        return name;
    }

    /**
     * Cette méthode vous permet de changer le nom du cours.
     * 
     * @param name le nouveau nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Cette méthode permet d'obtenir le code du cours.
     * 
     * @return  le code de cours
     */
    public String getCode() {
        return code;
    }

    /**
     * Cette méthode vous permet de changer le code du cours.
     * 
     * @param code  le nouveau code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Cette méthode permet d'obtenir la session du cours.
     * 
     * @return  la session de cours
     */
    public String getSession() {
        return session;
    }

    /**
     * Cette méthode vous permet de changer la session du cours.
     * 
     * @param session   la nouvelle session du cours
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * La méthode toString permet de créer une String qui contient 
     * le nom, le code et la session du cours au format json.
     * 
     * @return  l'information du cours en format json 
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }

}