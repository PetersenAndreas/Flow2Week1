package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.Person;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = getEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
        
    }
    
    @Override
    public PersonDTO getPerson(Long id) {
        EntityManager em = getEntityManager();
        try{
            Person p = em.find(Person.class, id);
            if(p != null) {
                return new PersonDTO(p);
            } else {
                return null;
            }
        } finally {
            em.clear();
        }
    }
    
    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try{
            TypedQuery q = em.createQuery("SELECT c FROM Person c", Person.class);
            List<Person> dbList = q.getResultList();
            PersonsDTO result = new PersonsDTO(dbList);
            return result;
        } finally {
            em.clear();
        }
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        EntityManager em = getEntityManager();
        Person p = new Person(fName, lName, phone);
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(Long id) {
    EntityManager em = getEntityManager();
        try {
            Person p = em.find(Person.class, id);
            if(p != null) {
            em.getTransaction().begin();
            em.remove(p);
            em.remove(p.getAddress());
            em.getTransaction().commit();
            return new PersonDTO(p);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, p.getId()); 
            em.getTransaction().begin();
            person.setPhone(p.getPhone());
            person.setfName(p.getfName());
            person.setlName(p.getlName());
            
            //I dont know what to do here.
            person.setLastEdited(new Date());
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

}
