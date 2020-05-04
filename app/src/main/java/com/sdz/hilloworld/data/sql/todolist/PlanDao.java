package com.sdz.hilloworld.data.sql.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlanDao {
    @Insert
    void insertPlan(Plan plan);

    @Delete
    void deletePlan(Plan plan);

    @Update
    void updatePlan(Plan plan);

    @Query("delete from 'plan' where id = :id")
    void deletePlanById(int id);

    @Query("select * from `plan`")
    List<Plan> getPlanList();

    @Query("select * from `plan` where id = :id")
    Plan getPlanById(int id);

    @Query("select max(id) from `plan` ")
    int maxID();

    @Query("update `plan` set stat=:stat where id = :id")
    void updateStat(boolean stat, int id);
}
