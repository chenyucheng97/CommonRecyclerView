package com.example.unicorn.commonrecyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.unicorn.commonrecyclerview.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv1,tv2;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.ll);
        tv1 = (TextView) findViewById(R.id.tv_single);
        tv2 = (TextView) findViewById(R.id.tv_multi);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.tv_single:
                startActivity(new Intent(this,RecycleViewTestActivity.class));
                break;
            case R.id.tv_multi:
                startActivity(new Intent(this,MultiItemRvActivity.class));
                break;
            default:
                break;
        }
    }
}
