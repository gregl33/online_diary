/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.onlinediary.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import project.onlinediary.ents.Address;
import project.onlinediary.ents.Person;
import project.onlinediary.pers.AddressFacade;

/**
 *
 * @author greg
 */
@Stateless
public class AddressService {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private AddressFacade af;
    
        public Address createNewAddress(Address a) { //throws BusinessException {
            //check things: duplicates
    //        if (pf.findPersonByUsername(p.getUsername()).isEmpty()) {
            List<Address> existing_address = af.getAddressListByHosenameAndPostcode(a.getHouse_name(), a.getPostcode());
            if(existing_address.isEmpty()){
                af.create(a);
                return a;
            } else {
    //            raise problem
    //            throw new BusinessException("Duplicated name: " + p.getName());
                return existing_address.get(0);
            }
        }
        
        public Address updateAddress(Address a) {
            af.edit(a);
            return a;
        }
}
