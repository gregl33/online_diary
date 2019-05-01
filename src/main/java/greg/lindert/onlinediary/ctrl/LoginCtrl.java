
package greg.lindert.onlinediary.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import greg.lindert.onlinediary.bus.AddressService;
import greg.lindert.onlinediary.bus.PersonService;
import greg.lindert.onlinediary.bus.loginService;
import greg.lindert.onlinediary.ents.Address;
import greg.lindert.onlinediary.ents.Person;



/**
 *
 * @author up780016
 */
@Named(value = "loginCtrlMain")
@RequestScoped
public class LoginCtrl {
    
    @EJB
    private loginService ls;
    @EJB
    private PersonService ps;
    @EJB
    private AddressService as;
    
    
    private boolean editMode = true;

    private String username;
    private String password;
    
    private Person newUser = new Person();
    private Address newAddress = new Address();
   
    
    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
    public LoginCtrl() {
    }

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
    

    public Person getNewUser() {
        return newUser;
    }

    public void setNewUser(Person newUser) {
        this.newUser = newUser;
    }
    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
    }
    
        public String registerUser() {
        if(checkUsernameFree()){
                newUser.setHome(as.createNewAddress(newAddress));
                ps.createNewPerson(newUser);

                return "login?faces-redirect=true";
        }
        return "";
    }
        
	public String validateUser() {
               
		Person userLoggedin = ls.validate(username, password);

		if (userLoggedin != null) {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                    session.setAttribute("user", userLoggedin);	
                    
                    return "app/home?faces-redirect=true";
		} else {
//			
                  FacesContext.getCurrentInstance().addMessage("loginForm:username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect Username or Passowrd"));
                  FacesContext.getCurrentInstance().addMessage("loginForm:passwordInput", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Incorrect Username or Passowrd"));

			return "login";
		}

        }
        
        public String loggout(){
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if(session != null){
                session.removeAttribute("user");
                session.invalidate();
            }
            facesContext.getExternalContext().invalidateSession();
        
            
            return "/faces/login?faces-redirect=true";
        }
        
        public String gotoregisterpage(){
            return "register?faces-redirect=true";
    }
        
    public String gotologinPage(){
            return "login?faces-redirect=true";
    }
    
    public boolean checkUsernameFree(){
        if(!ps.checkUsername(newUser.getUsername())){
            FacesContext.getCurrentInstance().
                    addMessage("registerForm:username",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                    "Error!", "'" + newUser.getUsername() + "' Username already exists!"));              
        }
        if(newUser.getUsername().length() >= 1){
            return ps.checkUsername(newUser.getUsername());
        }else{
            return false;
        }
            
        
    }
    

}
