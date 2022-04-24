package com.example.schedulingApp.DAO;

import com.example.schedulingApp.Entities.EntityServices;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface DAOServices {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityServices entityServices);

    @Update
    void update(EntityServices entityServices);

    @Delete
    void delete(EntityServices entityServices);

    @Query("SELECT * FROM services ORDER BY serviceID ASC")
    List<EntityServices> getAllServices();
}
