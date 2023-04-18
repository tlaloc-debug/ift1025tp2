package com.example.demo;

/**
 * La classe Command vous permet de créer une instance d'une Cour
 * qui contiendra le code et le nom d'un cours
 *
 * Cette classe est utilisée dans le modele MVC pour remplir le tableau
 * montre dans l'interface grafique en utilisant les methides
 * getCode et getName.
 *
 */
public class Cour {
    private String code;
    private String name;

    public Cour (String code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * Ce methode permet de changer le code d'un cours.
     *
     * @param code  le nouveau code
     */
    public void setCode(String code){
        this.code = code;
    }

    /**
     * Ce methode permet de changer le nom d'un cours.
     *
     * @param name  le nouveau nom
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Ce methode permet d'obtenir le code du cours.
     *
     * @return  le code du cours
     */
    public String getCode(){
        return this.code;
    }

    /**
     * Ce methode permet d'obtenir le nom du cours.
     *
     * @return  le nom du cours
     */
    public String getName(){
        return this.name;
    }

}
