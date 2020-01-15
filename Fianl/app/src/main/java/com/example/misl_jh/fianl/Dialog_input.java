package com.example.misl_jh.fianl;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Dialog_input extends Dialog {

    EditText edit_title, edit_place, edit_memo;
    EditText edit_end_year, edit_end_month, edit_end_date, edit_end_hour;
    EditText edit_start_year, edit_start_month, edit_start_date, edit_start_hour;
    Button btn_save, btn_read, btn_delete;
    Calendar cal;

    String title, title_r;
    String str;
    String read;

    Dialog_read dr;


    public static Dialog_input mContext;

    public Dialog_input(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //다이얼로그에서 사용할 레이아웃
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));    //다이얼로그의 배경을 투명으로 만든다.
        setContentView(R.layout.dialog_input);

        mContext=this;

        cal = Calendar.getInstance();

        edit_title = (EditText) findViewById(R.id.edit_Title);

        edit_start_year = (EditText) findViewById(R.id.edit_Start_Year);
        edit_start_month = (EditText) findViewById(R.id.edit_Start_Month);
        edit_start_date = (EditText) findViewById(R.id.edit_Start_Date);
        edit_start_hour = (EditText) findViewById(R.id.edit_Start_Hour);

        edit_end_year = (EditText) findViewById(R.id.edit_End_Year);
        edit_end_month = (EditText) findViewById(R.id.edit_End_Month);
        edit_end_date = (EditText) findViewById(R.id.edit_End_Date);
        edit_end_hour = (EditText) findViewById(R.id.edit_End_Hour);

        edit_place = (EditText) findViewById(R.id.edit_Place);
        edit_memo = (EditText) findViewById(R.id.edit_Memo);

        btn_save = (Button) findViewById(R.id.btn_Save);
        btn_read = (Button) findViewById(R.id.btn_Read);
        btn_delete = (Button) findViewById(R.id.btn_Delete);

        edit_start_year.setText("");
        edit_start_month.setText("");
        edit_start_date.setText("");
        edit_start_hour.setText("");

        //윈도우 사이즈
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        //읽어들이는 다이얼로그 화면
        dr = new Dialog_read(context);
        WindowManager.LayoutParams wm = dr.getWindow().getAttributes();
        wm.copyFrom(dr.getWindow().getAttributes());
        wm.width = width/3*2;
        wm.height = height/3*2;


        //다이얼로그 저장버튼
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //
                setTitle();
                try {
                    FileOutputStream outFs = context.openFileOutput(title, Context.MODE_PRIVATE);
                    str = "제목 : " + edit_title.getText() + "\r\n일정 : " + edit_start_year.getText() + "." + edit_start_month.getText() + "." + edit_start_date.getText() + ". ~ " + edit_end_year.getText() + "." +
                            edit_end_month.getText() + "." + edit_end_date.getText() + ".\r\n장소 : " + edit_place.getText() + "\r\n메모 : " + edit_memo.getText();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(context.getApplicationContext(), title+"가 생성됨", Toast.LENGTH_SHORT).show();
                    edit_title.setText("");
                    edit_start_year.setText("");
                    edit_start_month.setText("");
                    edit_start_date.setText("");
                    edit_start_hour.setText("");
                    edit_end_year.setText("");
                    edit_end_month.setText("");
                    edit_end_date.setText("");
                    edit_end_hour.setText("");
                    edit_place.setText("");
                    edit_memo.setText("");
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                dismiss();  //다이얼로그를 닫는 메소드
            }
        });

        //다이얼로그 삭제버튼
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle();

                if (edit_start_hour.equals("")) {
                    Toast.makeText(context.getApplicationContext(), "입력된 값이 없습니다.\n  입력을 취소합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), title + " 가 삭제됨", Toast.LENGTH_SHORT).show();
                    context.deleteFile(title);
                }
                edit_title.setText("");
                edit_start_year.setText("");
                edit_start_month.setText("");
                edit_start_date.setText("");
                edit_start_hour.setText("");
                edit_end_year.setText("");
                edit_end_month.setText("");
                edit_end_date.setText("");
                edit_end_hour.setText("");
                edit_place.setText("");
                edit_memo.setText("");
                dismiss();  //다이얼로그를 닫는 메소드
            }
        });

        //다이얼로그 읽기버튼
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle();
                setTitle_r();
                try {
                    FileInputStream inFs = context.openFileInput(title);
                    byte[] txt = new byte[1000];
                    inFs.read(txt);
                    read = new String(txt);
                    Toast.makeText(context.getApplicationContext(), title+" 의 일정을 불러옵니다.", Toast.LENGTH_SHORT).show();

                    dr.read_title.setText(title_r);
                    dr.read_content.setText(read);
                    dr.show();

                    inFs.close();
                } catch (IOException e) {
                    Toast.makeText(context.getApplicationContext(), "해당 일정이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //저장할 때 쓸 title
    void setTitle() {
        String year = edit_start_year.getText().toString();
        String month = edit_start_month.getText().toString();
        String date = edit_start_date.getText().toString();
        String hour = edit_start_hour.getText().toString();
        title = year+"_"+month+"_"+date+"_"+hour+"시.txt";
    }

    //읽어 들일 때 쓸 title
    void setTitle_r() {
        String year = edit_start_year.getText().toString();
        String month = edit_start_month.getText().toString();
        String date = edit_start_date.getText().toString();
        String hour = edit_start_hour.getText().toString();
        title_r = year+"."+month+"."+date+"\n"+hour+"시의 일정";
    }
}
