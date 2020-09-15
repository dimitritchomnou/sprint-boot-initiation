package com.ecommerce.microcommerce.web.controller;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;


    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    //@GetMapping(value = "/Produits")

    /*public ArrayList<Product> listeProduits(){
        //return "Un exemple de produit";
        return productDao.findAll();
    }*/
    //Utilisation de filtre afin de traiter le cachage de propriété cas par cas
    public MappingJacksonValue listeProduits(){
        Iterable<Product> produits = productDao.findAll();
        //Creation objet filtre
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "id");//seule paramètre ignoré, ajouté les proprités à cacher
        FilterProvider listDenosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDenosFiltres);
        return produitsFiltres;
    }

    //@RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    @GetMapping(value = "/Produits/{id}")
    public Product afficheUnProduit(@PathVariable int id) {
        //Product product = new Product(id, "Aspirateur", 100);
        //Créons une var prodruit
        Product produit = productDao.findById(id);
        if (produit == null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est introuvable");
        return produit;

    }

    //recherche en fonction du prix
    @GetMapping(value = "testPrix/produits/{prixLimit}")
    public ArrayList<Product> testeDeRequetes(@PathVariable Integer prixLimit){
        //return productDao.findByPrixGreaterThan(prixLimit);
        return productDao.chercherUnProduitCher(prixLimit);
    }

    @GetMapping(value = "testName/produits/{recherche}")
    public ArrayList<Product> testeDeRequetes(@PathVariable String recherche){
        //return productDao.findByNomLike("%"+ recherche +"%");
        //Utilisation d'une Query
        return productDao.findProductByName(recherche);
    }
    //@Valid pour la validation des champs
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product){
        Product productAdded = productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        //Construction de l'url d'ajout de produit
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerUnProduit(@PathVariable Integer id){
        productDao.deleteById(id);
    }

    //MAJ
    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product){
        productDao.save(product);
    }

}
