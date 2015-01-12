package newsviewer.sample.com.newsviewertest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import newsviewer.sample.com.newsviewertest.R;
import newsviewer.sample.com.newsviewertest.model.News;

/**
 * Created by Jade Byfield on 1/11/2015.
 */
public class NewsGridAdapter extends BaseAdapter {


    private ArrayList<News> mNewsList;
    private Context mContext;
    private LayoutInflater mInflater;

    public NewsGridAdapter(ArrayList<News> newsList, Context c) {

        this.mContext = c;
        this.mNewsList = newsList;
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int i) {
        return mNewsList.get(i);
    }

    //Use Viewholder pattern to avoid uncessary calls to findViewById()
    private class ViewHolder {

        private ImageView icon;
        private TextView title;
        private TextView url;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.vertical_news_view, viewGroup, false);
            holder.icon = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.url = (TextView) convertView.findViewById(R.id.tvUrl);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();
        if (!mNewsList.get(i).getThumbnailUrl().isEmpty()) {
            Picasso.with(mContext).load(mNewsList.get(i).getThumbnailUrl()).into(holder.icon);
        }

        holder.title.setText(mNewsList.get(i).getTitle());
        holder.url.setText(mNewsList.get(i).getUrl());
        return convertView;
    }
}
