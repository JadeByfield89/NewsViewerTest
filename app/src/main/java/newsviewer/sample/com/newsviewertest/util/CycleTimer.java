package newsviewer.sample.com.newsviewertest.util;

import android.app.Fragment;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * Created by Jade Byfield on 1/10/2015.
 */


//This class notifies the Main Fragment that it's time to update the layout(if in Periodic Cycle Mode)
public class CycleTimer {


    private Context mContext;

    private CountDownTimer mTimer;
    private onIntervalReachedListener mListener;


    public CycleTimer(Context context) {
        this.mContext = context;

        mTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {

                mListener.onTick(l);
            }

            @Override
            public void onFinish() {

                //restart the timer
                mListener.onFinish();
                mTimer.start();
            }
        };
    }


    //We can implement this listener inside our fragment to listen for timer updates
    public interface onIntervalReachedListener {
        public abstract void onFinish();

        public abstract void onTick(long l);
    }


    public void registerListener(onIntervalReachedListener listener) {

        this.mListener = listener;

    }


    public void startTimer() {

        mTimer.start();


    }

    public void cancelTimer() {
        mTimer.cancel();
    }


}

