package com.example.misl_jh.fianl;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class Dialog_read extends Dialog {

    TextView read_title, read_content;
    String str;
    String title;

    Button btnBack;

    public Dialog_read(final Context context) {
        super(context);




        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));    //다이얼로그의 배경을 투명으로
        setContentView(R.layout.dialog_read);

        read_title = (TextView) findViewById(R.id.read_title);
        read_content = (TextView) findViewById(R.id.read_content);

        btnBack = (Button) findViewById(R.id.btn_back);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_read.super.onBackPressed();
            }
        });
    }
}
