package com.nsh.customerservice.services;

import com.nsh.customerservice.dtos.CustomerDto;
import com.nsh.customerservice.dtos.address.AddressDto;
import com.nsh.customerservice.entity.Customer;
import com.nsh.customerservice.entity.address.Address;
import com.nsh.customerservice.util.CustomBeanUtils;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
@Mapper
@Component
public interface AddressMapper {
    static Address dtoToAddress(AddressDto addDto){
        Address.AddressBuilder address=Address.builder();

        address.pin(addDto.getPin());
        address.city(addDto.getCity());
        address.state(addDto.getState());
        address.country(addDto.getCountry());
        address.address(addDto.getAddress());
        return address.build();
    }
    static AddressDto addressToDto(Address add){
        AddressDto.AddressDtoBuilder addDto=AddressDto.builder();

        addDto.pin(add.getPin());
        addDto.city(add.getCity());
        addDto.state(add.getState());
        addDto.country(add.getCountry());
        addDto.address(add.getAddress());
        return addDto.build();
    }
//        Address dtoToAddress(AddressDto addressDto);
//
//        AddressDto addressToDto(Address address);
    }

