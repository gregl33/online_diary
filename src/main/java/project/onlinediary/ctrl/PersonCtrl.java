/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import project.onlinediary.bus.AddressService;
import project.onlinediary.bus.PersonService;
import project.onlinediary.ents.Person;



/**
 *
 * @author greg
 */
@ManagedBean(name = "personCtrl")
@SessionScoped
public class PersonCtrl {

    /**
     * Creates a new instance of PersonCtrl
     */
    
    @EJB
    private PersonService ps;// = new PersonService();
    
    @EJB
    private AddressService as;
    
    private Person currentUser = new Person();
    
    
    private boolean editMode;

        
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            this.setCurrentUser((Person) session.getAttribute("user"));          
        }
        ps.addpersontoent(currentUser);
        
    }
    
    public PersonCtrl() {
//        getUserDetails();
//        ps.addpersontoent(currentUser);
    }
        
        
    public void edit() {
        System.out.println("EDIT MODE");
        this.setEditMode(!this.editMode);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }


    

    public PersonService getPs() {
        return ps;
    }

    public void setPs(PersonService ps) {
        this.ps = ps;
    }

    
    

    public AddressService getAs() {
        return as;
    }

    public void setAs(AddressService as) {
        this.as = as;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }



    public String updateUser() {
        ps.updatePerson(currentUser);
        as.updateAddress(currentUser.getHome());
        edit();
       return "profile?faces-redirect=true";
    }
    
    public String getUserDetails() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            this.setCurrentUser((Person) session.getAttribute("user"));          
        }
        System.out.println("EVENTS Curren User: " + currentUser.getEvents().size());
//        currentUser.getEvents();
//        ps.addpersontoent(currentUser);
//                currentUser.getEvents();

        
        return "profile?faces-redirect=true";
    }
    
    
    

    
}
