/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import project.onlinediary.ents.Event;

/**
 *
 * @author greg
 */
@Named(value = "homeCtrl")
@RequestScoped
public class HomeCtrl {

    private ScheduleEvent selectedevent = new DefaultScheduleEvent();

//        private Date todayDate = new Date();
    
   
        private Event selectedEvent;// = new Event(todayDate);
    
   
    public Event getSelectedEvent() {
        return selectedEvent;
        
    }

//    public ScheduleEvent getSelectedevent() {
//        return selectedevent;
//    }
//
//    public void setSelectedevent(ScheduleEvent selectedevent) {
//        this.selectedevent = selectedevent;
//    }
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public HomeCtrl() {
    }

    public String goHome(){
       return "home?faces-redirect=true";
    }
    
    
    public void onEventSelect(SelectEvent selectEvent) {
//        selectedEvent = (ScheduleEvent) selectEvent.getObject();
                selectedEvent = (Event) ((ScheduleEvent) selectEvent.getObject()).getData();

    }

//    public boolean checkEventLength(){
//        if(selectedevent != null && selectedevent.getStartDate() != null){
//        return !(selectedevent.getStartDate().getDay() == selectedevent.getEndDate().getDay()
//                && selectedevent.getStartDate().getMonth() == selectedevent.getEndDate().getMonth()
//                && selectedevent.getStartDate().getYear() == selectedevent.getEndDate().getYear());
//    }
//            return false;
//
//    }
}
