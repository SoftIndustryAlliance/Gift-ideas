package com.soft_industry.findgift.data.datasources.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.soft_industry.findgift.data.dto.DBPopulator;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by user on 4/20/18.
 */
@Dao
public interface PopulationDao {

    @Query("SELECT * from db_population")
    Single<List<DBPopulator>> start();
    @Query("SELECT * from db_population")
    Flowable<List<DBPopulator>> observe();

    @Insert
    void setPopulated(DBPopulator populator);


}
