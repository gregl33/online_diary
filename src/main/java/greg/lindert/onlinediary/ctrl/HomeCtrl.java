
package greg.lindert.onlinediary.ctrl;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import greg.lindert.onlinediary.ents.Event;

/**
 *
 * @author up780016
 */
@Named(value = "homeCtrl")
@RequestScoped
public class HomeCtrl {

    private ScheduleEvent selectedevent = new DefaultScheduleEvent();

    private Event selectedEvent;
    
   
    public Event getSelectedEvent() {
        return selectedEvent;
        
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public HomeCtrl() {
    }

    public String goHome(){
       return "home?faces-redirect=true";
    }
    
    
    public void onEventSelect(SelectEvent selectEvent) {
       selectedEvent = (Event) ((ScheduleEvent) selectEvent.getObject()).getData();

    }


}
