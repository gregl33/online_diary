
package greg.lindert.onlinediary.pers;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import greg.lindert.onlinediary.ents.Event;
import greg.lindert.onlinediary.ents.Person;

/**
 *
 * @author up780016
 */
@Stateless
public class EventFacade extends AbstractFacade<Event> {

    @PersistenceContext(unitName = "onlineDiary_persistance")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }
    

    
       public List<Event> findByGuest (Date startdate_, Date enddate_, List<Person> guestList_) {
        Query q = em.createQuery("SELECT e FROM Event e "
                + "WHERE e.guests IN :guestList "
                + "AND (:startdate BETWEEN e.start_datetime AND e.end_datetime) "
                + "OR (:enddate BETWEEN e.start_datetime AND e.end_datetime)" );
       
        q.setParameter("startdate", startdate_);
        q.setParameter("enddate", enddate_);
        q.setParameter("guestList", guestList_);

        List<Event> evetnsFound = q.getResultList();
        return evetnsFound;
    }
       
       
       public List<Event> findOwnerByGuestId(Person p){
           Query q = em.createQuery("SELECT e FROM Event e WHERE e.owner = :guest");
           q.setParameter("guest", p);

            List<Event> evetnsFound = q.getResultList();
        return evetnsFound;

       }
       
       
        public List<Event> findByGuestId(Person p){
           Query q = em.createQuery("SELECT e FROM Event e WHERE :guest MEMBER OF e.guests");
           q.setParameter("guest", p);

            List<Event> evetnsFound = q.getResultList();
        return evetnsFound;

       }
    
}
