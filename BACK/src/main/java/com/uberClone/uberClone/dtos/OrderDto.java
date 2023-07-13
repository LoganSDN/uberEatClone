package com.uberClone.uberClone.dtos;

import com.uberClone.uberClone.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String OrderID;

    private String status;

    Double latResto;

    Double lngResto;

    Double latUser;

    Double lngUser;


}
