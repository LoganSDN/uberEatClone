package com.uberClone.uberClone.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;


@Getter
@Entity
@Immutable
@Table(name = "driver_view")
public class DriverView implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String status;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "socket_id")
    private String socketId;


    @Override
    public String toString() {
        return "DriverView{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", ZIP='" + ZIP + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    private String street;
    private String city;
    private String ZIP;
    private Long lat;
    private Long lng;
}
