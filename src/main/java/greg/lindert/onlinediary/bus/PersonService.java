
package greg.lindert.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.*;
import greg.lindert.onlinediary.ents.Person;
import greg.lindert.onlinediary.pers.PersonFacade;



/**
 *
 * @author up780016
 */
@Stateful
public class PersonService {

 
    
    @EJB
    private PersonFacade pf;
    
    /**
     * Creates a new Person 
     * @param p Person details Object to be created 
     * @return Created Person Object 
     */
    public Person createNewPerson(Person p) {
        p.setPassword(pf.genHashPass(p.getPassword()));
        pf.create(p);
        return p;
    }
    
    /**
     * Updates a new Person 
     * @param p Person details Object to be Updated 
     * @return Updated Person Object 
     */   
    public Person updatePerson(Person p) {
        pf.edit(p);
        
        return p;
    }
    
    /**
     * Gets all users that are registered 
     * @return List of all users that are registered 
     */
    public List<Person> getAllPerson() {
        return pf.findAll();
    }
       
    /**
     * Gets all users by a like phrase and not the logged in user 
     * @param searchfor like phrase to search for in database 
     * @param userid logged in users id
     * @return List of all users that were found 
     */
    public List<Person> getPersonBy(String searchfor,Long userid) {
        return pf.findByAll(searchfor,userid);
    }
    
    /**
     * Adds person to entity manger 
     * @param p Person object to be added to entity manger 
     */
    public void addpersontoent(Person p) {
        pf.addpersontoentity(p);
    } 
    
    /**
     * Checks database for a duplicate see if a username is all ready registered 
     * @param u username to search for in database 
     * @return true or false if a user is all ready registered with the same username 
     */
    public boolean checkUsername(String u) {
        List<Person> temp = pf.findPersonByUsername(u);
        return temp.isEmpty();
    }

    
}
