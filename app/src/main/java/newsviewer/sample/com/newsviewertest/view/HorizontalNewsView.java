package newsviewer.sample.com.newsviewertest.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import newsviewer.sample.com.newsviewertest.R;

/**
 * Created by Jade Byfield on 1/11/2015.
 */
public class HorizontalNewsView extends RelativeLayout {


    private ImageView mImageView;
    private TextView mTitle;
    private TextView mUrl;
    private Context mContext;

    public HorizontalNewsView(Context c){
        super(c);

        this.mContext = c;
        inflate(c, R.layout.horizontal_news_view, this);
        this.mImageView = (ImageView)findViewById(R.id.ivIcon);
        this.mTitle = (TextView)findViewById(R.id.tvTitle);
        this.mUrl = (TextView)findViewById(R.id.tvUrl);

    }

    public void setImage(String url){
        Picasso.with(mContext).load(url).into(mImageView);
    }

    public void setTitle(String title){
        this.mTitle.setText(title);
    }

    public void setUrl(String url){
        this.mUrl.setText(url);
    }
}
