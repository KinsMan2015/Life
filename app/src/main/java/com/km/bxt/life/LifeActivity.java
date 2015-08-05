package com.km.bxt.life;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.km.bxt.life.db.DBHelper;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LifeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, DeathFragment.OnFragmentInteractionListener {
    public static final String LIFE_SMS = "life_sms";
    public  static Toolbar mToolbar;
    private DBHelper mDBHelper;
    private SQLiteDatabase mReader;
    private SQLiteDatabase mWriter;
    private Cursor mCursor;
    private Timer timer = new Timer();
    private boolean isFirstStart = true;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private Life mLife = new Life();
    private CharSequence mTitle;
    private Calendar mCalendar = Calendar.getInstance();

    public Toolbar getToolbar() {
        return mToolbar;
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            setOld();
        }
    };

    public void setOld() {
        mCalendar = Calendar.getInstance();
        NumberFormat mNumberFormat = NumberFormat.getNumberInstance();
        mNumberFormat.setMaximumFractionDigits(10);//设置小数点后面
        if (judgeRunYear(mCalendar.get(Calendar.YEAR))) {
            double old = (mCalendar.get(Calendar.YEAR) - mLife.getBirthYear()) + (calculateDays(mCalendar.get(Calendar.YEAR), mLife.getBirthMonth(), mLife.getBirthMonth()) * 24 * 60 * 60 / 31622400d);
            mLife.setOld(mNumberFormat.format(old));
        } else {
            double old = (mCalendar.get(Calendar.YEAR) - mLife.getBirthYear()) + (calculateDays(mCalendar.get(Calendar.YEAR), 1, 1) * 24 * 60 * 60 / 31536000d);
            mLife.setOld(mNumberFormat.format(old));
        }
    }

    public boolean judgeRunYear(int year) {
        if (year % 100 == 0) {
            if (year % 400 == 0) {
                return true;
            }
        } else {
            if (year % 4 == 0) {
                return true;
            }
        }
        return false;

    }

    public int calculateDays(int year, int month, int day) {
        Calendar c1 = Calendar.getInstance();
        c1.set(year, month, day);
        long n1 = c1.getTimeInMillis();
        long n2 = mCalendar.getTimeInMillis();
        return (int) Math.abs((n1 - n2) / 24 / 3600000);

    }

    private void setBirthDate() {
        DatePickerDialog mLifeDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (mCalendar.get(Calendar.YEAR) < year || mCalendar.get(Calendar.MONTH) < monthOfYear || mCalendar.get(Calendar.DAY_OF_MONTH) < dayOfMonth) {
                    Toast.makeText(LifeActivity.this, R.string.toast_message_future, Toast.LENGTH_SHORT).show();
                }
                mLife.setBirthYear(year);
                mLife.setBirthMonth(monthOfYear);
                mLife.setBirthDay(dayOfMonth);
                mLife.setYear(mCalendar.get(Calendar.YEAR) - mLife.getBirthYear());
                if (mCalendar.get(Calendar.MONTH) > monthOfYear) {
                    mLife.setMonth(mLife.getYear() * 12 + mCalendar.get(Calendar.MONTH) - mLife.getBirthMonth());
                } else {
                    mLife.setMonth((mLife.getYear() - 1) * 12 + Math.abs(mCalendar.get(Calendar.MONTH) - mLife.getBirthMonth()));
                }

                mLife.setWeek(mLife.getYear() * 52 + mCalendar.get(Calendar.WEEK_OF_YEAR));
                mLife.setDay(calculateDays(mLife.getBirthYear(), mLife.getBirthMonth(), mLife.getBirthDay()));
                mLife.setHour(calculateDays(mLife.getBirthYear(), mLife.getBirthMonth(), mLife.getBirthDay()) * 24);
                mLife.setMinute(calculateDays(mLife.getBirthYear(), mLife.getBirthMonth(), mLife.getBirthDay()) * 24 * 60);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(mLife)).commit();
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        mLifeDatePickerDialog.show();
    }

    private void setDeathDate() {
        DatePickerDialog mDeathDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mLife.setDeathOld(calculateDays(year, monthOfYear, dayOfMonth));
                mLife.setEat(calculateDays(year, monthOfYear, dayOfMonth) * 3);
                mLife.setMakeLove(calculateDays(year, monthOfYear, dayOfMonth) / 4);
                mLife.setWeekends(calculateDays(year, monthOfYear, dayOfMonth) / 7);
                mLife.setHoliday(calculateDays(year, monthOfYear, dayOfMonth) / 182);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DeathFragment.newInstance(mLife)).commit();
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        mDeathDatePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDBHelper = new DBHelper(getApplicationContext());
        mReader = mDBHelper.getReadableDatabase();
        mWriter = mDBHelper.getWritableDatabase();
        mCursor = mReader.query(DBHelper.TBL_NAME1, null, null, null, null, null, null);
        ;
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),mToolbar);
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerTask.cancel();
        timer.cancel();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                if(isFirstStart){
                    isFirstStart = false;
                }
                else{
                    setBirthDate();
                }

                break;
            case 1:
                setDeathDate();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1_setting_born_date);
                break;
            case 2:
                mTitle = getString(R.string.title_section2_close_death_clock);
                break;
            case 3:
                mTitle = getString(R.string.title_section3_flyto_space);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.life, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String SERIALIZABLE_LIFE = "serializable_life";
        private Timer timer;
        private Life mLife;
        private ImageView iv_hour_nail, iv_minute_nail, iv_middle;
        private TextView mLifeOldTextView, mLifeYearTextView, mLifeMonthTextView, mLifeWeekTextView, mLifeDayTextView, mLifeHourTextView, mLifeMinuteTextView;
        private Button mDeathClock;
        private Boolean timeFlag = false;
        private float minuteoldDegree, houroldDegree;
        private Handler handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        changeTimeAnimator();
                        mLifeOldTextView.setText("你已经" + mLife.getOld() + "岁了");
                        break;
                    case 1:
                        break;
                }
            }
        };
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);

            }
        };

        public PlaceholderFragment() {
            timer = new Timer();
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Life life) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(SERIALIZABLE_LIFE, life);
            fragment.setArguments(args);
            return fragment;
        }

        private void changeTimeAnimator() {
            Date date = new Date();
            RotateAnimation currentminuterotateAnimation = new RotateAnimation(minuteoldDegree, date.getMinutes() * 6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            currentminuterotateAnimation.setDuration(1000);
            minuteoldDegree = date.getMinutes() * 6;
            iv_minute_nail.startAnimation(currentminuterotateAnimation);
            RotateAnimation currenthourrotateAnimation = new RotateAnimation(houroldDegree, (date.getHours() - 12) * 30 - 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            currenthourrotateAnimation.setDuration(1000);
            currenthourrotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    timeFlag = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            houroldDegree = (date.getHours() - 12) * 30 - 90;
            iv_hour_nail.startAnimation(currenthourrotateAnimation);

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_life, container, false);
            iv_hour_nail = (ImageView) rootView.findViewById(R.id.iv_hour_nail);
            iv_minute_nail = (ImageView) rootView.findViewById(R.id.iv_minute_nail);
            mLifeOldTextView = (TextView) rootView.findViewById(R.id.old_textview);
            mLifeYearTextView = (TextView) rootView.findViewById(R.id.life_year);
            mLifeMonthTextView = (TextView) rootView.findViewById(R.id.life_month);
            mLifeWeekTextView = (TextView) rootView.findViewById(R.id.life_week);
            mLifeDayTextView = (TextView) rootView.findViewById(R.id.life_day);
            mLifeHourTextView = (TextView) rootView.findViewById(R.id.life_hour);
            mLifeMinuteTextView = (TextView) rootView.findViewById(R.id.life_minute);
            mDeathClock = (Button) rootView.findViewById(R.id.death_clock);
            mLifeOldTextView.setText("你已经" + mLife.getOld() + "岁了");
            mLifeYearTextView.setText(mLife.getYear() + "");
            mLifeMonthTextView.setText(mLife.getMonth() + "");
            mLifeWeekTextView.setText(mLife.getWeek() + "");
            mLifeDayTextView.setText(mLife.getDay() + "");
            mLifeHourTextView.setText(mLife.getHour() + "");
            mLifeMinuteTextView.setText(mLife.getMinute() + "");
            mDeathClock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, DeathFragment.newInstance(mLife)).commit();
                }
            });
            timer.schedule(timerTask, 0, 1000);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((LifeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
            mLife = (Life) getArguments().getSerializable(SERIALIZABLE_LIFE);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            timerTask.cancel();
            timer.cancel();
        }
    }

}
