
package greg.lindert.onlinediary.ctrl;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import greg.lindert.onlinediary.bus.PersonService;
import greg.lindert.onlinediary.ents.Person;

/**
 *
 * @author up780016
 */
@ManagedBean(name = "searchCtrl")
@ViewScoped
public class SearchCtrl implements Serializable{

    public SearchCtrl() {
    }
    
    private List<Person> contactUsers;

    @EJB
    private PersonService ps;

    private Person selectedPerson;
    
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
    
    public void getAllPersons(){
        setContactUsers(ps.getAllPerson());

    }
    
    public String getAllUsers(){
        personCtrlBean.setEditMode(false);
        setContactUsers(ps.getAllPerson());
        return "contacts?faces-redirect=true";
    }
    
    
    public void searchForContactsAjax(AjaxBehaviorEvent event){       
        setContactUsers(ps.getPersonBy((String)((UIOutput)event.getSource()).getValue(),personCtrlBean.getCurrentUser().getId()));
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        personCtrlBean.setEditMode(false);
        this.selectedPerson = selectedPerson;
    }
    
//    public void setSelectedDocument(Person selectedp) {
//        this.selectedPerson = selectedp;
//    }
        
        
        
}
