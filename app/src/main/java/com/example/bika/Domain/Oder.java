package com.example.bika.Domain;

public class Oder {


private String orderId;

    private String total;

    private String adress;

    public String getOrderId() {
        return orderId;
    }


    public Oder(String orderId, String total, String adress) {
        this.orderId = orderId;
        this.total = total;
        this.adress = adress;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
