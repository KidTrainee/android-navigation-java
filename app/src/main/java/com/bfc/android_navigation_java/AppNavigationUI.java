package com.bfc.android_navigation_java;

import java.util.Set;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.navigation.NavDestination;

//
// Created by  on 2020-01-16.
//
public class AppNavigationUI {
    static boolean matchDestinations(@NonNull NavDestination destination,
                                            @NonNull Set<Integer> destinationIds) {
        NavDestination currentDestination = destination;
        do {
            if (destinationIds.contains(currentDestination.getId())) {
                return true;
            }
            currentDestination = currentDestination.getParent();
        } while (currentDestination != null);
        return false;
    }

    static boolean matchDestination(@NonNull NavDestination destination,
                                           @IdRes int destId) {
        NavDestination currentDestination = destination;
        while (currentDestination.getId() != destId && currentDestination.getParent() != null) {
            currentDestination = currentDestination.getParent();
        }
        return currentDestination.getId() == destId;
    }
}
