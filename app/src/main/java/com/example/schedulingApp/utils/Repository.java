package com.example.schedulingApp.utils;

import android.app.Application;

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

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Repository {
    private DAOOwners daoOwners;
    private DAOEmployees daoEmployees;
    private DAOServices daoServices;
    private DAODogs daoDogs;
    private DAOCredentials daoCredentials;

    private List<EntityEmployees> allEmployees;
    private List<EntityServices> allServices;
    private List<EntityOwners> allOwners;
    private List<EntityDogs> allDogs;
    private List<EntityCredentials> allCredentials;

    private static int NUMBER_OF_THREADS = 5;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
         Database db = Database.getDbInstance(application);
        daoOwners = db.daoOwners();
        daoEmployees = db.daoEmployees();
        daoServices = db.daoServices();
        daoDogs = db.daoDogs();
        daoCredentials = db.daoCredentials();
    }

    public EntityDogs findDogById(int id){
       return daoDogs.find(id);
    }

    public EntityEmployees findEmployeeById(int id){
        return daoEmployees.find(id);
    }

    //List
    public List<EntityOwners> getAllOwners(){
        dbExecutor.execute(()->{
            allOwners = daoOwners.getAllOwners();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allOwners;
    }
    public List<EntityServices> getAllServices(){
        dbExecutor.execute(()->{
            allServices = daoServices.getAllServices();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allServices;
    }
    public List<EntityEmployees> getAllEmployees(){
        dbExecutor.execute(()->{
            allEmployees = daoEmployees.getAllEmployees();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allEmployees;
    }
    public List<EntityDogs> getAllDogs(){
        dbExecutor.execute(()->{
            allDogs = daoDogs.getAllDogs();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allDogs;
    }
    public List<EntityCredentials> getAllCredentials(){
        dbExecutor.execute(()->{
            allCredentials = daoCredentials.getAllCredentials();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allCredentials;
    }

    //Clients
    public void insert(EntityOwners entityOwners){
        dbExecutor.execute(()->{
            daoOwners.insert(entityOwners);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityOwners entityOwners){
        dbExecutor.execute(()->{
            daoOwners.update(entityOwners);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityOwners entityOwners){
        dbExecutor.execute(()->{
            daoOwners.delete(entityOwners);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //orders
    public void insert(EntityServices entityServices){
        dbExecutor.execute(()->{
            daoServices.insert(entityServices);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityServices entityServices){
        dbExecutor.execute(()->{
            daoServices.update(entityServices);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityServices entityServices){
        dbExecutor.execute(()->{
            daoServices.delete(entityServices);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //employees
    public void insert(EntityEmployees entityEmployees){
        dbExecutor.execute(()->{
            daoEmployees.insert(entityEmployees);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityEmployees entityEmployees){
        dbExecutor.execute(()->{
            daoEmployees.update(entityEmployees);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityEmployees entityEmployees){
        dbExecutor.execute(()->{
            daoEmployees.delete(entityEmployees);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //dogs
    public void insert(EntityDogs entityDogs){
        dbExecutor.execute(()->{
            daoDogs.insert(entityDogs);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityDogs entityDogs){
        dbExecutor.execute(()->{
            daoDogs.update(entityDogs);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityDogs entityDogs){
        dbExecutor.execute(()->{
            daoDogs.delete(entityDogs);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //credentials
    public void insert(EntityCredentials entityCredentials){
        dbExecutor.execute(()->{
            daoCredentials.insert(entityCredentials);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityCredentials entityCredentials){
        dbExecutor.execute(()->{
            daoCredentials.update(entityCredentials);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityCredentials entityCredentials){
        dbExecutor.execute(()->{
            daoCredentials.delete(entityCredentials);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
