
package greg.lindert.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import greg.lindert.onlinediary.ents.Person;
import greg.lindert.onlinediary.pers.PersonFacade;

/**
 *
 * @author up780016
 */
@Stateless
public class loginService {

    @EJB
    private PersonFacade pf;
    
    public Person validate(String user, String password) {
		
        List<Person> p = pf.getPersonByUsernamePassword(user, pf.genHashPass(password));

        if (p.size() == 1) {

                p.get(0).getEvents();
                return p.get(0);
        }else{

            return null;
        }
    }
}
