/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.*;
import project.onlinediary.ents.Event;
import project.onlinediary.ents.Person;
import project.onlinediary.pers.PersonFacade;



/**
 *
 * @author greg
 */
@Stateful
public class PersonService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @EJB
    private PersonFacade pf;
    
        public Person createNewPerson(Person p) {//throws BusinessException {
        //check things: duplicates
//        if (pf.findPersonByUsername(p.getUsername()).isEmpty()) {


//        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        byte[] hash = digest.digest(p.getPassword().getBytes(StandardCharsets.UTF_8));
//
//        String hex = DatatypeConverter.printHexBinary(hash);
    
            p.setPassword(pf.genHashPass(p.getPassword()));
            pf.create(p);
            return p;
//        } else {
            //raise problem
//            throw new BusinessException("Duplicated name: " + p.getName());
//        }
    }
    
        
    public Person updatePerson(Person p) {
        pf.edit(p);
        return p;
    }
    
    public List<Person> getAllPerson() {
        return pf.findAll();
    }
       
    public List<Person> getPersonBy(String searchfor,Long userid) {
        return pf.findByAll(searchfor,userid);
    }
        
    public void addpersontoent(Person p) {
        pf.addpersontoentity(p);
    } 
    
//    public List<Person> checkGuestAvailability(Event e){
//  
//        return pf.findByGuest(e.getStart_datetime(),e.getEnd_datetime(),e.getGuests());
//    }
    
}
