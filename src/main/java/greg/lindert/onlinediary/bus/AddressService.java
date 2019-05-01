
package greg.lindert.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import greg.lindert.onlinediary.ents.Address;
import greg.lindert.onlinediary.pers.AddressFacade;

/**
 *
 * @author up780016
 */
@Stateless
public class AddressService {

    @EJB
    private AddressFacade af;
    
    /**
     * Creates a New Address with users inputted details 
     * @param newAddress Users new Address Object
     * @return Newly created Address Object
     */
    public Address createNewAddress(Address newAddress) {

            List<Address> existing_address = af.getAddressListByHosenameAndPostcode(newAddress.getHouse_name(), newAddress.getPostcode());
            if(existing_address.isEmpty()){
                af.create(newAddress);
                return newAddress;
            } else {
                return existing_address.get(0);
            }
        }
        
    /**
     * Updates a users Address details 
     * @param updatedAddress Users updated Address object
     * @return Updated Address Object 
     */
    public Address updateAddress(Address updatedAddress) {
            af.edit(updatedAddress);
            return updatedAddress;
        }
}
