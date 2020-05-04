package com.sdz.hilloworld.bridge.state;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sdz.hilloworld.data.sql.todolist.Plan;

import java.util.List;

public class ToDoListViewModel extends ViewModel {
    private  MutableLiveData<List<Plan>> ThingsLiveToDo;

    public MutableLiveData<List<Plan>> getThingsLiveToDo() {
        if(ThingsLiveToDo==null){
            ThingsLiveToDo = new MutableLiveData<>();
        }
        return ThingsLiveToDo;
    }
}
