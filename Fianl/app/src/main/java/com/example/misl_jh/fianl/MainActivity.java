package com.example.misl_jh.fianl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout baseLayout;

    Button BtnBack, BtnNext;
    Button ContextWeek;

    TextView DateYear, DateMonth;

    Calendar mCal;
    Calendar cCal;

    int week;
    int cYear, cMonth, cDate;

    public static MainActivity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("14051032 이재호");

        mContext=this;

        baseLayout = (LinearLayout) findViewById(R.id.baseLayout);
        BtnBack = (Button) findViewById(R.id.contextYear);
        BtnNext = (Button) findViewById(R.id.contextMonth);
        registerForContextMenu(BtnBack);
        registerForContextMenu(BtnNext);
        DateYear = (TextView) findViewById(R.id.dateYear);
        DateMonth = (TextView) findViewById(R.id.dateMonth);
        ContextWeek = (Button) findViewById(R.id.contextWeek);
        registerForContextMenu(ContextWeek);


        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDateFormat = new SimpleDateFormat("dd", Locale.KOREA);

        mCal = Calendar.getInstance();
        cCal = Calendar.getInstance();          // 현재날짜 저장

        cYear = Integer.parseInt(curYearFormat.format(date));
        cMonth = Integer.parseInt(curMonthFormat.format(date))-1;
        cDate = Integer.parseInt(curDateFormat.format(date));

        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date))-1,1);
        cCal.set(cYear, cMonth, cDate);


        final Monthly_plan monthly_plan = new Monthly_plan();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, monthly_plan);
        fragmentTransaction.commit();

        final Monthly_plan fragment = (Monthly_plan)fragmentManager.findFragmentById(R.id.MonthPlan_frag);


        fragmentTransaction.show(Monthly_plan.newstance());
        fragmentTransaction.hide(Weekly_plan.newstance());


        DateYear.setText(mCal.get(Calendar.YEAR) + "년");
        DateMonth.setText(mCal.get(Calendar.MONTH)+1 + "월");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        if (v == BtnBack) {
            menu.setHeaderTitle("년도 선택");

            menuInflater.inflate(R.menu.menu_year, menu);
        }

        if (v == BtnNext) {
            menu.setHeaderTitle("월 선택");

            menuInflater.inflate(R.menu.menu_month, menu);
        }

        if (v == ContextWeek) {

            menu.setHeaderTitle("주 선택");

            menuInflater.inflate(R.menu.menu_week, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.Y2016:
                mCal.set(2016, mCal.get(Calendar.MONTH), 1);
                DateYear.setText(mCal.get(Calendar.YEAR)+"년");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.Y2017:
                mCal.set(2017, mCal.get(Calendar.MONTH), 1);
                DateYear.setText(mCal.get(Calendar.YEAR)+"년");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.Y2018:
                mCal.set(2018, mCal.get(Calendar.MONTH), 1);
                DateYear.setText(mCal.get(Calendar.YEAR)+"년");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.Y2019:
                mCal.set(2019, mCal.get(Calendar.MONTH), 1);
                DateYear.setText(mCal.get(Calendar.YEAR)+"년");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.Y2020:
                mCal.set(2020, mCal.get(Calendar.MONTH), 1);
                DateYear.setText(mCal.get(Calendar.YEAR)+"년");
                replaceFragment(Monthly_plan.newstance());
                return true;


            case R.id.M1:
                mCal.set(mCal.get(Calendar.YEAR), 0, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M2:
                mCal.set(mCal.get(Calendar.YEAR), 1, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M3:
                mCal.set(mCal.get(Calendar.YEAR), 2, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M4:
                mCal.set(mCal.get(Calendar.YEAR), 3, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M5:
                mCal.set(mCal.get(Calendar.YEAR), 4, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M6:
                mCal.set(mCal.get(Calendar.YEAR), 5, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M7:
                mCal.set(mCal.get(Calendar.YEAR), 6, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M8:
                mCal.set(mCal.get(Calendar.YEAR), 7, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M9:
                mCal.set(mCal.get(Calendar.YEAR), 8, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M10:
                mCal.set(mCal.get(Calendar.YEAR), 9, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M11:
                mCal.set(mCal.get(Calendar.YEAR), 10, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;
            case R.id.M12:
                mCal.set(mCal.get(Calendar.YEAR), 11, 1);
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());
                return true;

            case R.id.week1:
                week = 0;
                replaceFragment(Weekly_plan.newstance());
                return true;
            case R.id.week2:
                week = 1;
                replaceFragment(Weekly_plan.newstance());
                return true;
            case R.id.week3:
                week = 2;
                replaceFragment(Weekly_plan.newstance());
                return true;
            case R.id.week4:
                week = 3;
                replaceFragment(Weekly_plan.newstance());
                return true;
            case R.id.week5:
                week = 4;
                replaceFragment(Weekly_plan.newstance());
                return true;
            case R.id.week6:
                week = 5;
                replaceFragment(Weekly_plan.newstance());
                return true;


        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.optionMonth:
                replaceFragment(Monthly_plan.newstance());
                return true;

            case R.id.optionWeek:
                replaceFragment(Weekly_plan.newstance());
                return true;

            case R.id.optionToday:
                mCal.set(cYear, cMonth, 1);
                DateYear.setText(mCal.get(Calendar.YEAR)+"년");
                DateMonth.setText(mCal.get(Calendar.MONTH)+1+"월");
                replaceFragment(Monthly_plan.newstance());

        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment).commit();

    }


}
