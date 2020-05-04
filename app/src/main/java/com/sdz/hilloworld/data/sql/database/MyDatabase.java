package com.sdz.hilloworld.data.sql.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.sdz.hilloworld.data.sql.todolist.DateCoverter;
import com.sdz.hilloworld.data.sql.todolist.Plan;
import com.sdz.hilloworld.data.sql.todolist.PlanDao;

@Database(entities = {Plan.class}, version = 1,exportSchema = false)
@TypeConverters(DateCoverter.class)
public abstract class MyDatabase extends RoomDatabase {

    public static final String  DATABASE_NAME = "hillo_world";

    private static MyDatabase databaseInstance;

    public static synchronized MyDatabase getInstance(Context context){
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MyDatabase.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    public abstract PlanDao planDao();

}
