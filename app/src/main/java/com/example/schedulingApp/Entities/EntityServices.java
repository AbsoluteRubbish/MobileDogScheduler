package com.example.schedulingApp.Entities;

import com.example.schedulingApp.ViewModel.ProductAddOns;
import com.example.schedulingApp.ViewModel.Products;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "services",
foreignKeys = {@ForeignKey(entity = EntityEmployees.class,
                parentColumns = "employeeID", childColumns = "employeeID", onDelete = CASCADE) })

public class EntityServices {

    @PrimaryKey(autoGenerate = true)
    private int serviceID;
    private String serviceDate;
    private String serviceTime;
    private String serviceNotes;

    private Products product;
    private ProductAddOns productAddOns;

    private int employeeID;
    private int dogID;

    @Override
    public String toString() {
        return "EntityServices{" +
                "serviceID=" + serviceID +
                ", serviceDate=" + serviceDate +
                ", serviceTime=" + serviceTime +
                ", orderNotes='" + serviceNotes + '\'' +
                ", product=" + product +
                ", productAddOns=" + productAddOns +
                ", employeeID=" + employeeID +
                ", dogID=" +dogID +
                '}';
    }

    public EntityServices(int serviceID, Products product, ProductAddOns productAddOns, String serviceDate, String serviceTime, String serviceNotes, int dogID, int employeeID) {
        this.serviceID = serviceID;
        this.product = product;
        this.productAddOns = productAddOns;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
        this.serviceNotes = serviceNotes;
        this.dogID = dogID;
        this.employeeID = employeeID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public ProductAddOns getProductAddOns() {
        return productAddOns;
    }

    public void setProductAddOns(ProductAddOns productAddOns) {this.productAddOns = productAddOns;}

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceTime(){return serviceTime;}

    public void setServiceTime(String serviceTime){this.serviceTime = serviceTime;}

    public String getServiceNotes() {
        return serviceNotes;
    }

    public void setServiceNotes(String serviceNotes) {
        this.serviceNotes = serviceNotes;
    }

    public int getDogID(){return dogID;}

    public void setDogID(int dogID){this.dogID = dogID;}

    public int getEmployeeID(){return employeeID;}

    public void setEmployeeID(int employeeID){this.employeeID = employeeID;}
}
