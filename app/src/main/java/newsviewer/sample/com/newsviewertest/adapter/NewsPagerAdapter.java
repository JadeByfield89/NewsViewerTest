package newsviewer.sample.com.newsviewertest.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import newsviewer.sample.com.newsviewertest.R;
import newsviewer.sample.com.newsviewertest.model.News;

/**
 * Created by Jade Byfield on 1/11/2015.
 */
public class NewsPagerAdapter extends PagerAdapter {

    private int NUM_PAGES;
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<News> mNewsList;
    private ViewPager mPager;
    private ArrayList<News> listOne = new ArrayList<News>();
    private ArrayList<News> listTwo = new ArrayList<News>();
    private ArrayList<News> listThree = new ArrayList<News>();


    public NewsPagerAdapter(Context c, int pages) {
        this.mContext = c;
        this.NUM_PAGES = pages;

        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.mNewsList = newsList;

        //Split the data set so we can display news items in each page of the viewpager
        splitNewsList();
    }

    public void setPager(ViewPager pager) {
        this.mPager = pager;
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == ((View) o);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }


    //First check if we can display our news items evenly
    //If not, display the first 2 pages evenly and show the remaining on the last page
    public void splitNewsList() {

        //We need to hold 2 extra copies of our original list since
        //ArrayList.sublist will store the truncated list to the original object
        ArrayList<News> mNewsList2 = mNewsList;
        ArrayList<News> mNewsList3 = mNewsList;
        int largestEven = 0;
        //If the data set is divisible by 3, display all items evenly in 3 pages


        if (mNewsList.size() % 3 == 0) {
            listOne.addAll(mNewsList.subList(0, mNewsList.size() / 3));
            listTwo.addAll(mNewsList2.subList(mNewsList2.size() / 3, (mNewsList2.size() / 3) * 2));
            listThree.addAll(mNewsList3.subList((mNewsList3.size() / 3) * 2, mNewsList3.size()));
        }

        //If not, distribute the first 2 pages evenly, and display the remaining on the last
        else {
            for (int i = mNewsList.size(); i > 0; i--) {
                if (i % 2 == 0) {
                    largestEven = i;

                }
            }

            Log.d("NewsPagerAdapter", "Even index -> " + largestEven);
            Log.d("NewsPagerAdapter", "Index -> " + largestEven);
            listOne.addAll(mNewsList.subList(0, largestEven / 2));
            listTwo.addAll(mNewsList2.subList(largestEven / 2, largestEven));
            listThree.addAll(mNewsList3.subList(largestEven, mNewsList3.size()));
        }


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int resId = 0;
        View page = null;


        switch (position) {


            case 0:
                page = mInflater.inflate(R.layout.viewpager_layout, container, false);
                GridView grid = (GridView) page.findViewById(R.id.gvGrid);

                container.addView(page);

                NewsGridAdapter adapter = new NewsGridAdapter(listOne, mContext);
                grid.setAdapter(adapter);


                break;

            case 1:


                page = mInflater.inflate(R.layout.viewpager_layout, container, false);
                GridView grid2 = (GridView) page.findViewById(R.id.gvGrid);
                container.addView(page);

                NewsGridAdapter adapter2 = new NewsGridAdapter(listTwo, mContext);
                grid2.setAdapter(adapter2);

                break;

            case 2:
                page = mInflater.inflate(R.layout.viewpager_layout, container, false);
                GridView grid3 = (GridView) page.findViewById(R.id.gvGrid);
                container.addView(page);

                NewsGridAdapter adapter3 = new NewsGridAdapter(listThree, mContext);
                grid3.setAdapter(adapter3);

                break;
        }


        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
