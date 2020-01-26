package com.diana.televizor.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.diana.televizor.Clase.Televizor;
import com.diana.televizor.Database.Dao.TelevizorDao;

@Database(entities = {Televizor.class}, exportSchema = false, version = 1)
@TypeConverters({DateConvertor.class})
public abstract class DatabaseModel extends RoomDatabase {
    private static final String DB_NAME = "db_name_televizor";
    private static DatabaseModel databaseModel;

    public static DatabaseModel getInstance(Context context) {
        if (databaseModel == null) {
            synchronized (DatabaseModel.class) {
                if (databaseModel == null) {
                    databaseModel = Room.databaseBuilder(context, DatabaseModel.class, DB_NAME)
                            .fallbackToDestructiveMigration().build();
                    return databaseModel;
                }
            }
        }

        return databaseModel;
    }

    public abstract TelevizorDao getTelevizorDao();
}
