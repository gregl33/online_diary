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
import project.onlinediary.bus.loginService;



/**
 *
 * @author greg
 */
@Named(value = "loginCtrlMain")
@RequestScoped
public class LoginCtrl {
    
    @EJB
    private loginService ls;
        
    private String username;
    private String password;
    
   
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
    
    public String gotoregisterpage(){
        System.out.println("HI");
        return "register";
    }
    
    
    	//validate login
	public String validateUser() {
                System.out.println("username: " + username);
                System.out.println("password: "+password);

		boolean valid = ls.validate(username, password);
                System.out.println("valid: " + valid);
		if (valid) {
//			HttpSession session = SessionUtils.getSession();
//			session.setAttribute("username", user);
			return "home";
		} else {
//			FacesContext.getCurrentInstance().addMessage(
//					null,
//					new FacesMessage(FacesMessage.SEVERITY_WARN,
//							"Incorrect Username and Passowrd",
//							"Please enter correct username and Password"));
                        
                  FacesContext.getCurrentInstance().addMessage("loginError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
                  
			return "login";
		}

        }
}
