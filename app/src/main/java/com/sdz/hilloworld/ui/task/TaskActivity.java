package com.sdz.hilloworld.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sdz.hilloworld.R;
import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.data.sql.todolist.Plan;
import com.sdz.hilloworld.data.sql.todolist.PlanRepository;

public class TaskActivity extends AppCompatActivity {


    Button back;
    CheckBox selected;
    TextView content;

    Plan mPlan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().hide();
        init();
        setListener();
    }

    private void init() {
        back = findViewById(R.id.btn_back_to_task);
        selected = findViewById(R.id.radio_task_completed);
        content = findViewById(R.id.et_task_content);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("task");
//        String content = bundle.getString("content");
//        isChecked = bundle.getBoolean("isChecked",false);
        mPlan = (Plan)bundle.getSerializable("data");
        ((TextView)findViewById(R.id.et_task_content)).setText(mPlan.title);
        if(mPlan.stat){
            content.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mPlan.stat= true;
            selected.setChecked(true);
            AsyncTask.getInstance().postNormalTask(()->{
                PlanRepository.getInstance(getApplication()).updateStat(mPlan.stat,mPlan.id);
            });
        }else{
            content.setPaintFlags(0);
            mPlan.stat = false;
            selected.setChecked(false);
            AsyncTask.getInstance().postNormalTask(()->{
                PlanRepository.getInstance(getApplication()).updateStat(mPlan.stat,mPlan.id);
            });
        }
    }

    private void setListener(){
        back.setOnClickListener((View v) -> {
            finish();
        });

        selected.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mPlan.stat){
                    content.setPaintFlags(0);
                    mPlan.stat = false;
                    selected.setChecked(false);
                    AsyncTask.getInstance().postNormalTask(()->{
                        PlanRepository.getInstance(getApplication()).updateStat(mPlan.stat,mPlan.id);
                    });
                }else{
                    content.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mPlan.stat= true;
                    selected.setChecked(true);
                    AsyncTask.getInstance().postNormalTask(()->{
                        PlanRepository.getInstance(getApplication()).updateStat(mPlan.stat,mPlan.id);
                    });
                }
            }
        });

    }





}
