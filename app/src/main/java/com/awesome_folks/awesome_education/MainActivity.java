package com.awesome_folks.awesome_education;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.awesome_folks.awesome_education.DashBoard.DashFragment;
import com.awesome_folks.awesome_education.Notes.NotesFragment;
import com.awesome_folks.awesome_education.Notices.NoticeFragment;
import com.awesome_folks.awesome_education.Routine.RoutineFragment;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class MainActivity extends ActionBarActivity {
    private NotesFragment notesFrag;
    private DashFragment dashFrag;
    private NoticeFragment noticeFrag;
    private RoutineFragment routineFrag;
    NavigationDrawerFragment drawerFragment;
    private int mCurrentPosition = 0;
    Toolbar toolbar;
    View elevationShadow;

    private void setCurrentPosition(int position) {
        mCurrentPosition = position;
    }

    public static final String CURRENT_POSITION = "CURRENT_POSITION";

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // TODO Auto-generated method stub
        if (savedInstanceState != null) {
            super.onSaveInstanceState(savedInstanceState);
            System.out.println(mCurrentPosition);
            savedInstanceState.putInt(CURRENT_POSITION, mCurrentPosition);
        }
    }

    @Override

    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setFragment(savedInstanceState.getInt(CURRENT_POSITION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesFrag = new NotesFragment();
        dashFrag = new DashFragment();
        noticeFrag = new NoticeFragment();
        routineFrag = new RoutineFragment();
        setContentView(R.layout.activity_main);
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);

        //Initializing Toolbar
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        elevationShadow = findViewById(R.id.elevationShadow);
        //Initializing Navigation Bar
        drawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        if (savedInstanceState != null) {
            setFragment(savedInstanceState.getInt(CURRENT_POSITION));
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragContainer, dashFrag, "Dashboard").setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .commit();
            setFragment(0);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void navFootListener(View v) {
        if (v.getId() == R.id.settings) {
            Intent i = new Intent(this, SettingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        if (v.getId() == R.id.feedback) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse("mailto:"));
            String to = "awesomefolks@afks.com";
            i.putExtra(Intent.EXTRA_EMAIL, to);
            i.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
            i.setType("message/rfc822");
            Intent chooser = Intent.createChooser(i, "Send Feedback");
            startActivity(chooser);
        }

    }

    public void navDrawerListener(View view) {

        if (view.getId() == R.id.tabDash) {
            setFragment(0);
        } else if (view.getId() == R.id.tabNotes) {
            setFragment(1);
        } else if (view.getId() == R.id.tabNotices) {
            setFragment(2);
        } else if (view.getId() == R.id.tabDiscussion) {
            setFragment(3);
        } else if (view.getId() == R.id.tabRoutine) {
            setFragment(4);
        }
    }

    private void setFragment(int position) {
        if (position == 0) {
            drawerFragment.onItemClickNavigation(getSupportFragmentManager(), dashFrag, 0, "Dashboard");
            setCurrentPosition(0);

        } else if (position == 1) {
            drawerFragment.onItemClickNavigation(getSupportFragmentManager(), notesFrag, 1, "Notes");
            setCurrentPosition(1);

        } else if (position == 2) {
            drawerFragment.onItemClickNavigation(getSupportFragmentManager(), noticeFrag, 2, "Notice");
            setCurrentPosition(2);

        } else if (position == 3) {
            drawerFragment.onItemClickNavigation(getSupportFragmentManager(), notesFrag, 3, "Notes");
            setCurrentPosition(3);

        } else if (position == 4) {
            drawerFragment.onItemClickNavigation(getSupportFragmentManager(), routineFrag, 4, "Routine");
            setCurrentPosition(4);
        }
        if (position == 4) {
            elevationShadow.setVisibility(View.GONE);
        } else {
            elevationShadow.setVisibility(View.VISIBLE);
        }
    }

    private void toggleTranslateFAB(float slideOffset) {
        View fab = findViewById(R.id.fab);
        fab.setTranslationX(slideOffset * 300);  //fab
    }

    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }


    public void HideFAB(boolean isBottom) {
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        if (isBottom)
            fab.animate().translationY(300).setInterpolator(new DecelerateInterpolator());
        else
            fab.animate().translationY(0).setInterpolator(new AccelerateInterpolator());
        fab.collapse();
    }

    public void hideFab(View v) {
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        fab.collapse();
    }

    public void onFabExpanded() {
        ImageView overlay = (ImageView) findViewById(R.id.fabOverlay);
        overlay.setVisibility(View.VISIBLE);
    }

    public void onFabCollapsed() {
        ImageView overlay = (ImageView) findViewById(R.id.fabOverlay);
        overlay.setVisibility(View.GONE);
    }
}
