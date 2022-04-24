package com.example.schedulingApp.Entities;

import com.example.schedulingApp.ViewModel.DogAggression;
import com.example.schedulingApp.ViewModel.DogWeight;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "dogs",
        foreignKeys = {@ForeignKey(entity = EntityOwners.class,
                parentColumns = "ownerID", childColumns = "ownerID", onDelete = CASCADE)})

public class EntityDogs {

    @PrimaryKey(autoGenerate = true)
    private int dogID;
    private String dogName;
    private DogAggression dogAggression;
    private DogWeight dogWeight;
    private String dogBreed;
    private int ownerID;

    public EntityDogs(int dogID, String dogName, String dogBreed, DogAggression dogAggression, DogWeight dogWeight, int ownerID) {
        this.dogID = dogID;
        this.dogName = dogName;
        this.dogAggression = dogAggression;
        this.dogWeight = dogWeight;
        this.dogBreed = dogBreed;
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return "EntityDogs{" +
                "dogID=" + dogID +
                ", dogName='" + dogName + '\'' +
                ", dogAggression=" + dogAggression +
                ", dogWeight=" + dogWeight +
                ", dogBreed='" + dogBreed + '\'' +
                ", ownerID=" + ownerID +
                '}';
    }

    public int getDogID() {
        return dogID;
    }

    public void setDogID(int dogID) {
        this.dogID = dogID;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public DogAggression getDogAggression() {
        return dogAggression;
    }

    public void setDogAggression(DogAggression dogAggression) {
        this.dogAggression = dogAggression;
    }

    public DogWeight getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(DogWeight dogWeight) {
        this.dogWeight = dogWeight;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

}
