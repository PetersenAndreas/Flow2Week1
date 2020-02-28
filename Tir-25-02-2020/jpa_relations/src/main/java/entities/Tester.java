/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author andre
 */
public class Tester {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Customer c1 = new Customer("Lars", "Larsen");
        Customer c2 = new Customer("Jens", "Jensen");
        
        c1.addHobby("Løb");
        c1.addHobby("øl");
        c2.addHobby("Folkedans");
        
        c1.addPhone("88888888", "Det er Leasy");
        c2.addPhone("114", "Det er politiet");
        
        em.persist(c1);
        em.persist(c2);
        
        em.getTransaction().commit();
        
    }
    
}
