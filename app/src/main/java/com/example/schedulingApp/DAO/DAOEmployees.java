package com.example.schedulingApp.DAO;

import com.example.schedulingApp.Entities.EntityEmployees;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface DAOEmployees {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityEmployees entityEmployees);

    @Update
    void update(EntityEmployees entityEmployees);

    @Delete
    void delete(EntityEmployees entityEmployees);

    @Query("SELECT * FROM employees ORDER BY employeeID ASC")
    List<EntityEmployees> getAllEmployees();

    @Query("SELECT * FROM employees WHERE employeeID = :id LIMIT 1")
    EntityEmployees find(int id);

}
