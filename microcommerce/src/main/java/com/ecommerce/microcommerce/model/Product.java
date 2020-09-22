package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

//@JsonIgnoreProperties(value = {"prixAchat", "id"})//plusieurs propriété à cacher
//@JsonFilter("monFiltreDynamique")//filtrage de propriété cas par cas

@Entity//Annotation pour Mapping
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    //annotation de validation pour champs
    @Length(min = 3, max = 20, message = "Nom trop long ou trop court.")
    private String nom;

    @Min(value = 1)
    private Integer prix;
    //Information à cacher
    //@JsonIgnore//Pour chacher, a utiliser lorsqu'a une seul propriété
    private Integer prixAchat;

    public Product() {
    }

    public Product(Integer id, String nom, Integer prix, Integer prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Integer prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
