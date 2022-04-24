package com.example.schedulingApp.ViewModel;

import com.example.schedulingApp.Entities.EntityOwners;

public final class OwnerViewModel {
    public final int id;
    public final String name;

    public OwnerViewModel(int id, String name){
        this.id = id;
        this.name = name;
    }
    public OwnerViewModel(EntityOwners o){
        this.id = o.getOwnerID();
        this.name = o.getOwnerName();
    }
    @Override
    public String toString(){return name;}
}
