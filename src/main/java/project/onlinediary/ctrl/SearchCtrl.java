/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import project.onlinediary.bus.PersonService;
import project.onlinediary.ents.Person;

/**
 *
 * @author greg
 */
@ManagedBean(name = "searchCtrl")
@ViewScoped
public class SearchCtrl implements Serializable{

    public SearchCtrl() {
    }
    
    private List<Person> contactUsers;

    @EJB
    private PersonService ps;

    
    @ManagedProperty(value="#{personCtrl}") 
    private PersonCtrl personCtrlBean;
        
    public List<Person> getContactUsers() {
        return contactUsers;
    }

    public void setContactUsers(List<Person> contactUsers) {
        this.contactUsers = contactUsers;
    }

    public PersonService getPs() {
        return ps;
    }

    public void setPs(PersonService ps) {
        this.ps = ps;
    }

    public PersonCtrl getPersonCtrlBean() {
        return personCtrlBean;
    }

    public void setPersonCtrlBean(PersonCtrl personCtrlBean) {
        this.personCtrlBean = personCtrlBean;
    }
    
    
    
    public String getAllUsers(){
        this.setContactUsers(ps.getAllPerson());
        return "contacts?faces-redirect=true";
    }
    
    
    public void searchForContactsAjax(AjaxBehaviorEvent event){       
        this.setContactUsers(ps.getPersonBy((String)((UIOutput)event.getSource()).getValue(),personCtrlBean.getCurrentUser().getId()));
    }
}
