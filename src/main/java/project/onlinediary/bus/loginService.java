/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import project.onlinediary.ents.Person;
import project.onlinediary.pers.PersonFacade;

/**
 *
 * @author greg
 */
@Stateless
public class loginService {

    @EJB
    private PersonFacade pf;
    
    public Person validate(String user, String password) {
		
        List<Person> p = pf.getPersonByUsernamePassword(user, pf.genHashPass(password));

        if (p.size() == 1) {
                //result found, means valid inputs
//                return true;
                return p.get(0);
        }else{

            return null;
        }
    }
}
