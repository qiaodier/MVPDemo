package com.mvp.cn.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mvp.cn.R;
import com.mvp.cn.mvp.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iqiao on 2020-03-04 10:24
 * Desc:
 *
 * @author iqiao
 */
public class ThirdFragment extends BaseFragment {
    @BindView(R.id.button)
    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener((View v) -> {
            Navigation.findNavController(v).navigate(R.id.action_thirdFragment_to_third2Fragment);
        });
    }


}
