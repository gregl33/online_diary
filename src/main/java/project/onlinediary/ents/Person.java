/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author greg
 */
@Entity
public class Person implements Serializable {


//    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ManyToMany(mappedBy = "guests",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Event> events;
    
    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Event> events_o;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String username = "";
    private String password;
    private String firstname;
    private String lastname;

    @OneToOne
    private Address home;
    
    private String phonenumber;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
        this.home = home;
    }

//    public List<Event> getEvents() {
//        return events;
//    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents_o() {
        return events_o;
    }

    public void setEvents_o(List<Event> events_o) {
        this.events_o = events_o;
    }
    
    public List<Event> getEvents() {
        List<Event> newList = new ArrayList<Event>(events);
        newList.addAll(events_o);
        Collections.sort(newList, new Comparator<Event>() {
            @Override
            public int compare(Event ev1, Event ev2) {
                return ev1.getStart_datetime().compareTo(ev2.getStart_datetime());
                   
            }
        });
        return newList;
    }
    
    public List<Event> getEventsG() {
        return events;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.onlinediary.ents.Person[ id=" + id + " ]";
    }
    
}
