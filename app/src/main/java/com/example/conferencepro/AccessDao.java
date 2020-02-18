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
    @Query("Select * from `Entrant Data` Inner Join ApplicantInfo ON ApplicantInfo.userId=`Entrant Data`.userData")
    LiveData<List<AllDataClass>> getAllDataWithApplicantInfo();
    @Query("Select * from `ApplicantInfo` where userId=:userKey")
    List<ApplicantInfo> getApplicantData(Integer[] userKey);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntrantData ed,ApplicantInfo af);

    @Query("Select * from `Entrant Data` Inner Join ApplicantInfo ON id=`Entrant Data`.userData where name LIke :name")
    LiveData<List<AllDataClass>> getAllApplicantData(String name);
    @Query("Select * from `Entrant Data`  where name LIke :name")
    LiveData<List<EntrantData>> getSpecificEntrantData(String name);
    @Delete()
    void delete(EntrantData...ed);
    @Update
    void update(EntrantData...ed);



}
