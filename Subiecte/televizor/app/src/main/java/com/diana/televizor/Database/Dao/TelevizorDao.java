package com.diana.televizor.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.diana.televizor.Clase.Televizor;

import java.util.List;

@Dao
public interface TelevizorDao {
    @Query("select * from televizoare")
    List<Televizor> getAll();

    @Insert
    long insertTelevizor(Televizor televizor);

    @Update
    int updateTelevizor(Televizor televizor);

    @Delete
    int deleteTelevizor(Televizor televizor);
}
