/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import project.onlinediary.bus.AddressService;
import project.onlinediary.bus.PersonService;
import project.onlinediary.ents.Address;
import project.onlinediary.ents.Person;

/**
 *
 * @author greg
 */
@Named(value = "personCtrl")
@RequestScoped
public class PersonCtrl {

    /**
     * Creates a new instance of PersonCtrl
     */
    
    @EJB
    private PersonService ps;
    
    @EJB
    private AddressService as;
    
    public PersonCtrl() {
    }
    
    private Person newUser = new Person();
    private Address newAddress = new Address();

    public Person getNewUser() {
        return newUser;
    }

    public void setNewUser(Person newUser) {
        this.newUser = newUser;
    }

    public PersonService getPs() {
        return ps;
    }

    public void setPs(PersonService ps) {
        this.ps = ps;
    }

    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
    }


    public String registerUser() {
//        as.createNewAddress(newAddress);
//        newUser.setHome(as.createNewAddress(newAddress));
//        ps.createNewPerson(newUser);

//        long tempid = 
        
//        newAddress.setPersonid_(tempid);
        newAddress.setResident(ps.createNewPerson(newUser));
        as.createNewAddress(newAddress);
        return "login";
    }
    
    
    
}
