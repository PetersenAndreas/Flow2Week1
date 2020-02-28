/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import entities.Customer;
import entities.ItemType;
import facades.CustomerFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author andre
 */
public class Tester {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private static final CustomerFacade FACADE = CustomerFacade.getFacadeExample(emf);
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        
        Customer c1 = FACADE.createCustomer("Lars Larsen", "a@b.dk");
        Customer c2 = FACADE.createCustomer("Henning Nina", "c@d.dk");
        
        ItemType it1 = FACADE.createItemType("Ananas", "pointy", 55);
        ItemType it2 = FACADE.createItemType("Jakabov", "Ad", 10);
        
        Customer f1 = FACADE.findCustomer(1);
        ItemType fIT2 = FACADE.findItemType(1);
        
        List<Customer> all = FACADE.getAllCustomer();
        
        try{
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(it2);
            em.persist(it1);
            em.getTransaction().commit();
            
            
            System.out.println(c1.toString());
            System.out.println(c2.toString());
            System.out.println(it2);
            System.out.println(it1);
            System.out.println(f1);
            System.out.println(fIT2);
            System.out.println(all);
            
        } finally {
            em.close();
        }
        
    }
}
