package server;

import java.io.Serializable;

/**
 * La classe Response permet de créer des instances contenant un texte au format String.
 *
 * Cette classe est utilisée par le serveur lors d'un inscription pour indiquer au client
 * si l'inscription a réussi ou si une erreur s'est produite.
 */
public class Response implements Serializable {

    private String message;

    public Response(String message){
        this.message = message;
    }

    public String getMessage (){
        return this.message;
    }

}
