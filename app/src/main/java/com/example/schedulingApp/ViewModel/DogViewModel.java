package com.example.schedulingApp.ViewModel;

import com.example.schedulingApp.Entities.EntityDogs;

public class DogViewModel {
    public final int id;
    public final String name;

    public DogViewModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public DogViewModel(EntityDogs d){
        this.id = d.getDogID();
        this.name = d.getDogName();
    }



    @Override
    public String toString(){return name;}
}
