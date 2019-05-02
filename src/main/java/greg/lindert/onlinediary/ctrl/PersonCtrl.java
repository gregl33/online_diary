
package greg.lindert.onlinediary.ctrl;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import greg.lindert.onlinediary.bus.AddressService;
import greg.lindert.onlinediary.bus.EventService;
import greg.lindert.onlinediary.bus.PersonService;
import greg.lindert.onlinediary.ents.Address;
import greg.lindert.onlinediary.ents.Person;



/**
 *
 * @author up780016
 */
@ManagedBean(name = "personCtrl")
@SessionScoped
public class PersonCtrl {

    
    @EJB
    private PersonService ps;
    
    @EJB
    private AddressService as;
    
    @EJB
    private EventService es;
        
        
    private Person currentUser = new Person();
    
    private String prevusername;
    private boolean editMode;
    private DefaultScheduleModel eventModel;

    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            this.setCurrentUser((Person) session.getAttribute("user"));
        }

        this.currentUser.getEventsG().clear();
        this.currentUser.getEvents_o().clear();

        this.currentUser.setEvents(es.getByGuestId(currentUser));
        this.currentUser.setEvents_o(es.getOwnerByGuestId(currentUser));

        ps.addpersontoent(currentUser);
        prevusername = currentUser.getUsername();

        setEventModelEvents();
       

    }
    
    public PersonCtrl() {

    }
        
        
    public void edit() {
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
    
    
    public void CreateTestUser(){
        if(ps.checkUsername("user")){
            Person tempPerson = new Person();      
            tempPerson.setEmail("user.test@gmail.com");
            tempPerson.setFirstname("User");
            tempPerson.setLastname("Test");
            tempPerson.setPassword("user");
            tempPerson.setUsername("user");
            tempPerson.setPhonenumber("01234567891");

            Address tempAddress = new Address();
            tempAddress.setAddress_line_1("Skyline Road");
            tempAddress.setAddress_line_2("");
            tempAddress.setHouse_name("1");
            tempAddress.setCounty("Hampshire");
            tempAddress.setTown("Portsmouth");
            tempAddress.setPostcode("PO12 2RW");

            tempAddress = as.createNewAddress(tempAddress);
            tempPerson.setHome(tempAddress);

            ps.createNewPerson(tempPerson);
        }
    }
}
