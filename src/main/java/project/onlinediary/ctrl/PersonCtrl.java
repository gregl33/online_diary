/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
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
    
    private String prevusername;
    private boolean editMode;
    private DefaultScheduleModel eventModel;// = new DefaultScheduleModel();

        
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            this.setCurrentUser((Person) session.getAttribute("user"));          
        }
        
        System.out.println("***** EVENTS_O Current User: " + currentUser.getEvents_o().size());
        System.out.println("***** EVENTS Current User: " + currentUser.getEvents().size());

        ps.addpersontoent(currentUser);
        prevusername = currentUser.getUsername();
//  
       setEventModelEvents();
       
        System.out.println("*** EVENTS_O Current User: " + currentUser.getEvents_o().size());
        System.out.println("*** EVENTS Current User: " + currentUser.getEvents().size());

    }
    
    public PersonCtrl() {

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

    public DefaultScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(DefaultScheduleModel eventModel) {
        this.eventModel = eventModel;
    }



    public String updateUser() {
        if(checkUsernameFree()){
            ps.updatePerson(currentUser);
            as.updateAddress(currentUser.getHome());
            edit();
            prevusername = currentUser.getUsername();
           return "profile?faces-redirect=true";
        }else{
            return "";
        }
    }
    
    public String getUserDetails() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            this.setCurrentUser((Person) session.getAttribute("user"));          
        }
        System.out.println("EVENTS Curren User: " + currentUser.getEvents().size());

        return "profile?faces-redirect=true";
    }
    
    
    
    public String getCurrTime(){
        return java.time.LocalTime.now().toString();
    }
    
    
    
    public void setEventModelEvents(){
        eventModel = new DefaultScheduleModel();

        for (int i = 0; i < currentUser.getEvents().size(); i++) {
            DefaultScheduleEvent tempEvent = new DefaultScheduleEvent(
                    currentUser.getEvents().get(i).getEvent_name(), 
                    currentUser.getEvents().get(i).getStart_datetime(), 
                    currentUser.getEvents().get(i).getEnd_datetime(),
                    currentUser.getEvents().get(i));
            tempEvent.setDescription(currentUser.getEvents().get(i).getEvent_description());
           
            eventModel.addEvent(tempEvent);

        }
    }
    
    public boolean checkUsernameFree(){
      
        
        if(currentUser.getUsername().equalsIgnoreCase(prevusername)){
            return true;
        } else{
            if(!ps.checkUsername(currentUser.getUsername())){
            FacesContext.getCurrentInstance().
                    addMessage("editForm:username",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                    "Error!", "'" + currentUser.getUsername() + "' Username already exists!"));              
        }
        if(currentUser.getUsername().length() >= 1){
            return ps.checkUsername(currentUser.getUsername());
        }else{
            return false;
        }
        }
        
    }
}
