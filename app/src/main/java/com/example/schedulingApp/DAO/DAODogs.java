package com.example.schedulingApp.DAO;

import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityOwners;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAODogs {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityDogs entityDogs);

    @Update
    void update(EntityDogs entityDogs);

    @Delete
    void delete (EntityDogs entityDogs);

    @Query("SELECT * FROM dogs ORDER BY dogID ASC")
    List<EntityDogs> getAllDogs();

    @Query("SELECT * FROM dogs WHERE dogID = :id LIMIT 1")
    EntityDogs find(int id);
}
