package com.example.demo;

import java.io.Serializable;

/**
 * La classe RegistrationForm vous permet de créer des instances
 * contenant les données de l'étudiant ainsi que le cours auquel
 * il souhaite s'inscrire.
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * Cette méthode permet d'obtenir le prenom de l'étudiant
     *
     * @return  le prenom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Cette méthode permet de changer le prenom de l'étudiant
     *
     * @param prenom    le nouveau prenom de l'étudiant
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Cette méthode permet d'obtenir le nom de l'étudiant.
     *
     * @return  le nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }

    /**
     * Cette méthode permet de changer le nom de l'étudiant
     *
     * @param nom   le nouveau nom de l'étudiant
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Cette méthode permet d'obtenir l'email'de l'étudiant.
     *
     * @return  l'email'de l'étudiant
     */
    public String getEmail() {
        return email;
    }

    /**
     * Cette méthode permet de changer l'email de l'étudiant.
     *
     * @param email le nouveau email de l'étudiant
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Cette méthode permet d'obtenir la matricule de l'étudiant.
     *
     * @return  la matricule de l'étudiant
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Cette méthode permet de changer la matricule de l'étudiant.
     *
     * @param matricule la nouvelle matricule de l'étudiant
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Cette méthode vous permet d'obtenir le cours auquel l'étudiant souhaite s'inscrire.
     *
     * @return  le cours auquel l'étudiant souhaite s'inscrire.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Cette méthode vous permet de changer le cours auquel l'étudiant souhaite s'inscrire.
     *
     * @param course    le nouveau cours auquel l'étudiant souhaite s'inscrire.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Cette méthode permet de créer un String qui contient les données du formulaire
     * d'inscription au format json.
     *
     * @return  les données du formulaire d'inscription au format json.
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
