package com.example.schedulingApp.DAO;

import com.example.schedulingApp.Entities.EntityOwners;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOOwners {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityOwners entityOwners);


    @Update
    void update(EntityOwners entityOwners);

    @Delete
    void delete(EntityOwners entityOwners);

    @Query("SELECT * FROM owners ORDER BY ownerID ASC")
    List<EntityOwners> getAllOwners();
}
