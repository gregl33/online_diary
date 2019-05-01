
package greg.lindert.onlinediary.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import greg.lindert.onlinediary.bus.EventService;
import greg.lindert.onlinediary.ents.Event;
import greg.lindert.onlinediary.ents.Person;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author up780016
 */

@ManagedBean(name = "eventsCtrl")
@ViewScoped
public class EventsCtrl implements Serializable {

     private ArrayList<String> daysArr = new ArrayList<String>(Arrays.asList("","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private ArrayList<String> monthsArr = new ArrayList<String>(Arrays.asList("January", "February", "March", "April", "May", "June",
"July", "August", "September", "October", "November", "December"));

    
    @EJB
    private EventService es;
    
    @ManagedProperty(value="#{personCtrl}") 

    private PersonCtrl personCtrlBean;
      
    private boolean editEvent = false;
          
    
    private boolean viewMode = true;

    private Date todayDate = new Date();
    
    private Event newEvent_ = new Event(todayDate);
    private List<Person> newEvent_busyUsers = new ArrayList<>();


    public EventsCtrl() {
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

    public boolean isEditEvent() {
        return editEvent;
    }

    public void setEditEvent(boolean editEvent) {
        this.editEvent = editEvent;
    }


    public Event getNewEvent_() {
        return newEvent_;
    }

    public void setNewEvent_(Event newEvent_) {
        this.newEvent_ = newEvent_;
    }

    public boolean isViewMode() {
        return viewMode;
    }

    public void setViewMode(boolean viewMode) {
        this.viewMode = viewMode;
    }

        public void changeViewMode() {
        this.setViewMode(!this.viewMode);
    }

    
    public String goUpcomingEvents(){  
        return "upcoming?faces-redirect=true";
    }
   
    
    public void addGuestToNewEvent(Person p){ 
        List<Person> t = newEvent_.getGuests();
        if(t.indexOf(p) == -1){
            newEvent_.getGuests().add(p);
        }
    }
    
    public void removeGuestFromNewEvent(Person p){        
        if(newEvent_.getGuests().indexOf(p) != -1){
            newEvent_.getGuests().remove(p);
        }
    }
    
    private void deleteLocalEvent(){
       if(personCtrlBean.getCurrentUser().getEvents_o().indexOf(newEvent_) != -1){
            personCtrlBean.getCurrentUser().getEvents_o().remove(newEvent_);
        }
        
        if(personCtrlBean.getCurrentUser().getEventsG().indexOf(newEvent_) != -1){
            personCtrlBean.getCurrentUser().getEventsG().remove(newEvent_);
        }  

    }
    private void addLocalEvent(){
        deleteLocalEvent();
            List<Event> tempOEvents = personCtrlBean.getCurrentUser().getEvents_o();
            tempOEvents.add(newEvent_);
            personCtrlBean.getCurrentUser().setEvents_o(tempOEvents);
            personCtrlBean.setEventModelEvents();
    }
    
    public String deleteEvent(String redir){
        
       newEvent_ =  es.deleteEvent(newEvent_);
        
       deleteLocalEvent();
        
        personCtrlBean.setEventModelEvents();

        
        return "";

    }
    
    
    public String editEvent(String redir){

        newEvent_ = es.updateEvent(newEvent_);
        addLocalEvent();
      
        return redir + "?faces-redirect=true";
    }
    
    
    public String createNewEvent(String redir){
        newEvent_.setOwner(personCtrlBean.getCurrentUser());
        
        List<Event> guestsEvents = new ArrayList<Event>();
        
        if(!newEvent_.getGuests().isEmpty()){
            guestsEvents = es.checkGuestAvailability(newEvent_);
        }
        
        if(guestsEvents.isEmpty()){
            newEvent_ = es.createEvent(newEvent_);
            addLocalEvent();

            return redir+"?faces-redirect=true";

        }else{
            newEvent_busyUsers = new ArrayList<>();
            for (Event temp : guestsEvents) {
                Person e_owner = temp.getOwner();
                
                
                List<Person> e_guests = temp.getGuests();
                
                if(newEvent_.getGuests().contains(e_owner)){
                    if(!newEvent_busyUsers.contains(e_owner)){
                        newEvent_busyUsers.add(e_owner);
                    }
                }
//                }else{
                    for (Person temp_p : newEvent_.getGuests()) {
                        if(e_guests.contains(temp_p)){
                            if(!newEvent_busyUsers.contains(temp_p)){
                                newEvent_busyUsers.add(temp_p);
                            }
                        }
                    }                
            }
            
        }
        
       return "";
    }
    
    
    
    
    public void setEnd_datetimeAjax(AjaxBehaviorEvent event){
     
        Date tempt = (Date) ((UIOutput)event.getSource()).getValue();
        
        newEvent_.setEnd_datetime(new Date(tempt.getTime() + 3600 * 1000));
             
        
    }
    
    
    
    public void onDateSelect (SelectEvent selectEvent) {
        Date selected_date = (Date) selectEvent.getObject();
        
        
        Date startdate = (Date) selected_date.clone();

        if(selected_date.getHours() == 0 && selected_date.getMinutes() == 0){
            Date temp = new Date(System.currentTimeMillis());
            startdate.setHours(temp.getHours());
            startdate.setMinutes(temp.getMinutes());
            startdate.setSeconds(temp.getSeconds()); 
        }


        Date endDate = (Date) selected_date.clone();
        endDate.setTime(startdate.getTime() + 3600 * 1000);
    
        newEvent_ = new Event();
        newEvent_.setStart_datetime(startdate);
        newEvent_.setEnd_datetime(endDate);

        
        todayDate = (Date) startdate.clone();
        editEvent = false;
        viewMode = false;
         
            
    }
    
    
    public void onEventSelect(SelectEvent selectEvent) {
        newEvent_ = (Event) ((ScheduleEvent) selectEvent.getObject()).getData();
        editEvent = true;
        
    }
    
    public ArrayList<String> getDaysArr() {
        return daysArr;
    }

    public void setDaysArr(ArrayList<String> daysArr) {
        this.daysArr = daysArr;
    }

    public ArrayList<String> getMonthsArr() {
        return monthsArr;
    }

    public void setMonthsArr(ArrayList<String> monthsArr) {
        this.monthsArr = monthsArr;
    }
    
    
    public String getDayOfWeek(int day) {
        return daysArr.get(day);
    }
    public String getMonth(int month) {
        return monthsArr.get(month);
    }
    
    
    public boolean checkEventLength(){
        if(newEvent_ != null && newEvent_.getStart_datetime()!= null){
        return !(newEvent_.getStart_datetime().getDay() == newEvent_.getEnd_datetime().getDay()
                && newEvent_.getStart_datetime().getMonth() == newEvent_.getEnd_datetime().getMonth()
                && newEvent_.getStart_datetime().getYear() == newEvent_.getEnd_datetime().getYear());
    }
            return false;

    }
    
    
    
     public void handleClose(CloseEvent event) {
        
         this.setViewMode(true);
         newEvent_ = new Event(todayDate);
         
         
    }
     

     
    public boolean checkIfUserInvited(Person p) {
        return !newEvent_.getGuests().contains(p);
    }
     

    private Event findnextEventToday(){
        for (Event event : (personCtrlBean.getCurrentUser().getEvents())) {
             if(event.getStart_datetime().after(todayDate)){
                 if(event.getStart_datetime().getDate() == todayDate.getDate()
                         && event.getStart_datetime().getMonth() == todayDate.getMonth()
                          && event.getStart_datetime().getYear() == todayDate.getYear()){
                     return event;
                 }
             }
         }
         return null;
    }
    
     public String nextEventToday() {
         Event temp = findnextEventToday();
         if(temp != null){
             return temp.getEvent_name();
         }else{
             return "";
         }
             
     }
    
    public String timeTillnextEventToday() {
        Event temp = findnextEventToday();
        if(temp != null){
            long time = temp.getStart_datetime().getTime() - todayDate.getTime();

    //        long diffSeconds = time / 1000 % 60;
            long diffMinutes = time / (60 * 1000) % 60;
            long diffHours = time / (60 * 60 * 1000);

            if(diffHours <= 0 ){
                return diffMinutes + " Minutes";
            }else if(diffHours >= 1 && diffMinutes <= 0){
                return diffHours + " Hours";
            }else{
                return diffHours + " Hours and " + diffMinutes + " Minutes";
            }   
        }
        return "";
               
    } 
    public boolean checkIfOwner() {
        if(newEvent_ != null && newEvent_.getOwner() != null){
            return newEvent_.getOwner().equals(personCtrlBean.getCurrentUser());
        }
        return true;
    }
    
        public boolean newEventBusyUsers(Person p){
            return newEvent_busyUsers.contains(p);
        }
        
        public boolean anyBusyusers(){
            return !newEvent_busyUsers.isEmpty();
        }

        public String getBusyUserMsg(){
            String err = "";
            if(anyBusyusers()){
                err = "Following users are busy during this event: \n";
                for(Person p : newEvent_busyUsers){
                    err += "'" + p.getFirstname() + " " + p.getLastname() + "', ";
                }
            }
            err = err.substring(0, err.length() - 2);
            return err;
        }
}
