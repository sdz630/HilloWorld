package com.sdz.hilloworld.ui.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sdz.hilloworld.R;
import com.sdz.hilloworld.bridge.state.ToDoListViewModel;
import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.data.sql.todolist.Plan;
import com.sdz.hilloworld.data.sql.todolist.PlanRepository;
import com.sdz.hilloworld.global.Constants;
import com.sdz.hilloworld.utils.Utils;

import java.util.List;

public class TaskFragment extends Fragment {

    ToDoListViewModel toDoListViewModel;


    ListView listView;
    EditText addEditTextView;
    Button addButton;
    FloatingActionButton floatButton;
    LinearLayout linearLayout;

    ListViewAdapter mAdapter;
    private List<Plan> list;
    PlanRepository planRepository;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoListViewModel = ViewModelProviders.of(this).get(ToDoListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init(View view) {
        listView = view.findViewById(R.id.list_to_do);
        addEditTextView = view.findViewById(R.id.edet_add_thing);
        addButton = view.findViewById(R.id.btn_add_thing);
        linearLayout = view.findViewById(R.id.line_add_thing);
        floatButton = view.findViewById(R.id.float_button);
        AsyncTask.getInstance().postNormalTask(
                () -> {
                    planRepository = PlanRepository.getInstance(getActivity());
                    list = planRepository.getPlanList();
                }, () -> {
                    mAdapter = new ListViewAdapter(list, getActivity(), addEditTextView);
                    listView.setAdapter(mAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getActivity(), "点击事件，执行你的操作", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Constants.INIT_TASK_FRAGMENT_COMPLETED = true;
                }
        );


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = addEditTextView.getText().toString();
                Plan plan = new Plan(input, Utils.getSystemDate());
                list.add(plan);
                mAdapter.notifyDataSetChanged();
                addEditTextView.setText("");
                AsyncTask.getInstance().postNormalTask(() -> planRepository.insertPlan(plan));
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatButton.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                addEditTextView.requestFocus(1);
            }
        });
        addEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditTextView.requestFocus();
            }
        });
    }

    private void initData() {
    }


}
