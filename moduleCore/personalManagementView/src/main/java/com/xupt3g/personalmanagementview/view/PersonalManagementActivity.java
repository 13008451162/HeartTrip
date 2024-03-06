package com.xupt3g.personalmanagementview.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.personalmanagementview.R;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;


/**
 * @author lenovo
 */
@Route(path = "/personalManagement/PersonalManagementActivity")
public class PersonalManagementActivity extends AppCompatActivity {
    public MutableLiveData<AccountInfoResponse> responseMutableLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_personal_management);

        UiTool.setImmersionBar(this, false);


    }


    public static void setFragmentChangeTransition(Context context, Fragment fragment) {
        // 设置进入动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Transition enterTransition = TransitionInflater.from(context)
                    .inflateTransition(android.R.transition.slide_right);
            fragment.setEnterTransition(enterTransition);
        }

        // 设置退出动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Transition exitTransition = TransitionInflater.from(context)
                    .inflateTransition(android.R.transition.slide_left);
            fragment.setExitTransition(exitTransition);
        }
    }
}