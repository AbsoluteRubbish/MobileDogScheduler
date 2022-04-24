package com.example.schedulingApp.Entities;

import com.example.schedulingApp.ViewModel.EmployeePosition;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employees")
public class EntityEmployees {

    @PrimaryKey(autoGenerate = true)
    private int employeeID;

    private String employeeName;
    private String employeePhone;
    private String employeeEmail;
    private EmployeePosition employeePosition;

    @Override
    public String toString() {
        return "EntityEmployees{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeePosition='" + employeePosition + '\'' +
                '}';
    }

    public EntityEmployees(int employeeID, String employeeName, EmployeePosition employeePosition, String employeePhone, String employeeEmail) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public EmployeePosition getEmployeePosition(){return employeePosition;}

    public void setEmployeePosition(EmployeePosition employeePosition){this.employeePosition = employeePosition;}

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
