package com.marbles.sagar.phone_wifi;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class wel_activity extends AppCompatActivity implements View.OnClickListener {
private ViewPager viewPager;
private int[] layouts = {R.layout.new_frame_layout,R.layout.sec_slide,R.layout.thrid_layout,R.layout.forth_layout};
private MpagerAdapter mpagerAdapter;
    private boolean exit =false;

private LinearLayout dots_layout;
private ImageView[] dots;
private Button next,skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new Prefrencemanager(this).checkPref())
        {
            loadHome();
        }


        setContentView(R.layout.activity_wel_activity);
            if(Build.VERSION.SDK_INT>19)
            {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            else
            {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        viewPager = findViewById(R.id.view_pager);
            next=(Button)findViewById(R.id.btnnxt);
        skip=(Button)findViewById(R.id.btnskip);
        next.setOnClickListener(this);
        skip.setOnClickListener(this);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        viewPager.setAdapter(mpagerAdapter);

        dots_layout =(LinearLayout)findViewById(R.id.dots_layout);
        createDots(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position==layouts.length-1)
                {
                    next.setText("Start");
                    skip.setVisibility(View.INVISIBLE);

                    }
                    else
                {
                    next.setText("Next");
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    private  void createDots(int cuurPos)
    {
        if(dots_layout!=null)
        {
           dots_layout.removeAllViews();
        }
        dots = new ImageView[layouts.length];
        for(int i=0; i<layouts.length;i++)
        {
            dots[i]= new ImageView(this);
            if(i==cuurPos)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
            }


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT

                    );


            params.setMargins(4,0,4,0);


            dots_layout.addView(dots[i],params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnnxt:
                loadNext();
                break;

            case R.id.btnskip:
                loadHome();
                new Prefrencemanager(this).writePref();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(exit)
        {

            super.onBackPressed();

            finish();
            System.exit(0);

            return ;

        }
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        exit=true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit=false;

            }
        },5*1000);

    }

    private void loadHome(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    private void loadNext()
    {
        int next = viewPager.getCurrentItem()+1;
        if(next<layouts.length)
        {
            viewPager.setCurrentItem(next);
        }
        else
        {
            loadHome();
            new Prefrencemanager(this).writePref();
        }
    }
}
