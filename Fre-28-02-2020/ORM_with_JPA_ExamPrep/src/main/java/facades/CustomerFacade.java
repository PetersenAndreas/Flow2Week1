/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Customer;
import entities.ItemType;
import entities.PurchaseOrder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author andre
 */
public class CustomerFacade {

// Used this to create the empty tables in MySQL    
//    public static void main(String[] args) {
//        Persistence.generateSchema("pu", null);
//        
//    }
    private static CustomerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CustomerFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer createCustomer(String name, String email) {
        EntityManager em = getEntityManager();
        Customer c = new Customer(name, email);
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return c;
        } finally {
            em.close();
        }
    }
    
    public Customer findCustomer(int id) {
        EntityManager em = getEntityManager();
        try{
            Customer c = em.find(Customer.class, id);
            if(c != null) {
                return c;
            } else {
                return null;
            }
        } finally {
            em.clear();
        }
    }
    
    public List<Customer> getAllCustomer() {
        EntityManager em = getEntityManager();
        try{
            TypedQuery q = em.createQuery("SELECT c FROM Customer c", Customer.class);
            List<Customer> qList = q.getResultList();
            return qList;
        } finally {
            em.clear();
        }
    }
    
    public ItemType createItemType(String name, String description, int price) {
        EntityManager em = getEntityManager();
        ItemType it = new ItemType(name, description, price);
        try {
            em.getTransaction().begin();
            em.persist(it);
            em.getTransaction().commit();
            return it;
        } finally {
            em.close();
        }
    }
    
    public ItemType findItemType(int id) {
        EntityManager em = getEntityManager();
        try{
            ItemType it = em.find(ItemType.class, id);
            if(it != null) {
                return it;
            } else {
                return null;
            }
        } finally {
            em.clear();
        }
    }
    
//    public Customer createOrderAddToCustomer(int orderId, String name, String email) {
//        EntityManager em = getEntityManager();
//        PurchaseOrder po = new PurchaseOrder(orderId);
//        try {
//            em.getTransaction().begin();
//            em.persist(po);
//            em.getTransaction().commit();
//            Customer c = em.find(Customer.class, email);
//            c.setOrders((List<PurchaseOrder>) po);
//            return c;
//        } finally {
//            em.close();
//        }
//    }
    
}
