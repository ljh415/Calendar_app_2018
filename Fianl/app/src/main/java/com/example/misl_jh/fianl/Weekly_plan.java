package com.example.misl_jh.fianl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Calendar;

public class Weekly_plan extends Fragment implements View.OnClickListener {

    ScrollView scroll;                      //스크롤뷰

    GridLayout weekCalendar;
    Button[] btnDay;

    ArrayList<String> dayList;
    String[][] weekDay;

    Calendar cal;

    Dialog_input di;

    int btnWidth, dayNum;
    int x;

    public static Weekly_plan newstance(){
        return new Weekly_plan();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cal = ((MainActivity)getActivity()).mCal;           //메인 액티비티의  mCal을
        dayNum = cal.get(Calendar.DAY_OF_WEEK);

        dayList = new ArrayList<String>();
        dayList.add("");
        dayList.add("Sun");
        dayList.add("Mon");
        dayList.add("Tue");
        dayList.add("Wed");
        dayList.add("Thu");
        dayList.add("Fri");
        dayList.add("Sat");

        weekDay = new String[8][8];
        weekDay[7][7] = "";


        //1일 - 요일 매칭시키기 위해 공백 add
        for(int i = 1 ; i < dayNum ; i++) {
            dayList.add("");
        }
        setCalendarDate(cal.get(Calendar.MONTH)+1);


        //gridlayout(week)

        weekCalendar = new GridLayout(getActivity());
        weekCalendar.setColumnCount(8);
        weekCalendar.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        btnDay = new Button[208];

        //get Window size

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        // set width for button
        btnWidth = width / 8;

            for (int j = 0 ; j < 7 ; j++) {
                for (int i = 1 ; i < 8 ; i++) {
                    if(((j*7)+i) == ((MainActivity)getActivity()).mCal.getActualMaximum(Calendar.DAY_OF_MONTH)+dayNum) {
                        i=8;
                        j=7;
                    }
                    else
                        weekDay[j][i] = dayList.get((j*7)+i+7);
                }
            }


        for(int i = 0; i < 8 ; i++) {              // 맨 윗 줄 요일 출력

            btnDay[i] = new Button(getActivity());
            btnDay[i].setBackgroundColor(Color.GRAY);
            btnDay[i].setId((i+1));
            btnDay[i].setText(dayList.get(i));


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnWidth, GridLayout.LayoutParams.WRAP_CONTENT);
            btnDay[i].setLayoutParams(params);
            btnDay[i].setOnClickListener(this);
            weekCalendar.addView(btnDay[i]);

            //일요일 빨간색, 토요일 파란색
            if (i==1) {
                btnDay[i].setTextColor(Color.RED);
            } else if (i == 7) {
                btnDay[i].setTextColor(Color.BLUE);
            }

            if (i == 0) {
                btnDay[i].setBackgroundColor(Color.BLACK);
                btnDay[i].setTextColor(Color.WHITE);
                btnDay[i].setText("요일");
            }

        }

        x=((MainActivity)getActivity()).week;

        for(int i = 8; i < 16 ; i++) {
            btnDay[i] = new Button(getActivity());
            btnDay[i].setBackgroundColor(Color.DKGRAY);
            btnDay[i].setTextColor(Color.WHITE);
            btnDay[i].setId((i+1));
            btnDay[i].setText(weekDay[x][i-8]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnWidth, GridLayout.LayoutParams.WRAP_CONTENT);
            btnDay[i].setLayoutParams(params);
            btnDay[i].setOnClickListener(this);
            weekCalendar.addView(btnDay[i]);

            if (i == 8) {
                btnDay[i].setBackgroundColor(Color.BLACK);
                btnDay[i].setTextColor(Color.WHITE);
                btnDay[i].setText(String.valueOf(((MainActivity)getActivity()).week+1)+"주차");
            }
        }


        for(int i = 16; i<208; i++) {
            btnDay[i] = new Button(getActivity());
            btnDay[i].setId((i+1));


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnWidth, GridLayout.LayoutParams.WRAP_CONTENT);
            btnDay[i].setLayoutParams(params);
            btnDay[i].setOnClickListener(this);
            weekCalendar.addView(btnDay[i]);

            if(i%8==0){
                btnDay[i].setBackgroundColor(Color.WHITE);
                btnDay[i].setTextSize(12);
                btnDay[i].setText(String.valueOf(i/8-2)+":00 ~ " + String.valueOf(i/8-1)+":00");
            }
        }

        //다이얼로그화면
        di = new Dialog_input(getActivity());
        WindowManager.LayoutParams wm = di.getWindow().getAttributes();
        wm.copyFrom(di.getWindow().getAttributes());
        wm.width = width/3*2;
        wm.height = height/3*2;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.week_plan, container, false);

        scroll = (ScrollView) v.findViewById(R.id.Scroll);

        scroll.addView(weekCalendar);

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //00시 ~ 01시
            case 18:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 19:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 20:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 21:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 22:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 23:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 24:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("00");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("01");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;


            //01시 ~ 02시
            case 26:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 27:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 28:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 29:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 30:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 31:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 32:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("01");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("02");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //02시 ~ 03시
            case 34:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 35:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 36:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 37:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 38:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 39:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 40:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("02");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("03");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //03시~04시
            case 42:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 43:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 44:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 45:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 46:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 47:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 48:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("03");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("04");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //04시~05시
            case 50:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 51:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 52:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 53:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 54:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 55:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 56:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("04");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("05");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //05시~06시
            case 58:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 59:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 60:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 61:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 62:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 63:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 64:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("05");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("06");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //06시~07시
            case 66:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 67:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 68:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 69:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 70:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 71:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 72:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("06");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("07");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //07시~08시
            case 74:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 75:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 76:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 77:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 78:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 79:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 80:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("07");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("08");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //08시~09시
            case 82:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 83:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 84:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 85:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 86:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 87:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 88:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("08");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("09");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //09시~10시
            case 90:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 91:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 92:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 93:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 94:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 95:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 96:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("09");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("10");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



            //10시~11시
            case 98:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 99:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 100:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 101:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 102:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 103:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 104:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("10");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("11");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//            11시~12시
            case 106:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 107:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 108:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 109:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 110:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 111:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 112:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("11");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("12");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//12시~1시
            case 114:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 115:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 116:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 117:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 118:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 119:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 120:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("12");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("13");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//13시~14시
            case 122:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 123:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 124:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 125:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 126:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 127:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 128:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("13");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("14");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//14시~15시
            case 130:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 131:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 132:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 133:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 134:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 135:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 136:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("14");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("15");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//15시~16시
            case 138:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 139:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 140:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 141:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 142:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 143:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 144:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("15");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("16");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//16시~17시
            case 146:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 147:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 148:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 149:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 150:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 151:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 152:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("16");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("17");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//17시~18시
            case 154:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 155:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 156:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 157:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 158:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 159:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 160:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("17");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("18");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//18시~19시
            case 162:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 163:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 164:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 165:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 166:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 167:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 168:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("18");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("19");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//19시~20시
            case 170:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 171:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 172:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 173:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 174:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 175:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 176:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("19");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("20");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//20시~21시
            case 178:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("20");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 179:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("20");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 180:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("20");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 181:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("20");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 182:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("20");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 183:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 184:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("20");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("21");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//21시~22시
            case 186:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 187:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 188:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 189:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 190:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 191:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 192:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("21");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("22");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//22시~23시
            case 194:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 195:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 196:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 197:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 198:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 199:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 200:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("22");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("23");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;



//23시~24시
            case 202:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][1]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][1]);
                di.show();
                break;
            case 203:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][2]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][2]);
                di.show();
                break;
            case 204:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][3]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][3]);
                di.show();
                break;
            case 205:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][4]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][4]);
                di.show();
                break;
            case 206:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][5]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][5]);
                di.show();
                break;
            case 207:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][6]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][6]);
                di.show();
                break;
            case 208:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_hour.setText("23");
                di.edit_start_date.setText(weekDay[x][7]);

                di.edit_end_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_end_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_end_hour.setText("24");
                di.edit_end_date.setText(weekDay[x][7]);
                di.show();
                break;
        }
    }

    private void setCalendarDate(int month) {
        cal.set(Calendar.MONTH, month - 1);
        for (int i = 0 ; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH) ; i++) {
            dayList.add("" + (i+1));
        }
    }
}
