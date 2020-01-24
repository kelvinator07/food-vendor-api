package com.byteworks.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kelvin
 *
 */
@Entity
@Table(name = "order_")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Meal> meals = new ArrayList<>();
    private Boolean officeDelivery;
    private Boolean cardPayment;
    private String shippingAddress;
    private Date orderDate;
    private double orderTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Boolean getOfficeDelivery() {
        return officeDelivery;
    }

    public void setOfficeDelivery(Boolean officeDelivery) {
        this.officeDelivery = officeDelivery;
    }

    public Boolean getCardPayment() {
        return cardPayment;
    }

    public void setCardPayment(Boolean cardPayment) {
        this.cardPayment = cardPayment;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void add(Meal meal) {

        if (meals == null) {
            meals = new ArrayList<>();
        }

        meals.add(meal);

        meal.setOrder(this);

    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", meals=" + meals.toString() +
                ", officeDelivery=" + officeDelivery +
                ", cardPayment=" + cardPayment +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", orderDate=" + orderDate +
                ", orderTotal=" + orderTotal +
                '}';
    }
}
