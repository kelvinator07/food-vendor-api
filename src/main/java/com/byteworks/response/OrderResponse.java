package com.byteworks.response;


public class OrderResponse {
    private Long id;
    private String responseMessage;
    private String totalOrderCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(String totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }
}
