package com.vessel.gather.module;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vessel.gather.R;
import com.vessel.gather.widght.MyImageAdapter;
import com.vessel.gather.widght.PhotoViewPager;

import java.util.List;

/**
 * @author vesselzhang
 * @date 2018/7/5
 */
public class PhotoViewActivity extends AppCompatActivity {
    private PhotoViewPager mViewPager;
    private LinearLayout mBack;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private List<String> Urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initView();
        initData();
    }

    private void initView() {
        mBack = findViewById(R.id.back);
        mViewPager = findViewById(R.id.view_pager_photo);
        mTvImageCount = findViewById(R.id.tv_image_count);
    }

    private void initData() {
        currentPosition = getIntent().getIntExtra("currentPosition", 0);
        Urls = (List<String>) getIntent().getSerializableExtra("dataBean");

        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });
        mBack.setOnClickListener(view -> finish());
    }
}