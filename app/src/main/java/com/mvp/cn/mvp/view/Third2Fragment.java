package com.mvp.cn.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.cn.R;
import com.mvp.cn.mvp.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by iqiao on 2020-03-04 13:23
 * Desc:
 * @author iqiao
 */
public class Third2Fragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third2,container,false);
    }
}
