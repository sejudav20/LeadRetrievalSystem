package com.example.conferencepro;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao()
public interface AccessDao {
    @Query("Select * from `Entrant Data`")
    LiveData<List<EntrantData>> getAllData();
    @Query("Select `Entrant Data`.*, ApplicantInfo.* from `Entrant Data` Inner Join ApplicantInfo ON `Entrant Data`.name=ApplicantInfo.userName")
    List<AllDataClass> getAllDataWithApplicantInfo();
    @Query("Select * from `ApplicantInfo` where userName=:userKey")
    List<ApplicantInfo> getApplicantData(String[] userKey);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ApplicantInfo af);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntrantData ed);


    @Query("Select * from `Entrant Data` Inner Join ApplicantInfo ON id=`Entrant Data`.name where name LIke :name")
    LiveData<List<AllDataClass>> getAllApplicantData(String name);
    @Query("Select * from `Entrant Data`  where name LIke :name")
    LiveData<List<EntrantData>> getSpecificEntrantData(String name);
    @Delete()
    void delete(EntrantData...ed);
    @Update
    void update(EntrantData...ed);



}
