package com.awesome_folks.quidity.Routine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesome_folks.quidity.R;

/**
 * Created by Ritesh on 11/25/2014.
 */
public class RoutineMondayFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.routine_monday,container,false);
    }
}
