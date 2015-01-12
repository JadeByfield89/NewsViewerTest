package newsviewer.sample.com.newsviewertest.activity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import newsviewer.sample.com.newsviewertest.R;
import newsviewer.sample.com.newsviewertest.fragment.MainFragment;


public class MainActivity extends Activity implements MainFragment.onTimerChangedListener, CompoundButton.OnCheckedChangeListener {

    private TextView mTimer;
    private CheckBox mPeriodic, mStandard;
    private MainFragment mFragment;
    private static final int MODE_PERIODIC = 0;
    private static final int MODE_STANDARD = 1;
    private int CURRENT_MODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            mFragment = new MainFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }

        mTimer = (TextView) findViewById(R.id.tvTimer);
        mPeriodic = (CheckBox) findViewById(R.id.cbPeriodic);
        mStandard = (CheckBox) findViewById(R.id.cbStandard);

        //Periodic mode by default
        mPeriodic.setChecked(true);

        mPeriodic.setOnCheckedChangeListener(this);
        mStandard.setOnCheckedChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Update the timer TextView one each 1 second tick of the timer
    @Override
    public void onTimerTick(long l) {

        String timeString = String.valueOf(l);
        timeString = timeString.substring(0, timeString.length() - 1);
        timeString = timeString.substring(0, timeString.length() - 2);
        mTimer.setText(timeString);
    }

    public static int safeLongToInt(long l) {
        return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
    }

    //Handle the mode selection
    //If either mode is unselected, the other mode is set to the default
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cbPeriodic:
                if (CURRENT_MODE == MODE_STANDARD) {
                    mPeriodic.setChecked(true);
                    mTimer.setVisibility(View.VISIBLE);
                    mStandard.setChecked(false);
                    mFragment.setCurrentMode(MainFragment.MODE_PERIODIC);
                    CURRENT_MODE = MODE_PERIODIC;
                } else {
                    mPeriodic.setChecked(false);
                    mTimer.setVisibility(View.INVISIBLE);
                    mStandard.setChecked(true);
                    mFragment.setCurrentMode(MODE_STANDARD);
                    CURRENT_MODE = MODE_STANDARD;
                }
                break;

            case R.id.cbStandard:
                if (CURRENT_MODE == MODE_PERIODIC) {
                    mStandard.setChecked(true);
                    mPeriodic.setChecked(false);
                    mFragment.setCurrentMode(MainFragment.MODE_STANDARD);
                    mTimer.setVisibility(View.INVISIBLE);
                    CURRENT_MODE = MODE_STANDARD;
                } else {
                    mStandard.setChecked(false);
                    mPeriodic.setChecked(true);
                    mFragment.setCurrentMode(MODE_PERIODIC);
                    mTimer.setVisibility(View.VISIBLE);
                    CURRENT_MODE = MODE_PERIODIC;
                }
                break;
        }
    }
}
