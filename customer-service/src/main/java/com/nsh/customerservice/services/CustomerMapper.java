package com.nsh.customerservice.services;

import com.nsh.customerservice.dtos.CustomerDto;
import com.nsh.customerservice.entity.Customer;
import com.nsh.customerservice.util.CustomBeanUtils;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;
@Mapper
@Component
public interface CustomerMapper {
    static Customer dtoToCustomer(CustomerDto customerDto,String id){
        Customer.CustomerBuilder customer=Customer.builder();
        customer.keyCloakUserId(id);
        customer.enabled(true);
        customer.firstName( customerDto.getFirstName() );
        customer.lastName( customerDto.getLastName() );
        customer.password( customerDto.getPassword() );
        customer.email( customerDto.getEmail() );
        customer.emailVerified(false);
        customer.address(AddressMapper.dtoToAddress(customerDto.getAddress()));
        return customer.build();
    }
    static Customer updateEntity(Customer customer, CustomerDto customerDto){
try {
    CustomBeanUtils.copyPropertiesNotNull(customer,customerDto);
} catch (InvocationTargetException | IllegalAccessException e) {
    throw new RuntimeException(e);
}
return customer;
    }

    Customer dtoToCustomer(CustomerDto customerDto);
    
    CustomerDto customerToDto(Customer customer);
}
