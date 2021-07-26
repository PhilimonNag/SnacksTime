package com.philimonnag.snackstime.Model;

public class Order {
    String FoodName,CustomerName,FoodPrice,FoodImg,PortionCount,Address,ContactNo,TotalPrice;


    public Order(String foodName, String customerName, String foodPrice, String foodImg, String portionCount, String address, String contactNo, String totalPrice) {
        FoodName = foodName;
        CustomerName = customerName;
        FoodPrice = foodPrice;
        FoodImg = foodImg;
        PortionCount = portionCount;
        Address = address;
        ContactNo = contactNo;
        TotalPrice = totalPrice;
    }

    public Order() {
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getFoodImg() {
        return FoodImg;
    }

    public void setFoodImg(String foodImg) {
        FoodImg = foodImg;
    }

    public String getPortionCount() {
        return PortionCount;
    }

    public void setPortionCount(String portionCount) {
        PortionCount = portionCount;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
