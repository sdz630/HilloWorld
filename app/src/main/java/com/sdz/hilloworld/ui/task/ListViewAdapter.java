package com.sdz.hilloworld.ui.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdz.hilloworld.R;
import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.data.sql.todolist.Plan;
import com.sdz.hilloworld.data.sql.todolist.PlanRepository;
import com.sdz.hilloworld.global.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private List<Plan> data;
    private Context context;
    private float xDown;
    private float xUp;
    private Button button;
    private Animation animation;
    private View view;
    private EditText editText;

    public ListViewAdapter(List<Plan> data, Context context, EditText editText) {
        this.editText = editText;
        this.data = data;
        this.context = context;
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_delete);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_todo, null);
            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.cb_delete_task);
            holder.content = convertView.findViewById(R.id.et_content);
            holder.date = convertView.findViewById(R.id.et_date);
            holder.deleteButton = convertView.findViewById(R.id.btn_delete);
            TextView content = holder.content;
            CheckBox checkBox = holder.checkBox;
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    data.get(position).stat = true;
                    content.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    AsyncTask.getInstance().postNormalTask(()->{
                        PlanRepository.getInstance(context).updateStat(data.get(position).stat,data.get(position).id);
                    });
                } else {
                    data.get(position).stat = false;
                    content.setPaintFlags(0);
                    AsyncTask.getInstance().postNormalTask(()->{
                        PlanRepository.getInstance(context).updateStat(data.get(position).stat,data.get(position).id);
                    });
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewHolder holder = (ViewHolder) v.getTag();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        xDown = event.getX();
                        if (button != null) {
                            button.setVisibility(View.GONE);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        xUp = event.getX();
                        break;
                    }
                }
                if (holder.deleteButton != null) {
                    if (Math.abs(xDown - xUp) > 130 && xUp < xDown) {
                        holder.deleteButton.setVisibility(View.VISIBLE);
                        button = holder.deleteButton;
                        view = v;
                        return true;
                    }
                    if (Math.abs(xDown - xUp) > 130 && xUp > xDown) {
                        if (holder.deleteButton.getVisibility() == View.VISIBLE) {
                            holder.deleteButton.setVisibility(View.GONE);
                        }
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button != null) {
                    button.setVisibility(View.GONE);
                    deleteItem(view, position);
                }
            }
        });

        holder.content.setText(data.get(position).title);
        holder.date.setText(data.get(position).date.toString());
        if(data.get(position).stat){
            holder.checkBox.setChecked(true);
            holder.content.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.checkBox.setChecked(false);
            holder.content.setPaintFlags(0);
        }
        convertView.setOnClickListener((View v) -> {
                    Intent intent = new Intent(context, TaskActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",data.get(position));
                    intent.putExtra("task", bundle);
                    context.startActivity(intent);
                }
        );
        //ListView刷新会让焦点
        if (Constants.INIT_TASK_FRAGMENT_COMPLETED) {
            editText.requestFocus();
        }
//        editText.requestFocus();
        return convertView;
    }


    protected void deleteItem(View view, final int position) {
        view.startAnimation(animation);
        AsyncTask.getInstance().postNormalTask(() -> PlanRepository.getInstance(context).delete(data.get(position).id));
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                data.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView content;
        TextView date;
        Button deleteButton;
    }
}
