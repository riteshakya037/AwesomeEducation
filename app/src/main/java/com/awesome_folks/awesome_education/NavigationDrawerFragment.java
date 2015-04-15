package com.awesome_folks.awesome_education;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private int mCurrentPosition = 0;
    private ActionBarDrawerToggleCompat mDrawerToggle;
    public static final String CURRENT_POSITION = "CURRENT_POSITION";
    FragmentManager mFragmentManager;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.nav_drawer, container, true);
        if (savedInstanceState != null) {
            setCurrentPosition(savedInstanceState.getInt(CURRENT_POSITION));
        }
        return layout;
    }


    public void setUp(DrawerLayout drawerLayout, Toolbar toolBar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggleCompat(getActivity(), mDrawerLayout, toolBar);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION, mCurrentPosition);
    }

    public void onItemClickNavigation(FragmentManager mFragmentManager, android.support.v4.app.Fragment layoutContainerId, int position, String fragmentName) {
        if (position != getCurrentPosition()) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragContainer, layoutContainerId, fragmentName)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(fragmentName)
                    .commit();
            colorizeCurrentSelection(position, getCurrentPosition());
            setCurrentPosition(position);
        } else
            colorizeCurrentSelection(position, getCurrentPosition());
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    private void colorizeCurrentSelection(int newPosition, int currentPosition) {
        if (currentPosition == 0) {
            setColorInactive(R.id.lblDash, R.id.icoDash);
        } else if (currentPosition == 1) {
            setColorInactive(R.id.lblNotes, R.id.icoNotes);
        } else if (currentPosition == 2) {
            setColorInactive(R.id.lblNotices, R.id.icoNotices);
        } else if (currentPosition == 3) {
            setColorInactive(R.id.lblDiscussion, R.id.icoDiscussion);
        } else if (currentPosition == 4) {
            setColorInactive(R.id.lblRoutine, R.id.icoRoutine);
        }
        if (newPosition == 0) {
            setColorActive(R.id.lblDash, R.id.icoDash);
        } else if (newPosition == 1) {
            setColorActive(R.id.lblNotes, R.id.icoNotes);
        } else if (newPosition == 2) {
            setColorActive(R.id.lblNotices, R.id.icoNotices);
        } else if (newPosition == 3) {
            setColorActive(R.id.lblDiscussion, R.id.icoDiscussion);
        } else if (newPosition == 4) {
            setColorActive(R.id.lblRoutine, R.id.icoRoutine);
        }

    }

    private void setColorActive(int textID, int iconID) {
        TextView text = (TextView) getView().findViewById(textID);
        ImageView ico = (ImageView) getView().findViewById(iconID);
        text.setTextColor(getResources().getColor(R.color.primaryColor));
        ico.setColorFilter(getResources().getColor(R.color.primaryColor));
    }

    private void setColorInactive(int textID, int iconID) {

        TextView text = (TextView) getView().findViewById(textID);
        ImageView ico = (ImageView) getView().findViewById(iconID);
        text.setTextColor(getResources().getColor(R.color.materialGray));
        text.setAlpha(170);
        ico.setColorFilter(getResources().getColor(R.color.materialGray));
    }

    private void setCurrentPosition(int position) {
        mCurrentPosition = position;
    }

    /**
     * get position in the last clicked item list
     */
    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }

    private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle {

        public ActionBarDrawerToggleCompat(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
            super(
                    activity,
                    drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            ((MainActivity)getActivity()).onDrawerSlide(slideOffset);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);

        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

}
