package com.sdz.hilloworld.data.sql.todolist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "plan")
public class Plan implements Serializable{

    public static final long serialVersionUID = 1L;

    public Plan() {

    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name="title", typeAffinity = ColumnInfo.TEXT)
    public String title;

    @ColumnInfo(name="date")
    public Date date;

    @ColumnInfo(name = "stat")
    public boolean stat;

    @Ignore
    public Plan(String title, Date date) {
        this.stat = false;
        this.id = ++PlanRepository.ID;
        this.title = title;
        this.date = date;
    }
}
