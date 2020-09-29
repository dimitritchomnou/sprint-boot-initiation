package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service//à ajouter pour les service
public class ProduitServiceImp implements IProduit{
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired//Importe la couche DAO côté contrôleur
    private ProductDao productDao;

    @Override
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        //Creation objet filtre
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "id");//seule paramètre ignoré, ajouté les proprités à cacher
        FilterProvider listDenosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDenosFiltres);
        return produitsFiltres;
    }

    @Override
    public Product afficheUnProduit(int id) {
        //Product product = new Product(id, "Aspirateur", 100);
        //Créons une var prodruit
        Product produit = productDao.findById(id);
        if (produit == null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est introuvable");
        return produit;
    }

    @Override
    public ArrayList<Product> testeDeRequetes(Integer prixLimit) {
        //return productDao.findByPrixGreaterThan(prixLimit);
        return productDao.chercherUnProduitCher(prixLimit);
    }

    @Override
    public ArrayList<Product> testeDeRequetes(String recherche) {
        return productDao.findProductByName(recherche);
    }

    @Override
    public Product ajouterProduit(Product product) {
        try {
            if (product.getPrix() == 0){
                log.info("test", product);
                throw new ProduitGratuitException("Le prix ne doit pas être null");
            }
            final Product productAdded = productDao.save(product);
            if (productAdded == null){
                log.info("Le produit à ajouté est null", productAdded);//s'utilise si la méthode toString() existe
            }

            return productAdded;

        }catch (Exception e){
            log.info("L'objet est null");
            return new Product();
        }
    }

    @Override
    public void supprimerUnProduit(Integer id) {
        productDao.deleteById(id);
    }

    @Override
    public void updateProduit(Product product) {
        productDao.save(product);
    }

    @Override
    public HashMap<Product, Integer> calculMargeProduit() {
        List<Product> productList = productDao.findAll();
        HashMap<Product, Integer> produits = new HashMap<>();
        //Use Java 8 syntaxe
        productList.forEach(c -> {
            produits.put(c, (c.getPrix() - c.getPrixAchat()));
        });

        //Other syntax
        /*for (Product prod: productList) {
            produits.put(prod, prod.getPrix() - prod.getPrixAchat());
        }*/

        return produits;
    }

    @Override
    public ArrayList<Product> triProduitOrdreAlphabetique() {
        return productDao.poductTriASC();
    }

}
