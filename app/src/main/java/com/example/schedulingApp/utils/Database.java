package com.example.schedulingApp.utils;

import com.example.schedulingApp.DAO.DAOCredentials;
import com.example.schedulingApp.DAO.DAODogs;
import com.example.schedulingApp.DAO.DAOEmployees;
import com.example.schedulingApp.DAO.DAOOwners;
import com.example.schedulingApp.DAO.DAOServices;
import com.example.schedulingApp.Entities.EntityCredentials;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.Entities.EntityServices;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@androidx.room.Database(exportSchema = false, version = 1,
    entities = {EntityServices.class, EntityEmployees.class, EntityOwners.class, EntityDogs.class, EntityCredentials.class})

public abstract class Database extends  RoomDatabase{

    public static volatile Database dbInstance;

    public abstract DAOServices daoServices();
    public abstract DAOEmployees daoEmployees();
    public abstract DAOOwners daoOwners();
    public abstract DAODogs daoDogs();
    public abstract DAOCredentials daoCredentials();

    public static synchronized Database getDbInstance(Context context){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "wgu868.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }
}
