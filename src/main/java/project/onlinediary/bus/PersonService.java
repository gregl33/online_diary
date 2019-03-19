/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.bus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;
import project.onlinediary.ents.Person;
import project.onlinediary.pers.AddressFacade;
import project.onlinediary.pers.PersonFacade;



/**
 *
 * @author greg
 */
@Stateless
public class PersonService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @EJB
    private PersonFacade pf;
    @EJB
    private AddressFacade af;
    
    
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
    
    
}
