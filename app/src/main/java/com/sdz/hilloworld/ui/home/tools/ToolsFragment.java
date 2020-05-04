package com.sdz.hilloworld.ui.home.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.flexbox.FlexboxLayout;
import com.sdz.hilloworld.R;

public class ToolsFragment extends Fragment {

    ToolsViewModel studyViewModel;

    FlexboxLayout mFlexboxLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studyViewModel = ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tool, container, false);
        return root;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFlexboxLayout = (FlexboxLayout)getActivity().findViewById(R.id.flexbox_layout);

        // 通过代码向FlexboxLayout添加View
//        TextView textView = new TextView(getContext());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            textView.setBackground(getResources().getDrawable(R.drawable.background));
//        }
//        textView.setText("Test  Label");
//        textView.setGravity(Gravity.TOP);
//
//        textView.setPadding(30,0,30,0);
//        textView.setTextColor(getResources().getColor(R.color.pure_black));
//        mFlexboxLayout.addView(textView);
        //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
//        ViewGroup.LayoutParams params = textView.getLayoutParams();
//        if(params instanceof FlexboxLayout.LayoutParams){
//            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
//            layoutParams.setFlexBasisPercent(0.5f);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
