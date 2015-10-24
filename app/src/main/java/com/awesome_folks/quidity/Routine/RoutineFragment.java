package com.awesome_folks.quidity.Routine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesome_folks.quidity.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class RoutineFragment extends Fragment implements MaterialTabListener {
    ViewPager routinePager = null;
    MaterialTabHost tabHost;
    RoutineAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Routine");
        View v = inflater.inflate(R.layout.routine_frag, container, false);
        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        routinePager = (ViewPager) v.findViewById(R.id.routinePager);
        pagerAdapter = new RoutineAdapter(getChildFragmentManager());
        routinePager.setAdapter(pagerAdapter);
        routinePager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        return v;
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        routinePager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
}

class RoutineAdapter extends FragmentStatePagerAdapter {

    public RoutineAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment routineFrag = null;
        switch (i) {
            case 0: {
                routineFrag = new RoutineSundayFrag();
                break;
            }
            case 1: {
                routineFrag = new RoutineMondayFrag();
                break;
            }
            case 2: {
                routineFrag = new RoutineTuesdayFrag();
                break;
            }
            case 3: {
                routineFrag = new RoutineWednesdayFrag();
                break;
            }
            case 4: {
                routineFrag = new RoutineThursdayFrag();
                break;
            }
            case 5: {
                routineFrag = new RoutineFridayFrag();
                break;
            }

        }
        return routineFrag;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String routineTitle = null;
        switch (position) {
            case 0: {
                routineTitle = "Sunday";
                break;
            }
            case 1: {
                routineTitle = "Monday";
                break;
            }
            case 2: {
                routineTitle = "Tuesday";
                break;
            }
            case 3: {
                routineTitle = "Wednesday";
                break;
            }
            case 4: {
                routineTitle = "Thursday";
                break;
            }
            case 5: {
                routineTitle = "Friday";
                break;
            }
        }
        System.out.println(routineTitle);
        return routineTitle;
    }
}