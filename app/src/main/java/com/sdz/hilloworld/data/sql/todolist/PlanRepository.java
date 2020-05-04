package com.sdz.hilloworld.data.sql.todolist;

import android.content.Context;

import androidx.room.Database;

import com.sdz.hilloworld.data.sql.database.MyDatabase;

import java.util.List;

public class PlanRepository {
    public static int ID;
    private static List<Plan> list;
    private static PlanDao planDao;

    private static PlanRepository planRepositoryInstance;

    public static PlanRepository getInstance(Context context){
        if (planRepositoryInstance == null) {
            planRepositoryInstance = new PlanRepository(context);
            ID = maxID();
        }
        return planRepositoryInstance;
    }

    private static int maxID() {
        return planDao.maxID();
    }

    private PlanRepository(Context context) {
        MyDatabase myDatabase = MyDatabase.getInstance(context);
        planDao = myDatabase.planDao();
        list = planDao.getPlanList();
    }

    public List<Plan> getPlanList(){
        if(list == null){
            planDao.getPlanList();
        }
        return list;
    }

    public Plan getPlanById(int id){
        return planDao.getPlanById(id);
    }

    public void insertPlan(Plan plan){
        planDao.insertPlan(plan);
    }

    public void delete(int id){
        planDao.deletePlanById(id);
    }

    public void updateStat(boolean stat, int id){planDao.updateStat(stat,id);}
}
