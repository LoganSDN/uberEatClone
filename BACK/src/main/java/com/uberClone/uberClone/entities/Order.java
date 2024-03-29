package com.uberClone.uberClone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOfOrder;
    private String status;
    private BigDecimal totalAmount;
    private Long susId;
    private Long resId;

    @NotNull
    private boolean driven = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsList = new ArrayList<>();

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateOfOrder=" + dateOfOrder +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", susId=" + susId +
                ", resId=" + resId +
                '}';
    }

    public Order(Long id, Date dateOfOrder, String status, BigDecimal totalAmount) {
        this.id = id;
        this.dateOfOrder = dateOfOrder;
        this.status = status;
        this.totalAmount = totalAmount;
    }
}
