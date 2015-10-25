package com.awesome_folks.quidity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.awesome_folks.quidity.BottomSheet.BottomSheet;
import com.awesome_folks.quidity.BottomSheet.BottomSheetCallback;
import com.awesome_folks.quidity.DashBoard.DashFragment;
import com.awesome_folks.quidity.Notes.NotesFragment;
import com.awesome_folks.quidity.Notices.NoticeFragment;
import com.awesome_folks.quidity.Parse.NoteRow;
import com.awesome_folks.quidity.Routine.RoutineFragment;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements BottomSheetCallback {
    private NotesFragment notesFrag;
    private DashFragment dashFrag;
    private NoticeFragment noticeFrag;
    private RoutineFragment routineFrag;
    private int mCurrentPosition = 0;
    Toolbar toolbar;
    View elevationShadow;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    CircleImageView proflePic;

    public static final String CURRENT_POSITION = "CURRENT_POSITION";
    private TextView lblName, lblEmail;
    private BottomSheet bottomSheet;

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // TODO Auto-generated method stub
        if (savedInstanceState != null) {
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putInt(CURRENT_POSITION, mCurrentPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPosition = savedInstanceState.getInt(CURRENT_POSITION, 0);
        Menu menu = navigationView.getMenu();
        menu.getItem(mCurrentPosition).setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesFrag = new NotesFragment();
        dashFrag = new DashFragment();
        noticeFrag = new NoticeFragment();
        routineFrag = new RoutineFragment();
        setContentView(R.layout.activity_main);

        //Initializing Toolbar
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        elevationShadow = findViewById(R.id.elevationShadow);

        if (savedInstanceState != null) {
            mCurrentPosition =
                    savedInstanceState.getInt(CURRENT_POSITION);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer, dashFrag, "Dashboard")
                .commit();
        mCurrentPosition = 0;
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        lblName = (TextView) findViewById(R.id.lblUserName);
        lblEmail = (TextView) findViewById(R.id.lblEmailID);
        proflePic = (CircleImageView) findViewById(R.id.profile_image);
        if (ParseUser.getCurrentUser() != null) {
            ParseUser user = ParseUser.getCurrentUser();
            lblName.setText(user.get("Name").toString());
            lblEmail.setText(user.getEmail());
            if (user.get("profilePic") != null) {
                ParseFile profilePic = (ParseFile) user.get("profilePic");
                profilePic.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {
                        if (e == null) {
                            Bitmap bmp = BitmapFactory
                                    .decodeByteArray(bytes, 0, bytes.length);
                            proflePic.setImageBitmap(bmp);
                        }
                    }
                });
            }
        }
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                elevationShadow.setVisibility(View.VISIBLE);
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Replacing the main content with ContentFragment
                    case R.id.drawDash:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragContainer, dashFrag, "Dashboard")
                                .commit();
                        mCurrentPosition = 0;
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.drawNotes:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragContainer, notesFrag, "Notice")
                                .commit();
                        mCurrentPosition = 1;
                        return true;
                    case R.id.drawNotices:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragContainer, noticeFrag, "Notices")
                                .commit();
                        mCurrentPosition = 2;
                        return true;
                    case R.id.drawDiscussion:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragContainer, notesFrag, "Discussion")
                                .commit();
                        mCurrentPosition = 3;
                        return true;
                    case R.id.drawRoutine:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragContainer, routineFrag, "Routine")
                                .commit();
                        mCurrentPosition = 4;
                        elevationShadow.setVisibility(View.GONE);
                        return true;
                    case R.id.drawSetting:
                        Intent setting = new Intent(MainActivity.this, SettingActivity.class);
                        setting.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        setting.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setting);
                        return true;
                    case R.id.drawFeedback:
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setData(Uri.parse("mailto:"));
                        String to = "awesomefolks@afks.com";
                        i.putExtra(Intent.EXTRA_EMAIL, to);
                        i.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
                        i.setType("message/rfc822");
                        Intent chooser = Intent.createChooser(i, "Send Feedback");
                        startActivity(chooser);
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                slideFAB(slideOffset);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


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
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else if (id == R.id.action_search) {
            Snackbar.make(findViewById(R.id.drawer_layout), "I'm a Snackbar", Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

    }

    private void toggleTranslateFAB(float slideOffset) {
        View fab = findViewById(R.id.fab);
        fab.setTranslationX(slideOffset * 300);  //fab
    }

    public void slideFAB(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }


    public void HideFAB(boolean isBottom) {
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        if (isBottom)
            fab.animate().translationY(300);
        else
            fab.animate().translationY(0);
        fab.collapse();
    }

    @Override
    public void showBottomSheet(NoteRow noteRow) {
        if (bottomSheet == null || !bottomSheet.isShowing()) {
            bottomSheet = new BottomSheet(this, noteRow);
            bottomSheet.show();
        }
    }
}
