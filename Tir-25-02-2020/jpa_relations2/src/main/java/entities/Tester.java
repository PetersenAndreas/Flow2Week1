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
    
       
    
//    public static void main(String[] args) {
//        Persistence.generateSchema("pu", null);
//        
//    }
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Customer c1 = new Customer("Lars", "Larsen");
        Customer c2 = new Customer("Jens", "Jensen");
        
        Address a1 = new Address("Stormgade 11","København");
        Address a2 = new Address("Krystalgade 2", "København");
        a1.setCustomer(c1);
        a2.setCustomer(c2);
        c1.addAddress(a1);
        c2.addAddress(a2);
        em.persist(c1);
        em.persist(c2);
        
        em.getTransaction().commit();
        
    }
    
}
