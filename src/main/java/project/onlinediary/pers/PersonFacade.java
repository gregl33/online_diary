/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.pers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;
import project.onlinediary.bus.PersonService;
import project.onlinediary.ents.Person;

/**
 *
 * @author greg
 */
@Stateful
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "onlineDiary_persistance")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
    
    public List<Person> findPersonByUsername (String name2searchfor){
        Query q = em.createQuery("SELECT p FROM Person p WHERE p.username = :searchname");
        q.setParameter("searchname", name2searchfor);
        List<Person> pl = q.getResultList();
        return pl;
    }
        
        
    public List<Person> getPersonByUsernamePassword (String name2Search, String pass2Search) {
        Query q = em.createQuery("SELECT p From Person p WHERE p.username = :name AND p.password = :pass");
        q.setParameter("name", name2Search);
        q.setParameter("pass", pass2Search);
        List<Person> personsFound = q.getResultList();
//        em.persist(this);

//        if (!em.contains(personsFound.get(0))) {
//          em.persist(personsFound.get(0));
//        } else {
//          em.merge(personsFound.get(0));
//        }
        
        return personsFound;
    }
    
    
    public List<Person> findByAll(String searchfor, Long userid) {
        Query q = em.createQuery("SELECT p From Person p "
                + "WHERE p.id != :userid AND "
                + "(lower(p.lastname) LIKE lower(:name)"
                + "OR lower(p.firstname) LIKE lower(:name)"
                + "OR lower(p.email) LIKE lower(:name)"
                + "OR lower(p.phonenumber) LIKE lower(:name))"

        );

        q.setParameter("name", "%" + searchfor + "%");
        q.setParameter("userid", userid);

        List<Person> personsFound = q.getResultList();
        return personsFound;
    }
    
    
    public String genHashPass(String pass){
        
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));

        String hex = DatatypeConverter.printHexBinary(hash);
        return hex;
    }
    
    public void addpersontoentity(Person p){
        em.merge(p);
//        em.persist(p);

   
    }
    
    
    
    
//        public List<Person> findByGuest (Date startdate_, Date enddate_, List<Person> guestList_) {
//        Query q = em.createQuery("SELECT p FROM Person p WHERE p in :guestList "
//                + "AND (:startdate BETWEEN p.events.start_datetime AND p.events.end_datetime"
//                + "OR :enddate BETWEEN  p.events.start_datetime AND p.events.end_datetime)"
//        );
//                
//                
////                "SELECT e FROM Event e "
////                + "WHERE e.guests IN :guestList "
////                + "AND :startdate BETWEEN  e.start_datetime AND e.end_datetime "
////                + "OR :enddate BETWEEN  e.start_datetime AND e.end_datetime" );
//       
//        q.setParameter("startdate", startdate_);
//        q.setParameter("enddate", enddate_);
//        q.setParameter("guestList", guestList_);
//
//        List<Person> evetnsFound = q.getResultList();
//        return evetnsFound;
//    }
           
}
