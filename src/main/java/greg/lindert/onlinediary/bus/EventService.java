
package greg.lindert.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import greg.lindert.onlinediary.ents.Event;
import greg.lindert.onlinediary.ents.Person;
import greg.lindert.onlinediary.pers.EventFacade;

/**
 *
 * @author up780016
 */
@Stateless
public class EventService {

    @EJB
    private EventFacade ef;
    
    /**
     * Creates a new event 
     * @param e Events details object to be Created 
     * @return Created Event 
     */
    public Event createEvent(Event e){
        ef.create(e);
        return e;
    }
    
    /**
     * Updates an event 
     * @param e Event to be Updated 
     * @return Updated Event 
     */
    public Event updateEvent(Event e){
        ef.edit(e);
        return e;
    }
    
    /**
     * Deletes an event 
     * @param e Event to be Deleted 
     * @return Deleted Event 
     */
    public Event deleteEvent(Event e){
        ef.remove(e);
        return e;
    }
    
     /**
     * Checks invited guests availability on create of new event
     * @param e new Events details  
     * @return List of Events for invited guests that are busy 
     */
    public List<Event> checkGuestAvailability(Event e){
        return ef.findByGuest(e.getStart_datetime(),e.getEnd_datetime(),e.getGuests());
    }
    
    /**
     * Gets events that the user is an guest off
     * @param p Person object that is the guest of events 
     * @return List of Events that the person is an guest off 
     */
    public List<Event> getByGuestId(Person p){
        return ef.findByGuestId(p);
    }
    
    /**
     * Gets events that the user is an owner off
     * @param p Person object that is the owner of events 
     * @return List of Events that the person is an owner off
     */
    public List<Event> getOwnerByGuestId(Person p){
        return ef.findOwnerByGuestId(p);
    }
 
}
