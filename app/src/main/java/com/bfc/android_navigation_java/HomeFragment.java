package com.bfc.android_navigation_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

//
// Created by  on 2019-11-13.
//
public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.navigate_destination_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findNavController(HomeFragment.this).navigate(R.id.flow_step_one_dest, null);
//            }
//        });
//        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.flow_step_one_dest, null));

        view.findViewById(R.id.navigate_destination_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavOptions options = new NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build();
                findNavController(HomeFragment.this).navigate(R.id.flow_step_one_dest, null, options);
            }
        });

        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
//        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.next_action, null)
//        )
        //TODO END STEP 7.2
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }
}
