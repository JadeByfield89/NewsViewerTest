package newsviewer.sample.com.newsviewertest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import newsviewer.sample.com.newsviewertest.R;

/**
 * Created by Jade Byfield on 1/11/2015.
 */
public class VerticalNewsView extends LinearLayout {


    private ImageView mImageView;
    private TextView mTitle;
    private TextView mUrl;
    private Context mContext;

    private String mNewsTitle;
    private String mNewsUrl;
    private String mThumbnailUrl;


    public VerticalNewsView(Context c) {
        super(c);
        this.mContext = c;

        inflate(c, R.layout.vertical_news_view, this);
        this.mImageView = (ImageView) findViewById(R.id.ivIcon);
        this.mTitle = (TextView) findViewById(R.id.tvTitle);
        this.mUrl = (TextView) findViewById(R.id.tvUrl);


    }


    public void setImage(String url) {

        if (url.isEmpty() || url.equals("")) {
            mImageView.setImageResource(R.drawable.news_icon);
        } else {
            this.mThumbnailUrl = url;
            Picasso.with(mContext).load(url).into(mImageView);
        }
    }

    public void setTitle(String title) {
        this.mNewsTitle = title;
        this.mTitle.setText(title);
    }

    public void setUrl(String url) {
        this.mNewsUrl = url;
        this.mUrl.setText(url);
    }

    public String getNewsTitle() {

        return mNewsTitle;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public String getmThumbnailUrl() {

        return mThumbnailUrl;
    }
}
