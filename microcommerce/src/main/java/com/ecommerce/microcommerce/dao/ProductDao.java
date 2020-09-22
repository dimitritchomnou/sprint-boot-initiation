package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    //Ici on défini tout nos méthode d'accès auw données
    //public ArrayList<Product> findAll();
    Product findById(int id);
    ArrayList<Product> findByPrixGreaterThan(int prixLimit);
    ArrayList<Product> findByNomLike(String recherche);
    //Product save(Product product);
    //Requête personnalisée
    @Query("select p from Product p where p.prix > :prixLimit")
    ArrayList<Product>  chercherUnProduitCher(@Param("prixLimit") Integer prix);

    @Query("select p from Product p where p.nom like CONCAT('%',:nameProduct,'%')")
    ArrayList<Product> findProductByName(@Param("nameProduct") String nameProduct);

    /*@Query("select p.prix from Product p where p.id = :idProduit")
    Integer prixProduit (@Param("idProduit") Integer id);

    @Query("select p.prixAchat from Product p where p.id = :idProduit")
    Integer prixAchatProduit (@Param("idProduit") Integer id);*/

}
