/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.pers;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import project.onlinediary.ents.Event;
import project.onlinediary.ents.Person;

/**
 *
 * @author greg
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
    
}
