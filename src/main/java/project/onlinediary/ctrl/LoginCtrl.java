/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import project.onlinediary.bus.AddressService;
import project.onlinediary.bus.PersonService;
import project.onlinediary.bus.loginService;
import project.onlinediary.ents.Address;
import project.onlinediary.ents.Person;



/**
 *
 * @author greg
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
   
    
    
//    public void edit() {
//        System.out.println("EDIT MODE");
//        this.setEditMode(!this.editMode);
//    }

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
//          as.createNewAddress(newAddress);
//        newUser.setHome(as.createNewAddress(newAddress));
//        ps.createNewPerson(newUser);
//          newUser.setHome(as.createNewAddress(newAddress));
//        long tempid = 

//        
        if(checkUsernameFree()){
                newUser.setHome(as.createNewAddress(newAddress));
                ps.createNewPerson(newUser);
        //        newAddress.setPersonid_(tempid);
        //        newAddress.setResident(ps.createNewPerson(newUser));
        //        as.createNewAddress(newAddress);

                return "login?faces-redirect=true";
        }
        return "";
    }
        
    	//validate login
	public String validateUser() {
                System.out.println("username: " + username);
                System.out.println("password: "+password);

//		boolean valid = ls.validate(username, password);
		Person userLoggedin = ls.validate(username, password);

//                System.out.println("valid: " + valid);
		if (userLoggedin != null) {
//			HttpSession session = SessionUtils.getSession();
//			session.setAttribute("username", user);

                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                    session.setAttribute("user", userLoggedin);	
                    
                    return "app/home?faces-redirect=true";
		} else {
//			FacesContext.getCurrentInstance().addMessage(
//					null,
//					new FacesMessage(FacesMessage.SEVERITY_WARN,
//							"Incorrect Username and Passowrd",
//							"Please enter correct username and Password"));
                        
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
//            this.edit();
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
            
        
            
//        return ps.checkUsername(newUser.getUsername());
    }
    

}
