/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author greg
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    private String event_name;
    private String event_description;
    private Date start_datetime;
    private Date end_datetime;

    @ManyToOne
    private Person owner;
    
    
    @ManyToMany
    private List<Person> guests;// = new ArrayList();

    public Event() {
        this.guests = new ArrayList<Person>();
    }

    
    
    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public Date getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(Date start_datetime) {
        this.start_datetime = start_datetime;
    }

    public Date getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(Date end_datetime) {
        this.end_datetime = end_datetime;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Person> getGuests() {
        return guests;
    }

    public void setGuests(List<Person> guests) {
        this.guests = guests;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.onlinediary.ents.Event[ id=" + id + " ]";
    }
    
}
