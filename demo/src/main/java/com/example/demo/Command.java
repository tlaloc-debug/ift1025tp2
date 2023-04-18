package com.example.demo;

import java.io.Serializable;

/**
 * La classe Command vous permet de créer une instance d'une commande
 * qui contiendra la commande "CHARGER" ou "INSCRIRE".
 * Si la commande est "CHARGER", l'instance doit également contenir
 * un argument correspondant à la session pour laquelle vous souhaitez
 * obtenir la liste des cours.
 *
 * Cette classe est utilisée par le client pour indiquer au serveur
 * s'il souhaite obtenir une liste de cours ou s'il souhaite procéder
 * à une inscription.
 *
 */
public class Command implements Serializable{

    public String cmd;
    public String args;

    public Command(String cmd, String args) {
        this.cmd = cmd;
        this.args = args;
    }

    /**
     * La méthode toString() permet de créer une String qui contient
     * la commande ainsi que les arguments séparés par un espace.
     */
    @Override
    public String toString() {
        return cmd + " " + args;
    }
    
}
