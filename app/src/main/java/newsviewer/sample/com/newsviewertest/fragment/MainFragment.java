package newsviewer.sample.com.newsviewertest.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import newsviewer.sample.com.newsviewertest.R;
import newsviewer.sample.com.newsviewertest.adapter.NewsPagerAdapter;
import newsviewer.sample.com.newsviewertest.model.News;
import newsviewer.sample.com.newsviewertest.network.NewsRequest;
import newsviewer.sample.com.newsviewertest.util.CycleTimer;
import newsviewer.sample.com.newsviewertest.view.VerticalNewsView;


public class MainFragment extends Fragment implements CycleTimer.onIntervalReachedListener {


    private static final String TAG = "MainFragment";
    private CycleTimer mCycleTimer;
    private onTimerChangedListener mListener;
    public static final int MODE_PERIODIC = 0;
    public static final int MODE_STANDARD = 1;
    private int CURRENT_MODE;
    private List<News> mNewsList;

    private int[] mParamsAlignPool = {RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.CENTER_HORIZONTAL};
    //private int[] mParamsRelationPool = { RelativeLayout.RIGHT_OF, RelativeLayout.LEFT_OF};
    //private RelativeLayout mContainer;

    private ScrollView mContainer;
    private RelativeLayout mInsideContainer;
    private ViewPager mViewPager;

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mContainer = (ScrollView) v.findViewById(R.id.svContainer);
        //mScrollContainer = (ScrollView)v.findViewById(R.id.svScrollContainer);
        mInsideContainer = (RelativeLayout) mContainer.findViewById(R.id.rlInsideContainer);
        mViewPager = (ViewPager) v.findViewById(R.id.pager);


        // Create and start the cycle timer
        mCycleTimer = new CycleTimer(getActivity());
        mCycleTimer.registerListener(this);
        mCycleTimer.startTimer();

        NewsRequest request = new NewsRequest(getActivity());
        request.getNews();
        mNewsList = request.getResults();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout();
    }

    @Override
    public void onFinish() {


        //Before refreshing the layout, check to make sure that we are in fact
        //In Periodic Cycle Mode
        if (CURRENT_MODE == MODE_PERIODIC) {
            try {
                refreshLayout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static int getRandomParam(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public void refreshLayout() {

        //Hide the ViewPager from the layout
        mViewPager.setVisibility(View.GONE);

        //Just some added "randomness" :)
        Collections.shuffle(mNewsList);

        //Remove all previous subviews from layout
        mInsideContainer.removeAllViews();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //On each refresh we need to re-layout the news items in a way that makes "sense"
        //Each News item consists of a thumbnail, article title, and url link(truncated to one line for cleanliness)

        //First, we iterate through the news items returned from the API call
        int numItems = mNewsList.size();


        for (int i = 0; i < mNewsList.size(); i++) {

            //Then, we create a View for each News object
            //In this case we're using our custom VerticalNewsView class
            VerticalNewsView vertView = new VerticalNewsView(getActivity());
            vertView.setTitle(mNewsList.get(i).getTitle());
            vertView.setUrl(mNewsList.get(i).getUrl());
            vertView.setImage(mNewsList.get(i).getThumbnailUrl());
            vertView.setId(i);


            //Create a new set of layout params for each view
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            //We randomly select a layout param attribute from our pool of attributes
            //CENTER, LEFT, RIGHT, etc
            if (i == 0) {


                params.addRule(getRandomParam(mParamsAlignPool));

                //Adding the BELOW rule guarantees that each subview is rendered on a new "line"
                //and will not overlap with a previous view
            } else {
                params.addRule(RelativeLayout.BELOW, i - 1);
                params.addRule(getRandomParam(mParamsAlignPool));

                //params.setMargins(new Random().nextInt(60), new Random().nextInt(60), new Random().nextInt(60), new Random().nextInt(60) );
            }


            //Set the subview's layout params
            vertView.setLayoutParams(params);


            //Add the view to the layout
            mInsideContainer.addView(vertView);


        }


    }

    private void refreshLayoutStandard() {
        mInsideContainer.removeAllViews();

        // mInsideContainer.addView(mViewPager);
        mViewPager.setVisibility(View.VISIBLE);
        NewsPagerAdapter adapter = new NewsPagerAdapter(getActivity(), 3);
        adapter.setNewsList((ArrayList) mNewsList);

        adapter.setPager(mViewPager);

        mViewPager.setAdapter(adapter);


    }

    @Override
    public void onTick(long l) {
        mListener.onTimerTick(l);
    }

    public interface onTimerChangedListener {
        public void onTimerTick(long l);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onTimerChangedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            Log.d(TAG, "ClassCastException. MainActivity must implement onTimerChangedListener!");
        }
    }

    public void setCurrentMode(int mode) {
        this.CURRENT_MODE = mode;

        if (CURRENT_MODE == MODE_PERIODIC) {
            mCycleTimer.cancelTimer();
            mCycleTimer.startTimer();
            refreshLayout();

        } else if (CURRENT_MODE == MODE_STANDARD) {
            mCycleTimer.cancelTimer();
            refreshLayoutStandard();
        }
    }
}
