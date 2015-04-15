package com.awesome_folks.awesome_education.Notices;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesome_folks.awesome_education.MainActivity;
import com.awesome_folks.awesome_education.R;


public class NoticeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    private NoticeListDisplay adaptor;
    SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    LinearLayoutManager linearLayoutManager;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    /**
     * This is a method for Fragment.
     * You can do the same in onCreate or onRestoreInstanceState
     */
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
//            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Notice");
        linearLayoutManager = new LinearLayoutManager(getActivity());
        View layout = inflater.inflate(R.layout.notice_frag, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.noticeSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorRoutine),
                getResources().getColor(R.color.colorDashBoard),
                getResources().getColor(R.color.colorNotes),
                getResources().getColor(R.color.colorDiscussions)
        );

        recyclerView = (RecyclerView) layout.findViewById(R.id.noticeList);
        adaptor = new NoticeListDisplay(getActivity());
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
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                int pos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                super.onScrollStateChanged(recyclerView, newState);
//                if (pos == adaptor.cardList.size() - 1)
//                    ((MainActivity) getActivity()).HideFAB(true);
//                else
//                    ((MainActivity) getActivity()).HideFAB(false);
//
//            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    }


}
