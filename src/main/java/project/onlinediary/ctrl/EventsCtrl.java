/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import project.onlinediary.bus.EventService;
import project.onlinediary.ents.Event;
import project.onlinediary.ents.Person;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author greg
 */
//@Named(value = "eventsCtrl")
@ManagedBean(name = "eventsCtrl")
@ViewScoped
public class EventsCtrl implements Serializable {

    @EJB
    private EventService es;
    
    @ManagedProperty(value="#{personCtrl}") 
//    @Inject
    private PersonCtrl personCtrlBean;
      
      
      
    private Date minDate = new Date(System.currentTimeMillis() + 3600 * 1000);
    private Date todayDate = new Date();
//    private List<Person> newEventGuests = new ArrayList();
    
    
    private Event newEvent_ = new Event();
    
    
    public EventsCtrl() {
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(Date todayDate) {
        this.todayDate = todayDate;
    }

    public EventService getEs() {
        return es;
    }

    public void setEs(EventService es) {
        this.es = es;
    }

    public PersonCtrl getPersonCtrlBean() {
        return personCtrlBean;
    }

    public void setPersonCtrlBean(PersonCtrl personCtrlBean) {
        this.personCtrlBean = personCtrlBean;
    }


    public Event getNewEvent_() {
        return newEvent_;
    }

    public void setNewEvent_(Event newEvent_) {
        this.newEvent_ = newEvent_;
    }

    
    public String goEvents(){  
        return "events?faces-redirect=true";
    }
    
    public String goUpcomingEvents(){  
//        Person tt = personCtrlBean.getCurrentUser();
//        personCtrlBean.getPs().addpersontoent(tt);
//        personCtrlBean.getCurrentUser().getEvents();
        System.out.print("EVENTS: " + personCtrlBean.getCurrentUser().getEvents().size());
//        System.out.print("EVENTS O: " + personCtrlBean.getCurrentUser().getEvents_o().size());

        return "upcoming?faces-redirect=true";
    }
   
    
    public void addGuestToNewEvent(Person p){ 
        List<Person> t = this.newEvent_.getGuests();
        if(t.indexOf(p) == -1){
            this.newEvent_.getGuests().add(p);
        }
    }
    
    public void removeGuestFromNewEvent(Person p){
        if(this.newEvent_.getGuests().indexOf(p) == -1){
            this.newEvent_.getGuests().remove(p);
        }
    }
    
    public String createNewEvent(){
        newEvent_.setOwner(personCtrlBean.getCurrentUser());
        
        List<Event> guestsEvents = es.checkGuestAvailability(newEvent_);
//        List<Person> pp = personCtrlBean.getPs().checkGuestAvailability(newEvent_);
        
        System.out.print("EVENTS: " + guestsEvents.size());
        if(guestsEvents.isEmpty()){
            es.createEvent(newEvent_);
            personCtrlBean.getCurrentUser().getEvents().add(newEvent_);
            return "upcoming?faces-redirect=true";
        }else{
//            System.out.print("pp: " + pp.size());

            System.out.print("USER BUSY");
            
        }
        
       return "";
    }
    
}
