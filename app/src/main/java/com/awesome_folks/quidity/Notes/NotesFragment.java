package com.awesome_folks.quidity.Notes;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.awesome_folks.quidity.MainActivity;
import com.awesome_folks.quidity.R;


public class NotesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager linearLayoutManager;
    private NotesListDisplay adaptor;
    private Handler handler = new Handler();

    /**
     * This is a method for Fragment.
     * You can do the same in onCreate or onRestoreInstanceState
     */
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
//            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
//        }
//    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//         outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Notes");
        linearLayoutManager = new LinearLayoutManager(getActivity());
        View layout = inflater.inflate(R.layout.notes_frag, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.noteSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorRoutine),
                getResources().getColor(R.color.colorDashBoard),
                getResources().getColor(R.color.colorNotes),
                getResources().getColor(R.color.colorDiscussions)
        );

        recyclerView = (RecyclerView) layout.findViewById(R.id.noteList);
        adaptor = new NotesListDisplay(getActivity());
        recyclerView.setAdapter(adaptor);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isSignificantDelta = Math.abs(dy) > getResources().getDimensionPixelOffset(R.dimen.fab_scroll_threshold);
                if (isSignificantDelta) {
                    if (dy > 0) {
                        ((MainActivity) getActivity()).HideFAB(true);
                    } else {
                        ((MainActivity) getActivity()).HideFAB(false);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int pos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                super.onScrollStateChanged(recyclerView, newState);
                if (pos == adaptor.cardList.size() - 1)
                    ((MainActivity) getActivity()).HideFAB(true);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        adaptor.refresh(getActivity(), swipeRefreshLayout);
        recyclerView.setAdapter(adaptor);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adaptor.getData(query, false);
        adaptor.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return false;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
