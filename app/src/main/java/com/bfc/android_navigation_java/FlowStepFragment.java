package com.bfc.android_navigation_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

//
// Created by  on 2019-11-13.
//
public class FlowStepFragment extends LogcatFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        int flowStepNumber = getArguments().getInt("flowStepNumber");
        // TODO STEP 8 - Use type-safe arguments - remove previous line!
//        val safeArgs: FlowStepFragmentArgs by navArgs()
//        val flowStepNumber = safeArgs.flowStepNumber
        // TODO END STEP 8

        if (flowStepNumber == 2) {
            return inflater.inflate(R.layout.flow_step_two_fragment, container, false);
        }
        return inflater.inflate(R.layout.flow_step_one_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.next_button).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.next_action));

        TextView tv = view.findViewById(R.id.fragment_counter);
        if (tv != null) {
            tv.setText(String.valueOf(MainActivity.fragmentCounter));
        }
    }
}
