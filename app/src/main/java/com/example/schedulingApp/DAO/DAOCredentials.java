package com.example.schedulingApp.DAO;

import com.example.schedulingApp.Entities.EntityCredentials;
import com.example.schedulingApp.Entities.EntityDogs;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOCredentials {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityCredentials entityCredentials);

    @Update
    void update(EntityCredentials entityCredentials);

    @Delete
    void delete (EntityCredentials entityCredentials);

    @Query("SELECT * FROM credentials ORDER BY UserID ASC")
    List<EntityCredentials> getAllCredentials();
}
