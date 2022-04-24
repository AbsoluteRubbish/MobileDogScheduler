package com.example.schedulingApp.ViewModel;

import com.example.schedulingApp.Entities.EntityEmployees;

public class EmployeeViewModel {
    public final int id;
    public final String name;

    public EmployeeViewModel(int id, String name){
        this.id = id;
        this.name = name;
    }
    public EmployeeViewModel(EntityEmployees o){
        this.id = o.getEmployeeID();
        this.name = o.getEmployeeName();
    }
    @Override
    public String toString(){return name;}
}
