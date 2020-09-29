package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;

public interface IProduit {

    MappingJacksonValue listeProduits();
    Product afficheUnProduit(int id);
    ArrayList<Product> testeDeRequetes(Integer prixLimit);
    ArrayList<Product> testeDeRequetes(String recherche);
    Product ajouterProduit(Product product);
    void supprimerUnProduit(Integer id);
    void updateProduit(Product product);
    HashMap<Product, Integer> calculMargeProduit();
    ArrayList<Product> triProduitOrdreAlphabetique();
}
