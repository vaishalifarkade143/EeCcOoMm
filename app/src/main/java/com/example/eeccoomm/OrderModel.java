package com.example.eeccoomm;

public class OrderModel
{
    private String orderNumber;
    private String customerName;
    private String customerNumber;
    private String custemerCityName;
    private String customerAddress;
    private String itemExpence;
    private String deliveryCharges;
    private String orderTrackingNumber;
    private String curier;
    private String orderPlacingDate;
    private String orderStatus;

    private String  uid;

    public OrderModel() {
    }

    public OrderModel(String orderNumber, String customerName, String customerNumber, String custemerCityName, String customerAddress, String itemExpence, String deliveryCharges, String orderTrackingNumber, String curier, String orderPlacingDate, String orderStatus, String uid) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.custemerCityName = custemerCityName;
        this.customerAddress = customerAddress;
        this.itemExpence = itemExpence;
        this.deliveryCharges = deliveryCharges;
        this.orderTrackingNumber = orderTrackingNumber;
        this.curier = curier;
        this.orderPlacingDate = orderPlacingDate;
        this.orderStatus = orderStatus;
        this.uid = uid;
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustemerCityName() {
        return custemerCityName;
    }

    public void setCustemerCityName(String custemerCityName) {
        this.custemerCityName = custemerCityName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getItemExpence() {
        return itemExpence;
    }

    public void setItemExpence(String itemExpence) {
        this.itemExpence = itemExpence;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getOrderTrackingNumber() {
        return orderTrackingNumber;
    }

    public void setOrderTrackingNumber(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }

    public String getCurier() {
        return curier;
    }

    public void setCurier(String curier) {
        this.curier = curier;
    }

    public String getOrderPlacingDate() {
        return orderPlacingDate;
    }

    public void setOrderPlacingDate(String orderPlacingDate) {
        this.orderPlacingDate = orderPlacingDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}