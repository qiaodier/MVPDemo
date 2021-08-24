package com.mvp.cn.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mvp.cn.R;
import com.mvp.master.mvp.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;


/**
 * Created by iqiao on 2020-03-04 10:24
 * Desc:
 *
 * @author iqiao
 */
public class ThirdFragment extends BaseFragment {

    Button button;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initViews(View view) {
        button = view.findViewById(R.id.button);
        button.setOnClickListener((View v) -> {
            Navigation.findNavController(v).navigate(R.id.action_thirdFragment_to_third2Fragment);
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
