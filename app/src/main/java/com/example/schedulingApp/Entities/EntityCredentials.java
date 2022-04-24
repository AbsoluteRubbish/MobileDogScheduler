package com.example.schedulingApp.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "credentials",
        foreignKeys = {@ForeignKey(entity = EntityEmployees.class,
        parentColumns = "employeeID", childColumns = "employeeID", onDelete = CASCADE)})
public class EntityCredentials {

    @PrimaryKey(autoGenerate = true)
    private int UserID;
    private String UserName;
    private String UserPassword;
    private int employeeID;

    public EntityCredentials(int UserID, String UserName, String UserPassword, int employeeID){
        this.UserID = UserID;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
        this.employeeID = employeeID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        employeeID = employeeID;
    }
}
