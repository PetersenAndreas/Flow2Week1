package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import facades.IPersonFacade;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);;
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(String personStr) {
        PersonDTO personCon = GSON.fromJson(personStr, PersonDTO.class);
        personCon = FACADE.addPerson(personCon.getfName(), personCon.getlName(), personCon.getPhone());
        return GSON.toJson(personCon);
    }
    
    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getpersonOnId(@PathParam("id") Long id) throws PersonNotFoundException {
        PersonDTO pd = FACADE.getPerson(id);
//        If you comment this part in, you can test that the 500 exceptions also gets caught

//        if(id == 13) {  
//            System.out.println(1/0);
//        }
        if(pd == null) {
            throw new PersonNotFoundException("{code: 404, message: No person with provided id found}");
        }
        return GSON.toJson(pd);
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        PersonsDTO persons = FACADE.getAllPersons();
        return GSON.toJson(persons);
    }
    
    @PUT
    @Path("edit/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String editPersonOnId(String personStr, @PathParam("id") Long id) {
        PersonDTO personCon = GSON.fromJson(personStr, PersonDTO.class);
        personCon.setId(id);
        personCon = FACADE.editPerson(personCon);
        return GSON.toJson(personCon);
    }
    
    @PUT
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePersonOnId(@PathParam("id") Long id) throws PersonNotFoundException {
        PersonDTO p = FACADE.deletePerson(id);
        if(p == null) {
            throw new PersonNotFoundException("{code: 404, message: Could not delete, provided id does not exist}");
        }
        return GSON.toJson(p);
    }

 
}
