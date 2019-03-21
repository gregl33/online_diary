/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import project.onlinediary.ents.Event;
import project.onlinediary.ents.Person;
import project.onlinediary.pers.EventFacade;

/**
 *
 * @author greg
 */
@Stateless
public class EventService {

    @EJB
    private EventFacade ef;
    
    
    public Event createEvent(Event e){
        ef.create(e);
        return e;
    }
    
    public List<Event> checkGuestAvailability(Event e){
  
        return ef.findByGuest(e.getStart_datetime(),e.getEnd_datetime(),e.getGuests());
    }
 
 
}
