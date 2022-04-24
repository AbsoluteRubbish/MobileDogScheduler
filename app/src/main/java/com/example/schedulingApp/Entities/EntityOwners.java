package com.example.schedulingApp.Entities;

import com.example.schedulingApp.ViewModel.DogWeight;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "owners")
public class EntityOwners {

    @PrimaryKey(autoGenerate = true)
    private int ownerID;

    private String ownerName;
    private String ownerAddress;
    private String ownerPhone;
    private String ownerEmail;

    public EntityOwners(int ownerID, String ownerName, String ownerAddress, String ownerPhone, String ownerEmail) {
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.ownerPhone = ownerPhone;
        this.ownerEmail = ownerEmail;

    }

    @Override
    public String toString() {
        return  ownerName;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

}


