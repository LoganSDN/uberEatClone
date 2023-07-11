package com.uberClone.uberClone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
@Table(name = "delivery")
@Getter
@ToString
@NoArgsConstructor
@Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @NotNull
    @JoinColumn(name = "deliver_user_id", referencedColumnName = "id")
    private User user;

    private Long latitude;
    private Long longitude;

    private String status;

    private Date lastDelivery;

    @OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL)
    private Order order;
}
