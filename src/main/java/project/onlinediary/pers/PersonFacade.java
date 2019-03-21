/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.pers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Stateless
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
           
}
