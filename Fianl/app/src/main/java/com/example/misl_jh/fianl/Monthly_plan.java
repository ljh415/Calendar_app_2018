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
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;


public class Monthly_plan extends Fragment implements View.OnClickListener {

    TextView b;
    LinearLayout baseFrame;

    GridLayout monthCalendar;
    Button[] btnDay;

    ArrayList<String> dayList;

    Dialog_input di;

    Calendar cal;
    Calendar currentCal;

    int btnWidth, dayNum;
    int A;

    public static Monthly_plan newstance() { return new Monthly_plan(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cal=((MainActivity)getActivity()).mCal;

        currentCal=((MainActivity)getActivity()).cCal;

        dayNum = cal.get(Calendar.DAY_OF_WEEK);

        dayList = new ArrayList<String>();
        dayList.add("Sun");
        dayList.add("Mon");
        dayList.add("Tue");
        dayList.add("Wed");
        dayList.add("Thu");
        dayList.add("Fri");
        dayList.add("Sat");



        //1일 - 요일 매칭시키기 위해 공백 add
        for(int i = 1 ; i < dayNum ; i++) {
            dayList.add("");
        }
        setCalendarDate(cal.get(Calendar.MONTH)+1);

        //gridlayout (month)

        monthCalendar = new GridLayout(getActivity());
        monthCalendar.setColumnCount(7);
        monthCalendar.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        btnDay = new Button[49];


        //  get Window size
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        //  set width for button
        btnWidth = width / 7;

        for (int i = 0; i < 7; i++) {


            btnDay[i] = new Button(getActivity());
            btnDay[i].setBackgroundColor(Color.GRAY);
            btnDay[i].setId((i + 1));
            btnDay[i].setText(dayList.get(i));


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnWidth, GridLayout.LayoutParams.WRAP_CONTENT);    // 버튼추가
            btnDay[i].setLayoutParams(params);
            btnDay[i].setOnClickListener(this);
            monthCalendar.addView(btnDay[i]);

            if (i==0) {
                btnDay[i].setTextColor(Color.RED);
            } else if (i == 6) {
                btnDay[i].setTextColor(Color.BLUE);
            }
        }


        for (int i = 7; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH)+6+dayNum; i++) {


            btnDay[i] = new Button(getActivity());
            btnDay[i].setBackgroundColor(Color.WHITE);
            btnDay[i].setId((i + 1));
            btnDay[i].setText(dayList.get(i));


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(btnWidth, GridLayout.LayoutParams.WRAP_CONTENT);    // 버튼추가
            btnDay[i].setLayoutParams(params);
            btnDay[i].setOnClickListener(this);
            monthCalendar.addView(btnDay[i]);
            if (i%7==0) {
                btnDay[i].setTextColor(Color.RED);
            }
            else if (i%7==6) {
                btnDay[i].setTextColor(Color.BLUE);
            }
            if ((cal.get(Calendar.YEAR)==currentCal.get(Calendar.YEAR)) && (cal.get(Calendar.MONTH)==currentCal.get(Calendar.MONTH)) && i == ((dayNum+5)+currentCal.get(Calendar.DATE))) {
                btnDay[i].setBackgroundColor(Color.YELLOW);
            }
        }


        //다이얼로그화면
        di = new Dialog_input(getActivity());
        WindowManager.LayoutParams wm = di.getWindow().getAttributes();
        wm.copyFrom(di.getWindow().getAttributes());
        wm.width = width/3*2;
        wm.height = height/3*2;

        A = 7+(dayNum-1);

    }

    //해당 월에 표시할 일 수 구하기
    private void setCalendarDate(int month) {
        cal.set(Calendar.MONTH, month - 1);
        for (int i = 0 ; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH) ; i++) {
            dayList.add("" + (i+1));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 8:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(7));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 9:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(8));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 10:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(9));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 11:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(10));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 12:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(11));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 13:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(12));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 14:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(13));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 15:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(14));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 16:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(15));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 17:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(16));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 18:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(17));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 19:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(18));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 20:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(19));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 21:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(20));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 22:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(21));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 23:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(22));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 24:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(23));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 25:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(24));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 26:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(25));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 27:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(26));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 28:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(27));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 29:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(28));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 30:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(29));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 31:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(30));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 32:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(31));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 33:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(32));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 34:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(33));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 35:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(34));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 36:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(35));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 37:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(36));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 38:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(37));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 39:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(38));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 40:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(39));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 41:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(40));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 42:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(41));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 43:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(42));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 44:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(43));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 45:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(44));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 46:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(45));
                di.show();
                break;
            case 47:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(46));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 48:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(47));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 49:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(48));
                di.edit_start_hour.setText("00");
                di.show();
                break;
            case 50:
                di.edit_start_year.setText(String.valueOf(cal.get(Calendar.YEAR)));
                di.edit_start_month.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
                di.edit_start_date.setText(dayList.get(49));
                di.edit_start_hour.setText("00");
                di.show();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.month_plan, container, false);

        b = (TextView) v.findViewById(R.id.dateYear) ;

        baseFrame = (LinearLayout) v.findViewById(R.id.MonthPlan_frag);
        baseFrame.addView(monthCalendar);


        return v;
    }

}
