
package greg.lindert.onlinediary.pers;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import greg.lindert.onlinediary.ents.Address;

/**
 *
 * @author up780016
 */
@Stateless
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "onlineDiary_persistance")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
    
    public List<Address> getAddressListByHosenameAndPostcode(String housename, String postcode) {
        Query q = em.createQuery("SELECT a From Address a WHERE a.postcode = :postcode AND  a.house_name = :housename");
        q.setParameter("postcode", postcode);
        q.setParameter("housename", housename);

        List<Address> addressList = q.getResultList();
        return addressList;    
    }
    
}
