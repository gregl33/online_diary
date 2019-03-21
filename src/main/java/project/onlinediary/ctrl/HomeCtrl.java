/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.ctrl;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author greg
 */
@Named(value = "homeCtrl")
@RequestScoped
public class HomeCtrl {

    /**
     * Creates a new instance of HomeCtrl
     */
    public HomeCtrl() {
        
    }

    public String goHome(){
       return "home?faces-redirect=true";
    }
}
