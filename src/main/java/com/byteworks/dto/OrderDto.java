package com.byteworks.dto;


public class OrderDto {

    private String meals;
    private Boolean officeDelivery;
    private Boolean cardPayment;
    private String shippingAddress;

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
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
}
