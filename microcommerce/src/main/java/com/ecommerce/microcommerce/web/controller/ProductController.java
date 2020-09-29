package com.ecommerce.microcommerce.web.controller;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.service.IProduit;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//Description pour l'API
@Api(description = "API pour les opérations CRUD sur les produits.")
@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired//Importe la couche DAO côté contrôleur
    private ProductDao productDao;

    @Autowired
    private IProduit iProduit;

    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    //Utilisation de filtre afin de traiter le cachage de propriété cas par cas
    public MappingJacksonValue listeProduits(){
        return iProduit.listeProduits();
    }

    //@RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficheUnProduit(@PathVariable int id) {
        return iProduit.afficheUnProduit(id);
    }

    //recherche en fonction du prix
    @GetMapping(value = "testPrix/produits/{prixLimit}")
    public ArrayList<Product> testeDeRequetes(@PathVariable Integer prixLimit){
        return iProduit.testeDeRequetes(prixLimit);
    }

    @GetMapping(value = "testName/produits/{recherche}")
    public ArrayList<Product> testeDeRequetes(@PathVariable String recherche){
        return iProduit.testeDeRequetes(recherche);
    }

    //@Valid pour la validation des champs
    @PostMapping(value = "/Produits")//@Valid
    //public Product ajouterProduit(@Valid @RequestBody Product product){@valide pour la close @Min(value = 1)
    public Product ajouterProduit(@RequestBody Product product){
       return iProduit.ajouterProduit(product);
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerUnProduit(@PathVariable Integer id){
        iProduit.supprimerUnProduit(id);
    }

    //MAJ
    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product){
        iProduit.updateProduit(product);
    }

    //Calcul marge produit
    @GetMapping(value = "marge/produits/")
    public HashMap<Product, Integer> calculMargeProduit(){
        return iProduit.calculMargeProduit();
    }

    //Tri par ordre alphabétique
    @GetMapping(value = "tri/produits/")
    public ArrayList<Product> triProduitOrdreAlphabetique(){
       return iProduit.triProduitOrdreAlphabetique();
    }
}
