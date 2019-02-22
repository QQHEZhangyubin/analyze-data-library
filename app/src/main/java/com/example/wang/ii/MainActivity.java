package com.example.wang.ii;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UpLoadPinnedHeaderListView.IXListViewListener {
    private ImageView Header1Img;
    private List<TaskModelBean> taskList;
    private static int count = 0;
    List<String> group;
    TaskModelBean modelBean;
    int sectionNow = 10000;
    int positionNow = 10000;
    Handler mHandler = new Handler();

    // // 设置自定义listview
    UpLoadPinnedHeaderListView pinnedlistView;
    TestSectionedAdapter sectionedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oa_nzl_main_view_taskmodel);
        group = new ArrayList<String>();
        group.add("一月");
        group.add("二月");
        group.add("三月");
        group.add("四月");
        taskList = new ArrayList<TaskModelBean>();
        addItem();
        initView();
    }

    private void initView() {
        pinnedlistView = (UpLoadPinnedHeaderListView) findViewById(R.id.pinnedListView);
        pinnedlistView.setXListViewListener(this);
        pinnedlistView.setPullLoadEnable(true);
        sectionedAdapter = new TestSectionedAdapter(this, taskList, group,Header1Img);
        pinnedlistView.setAdapter(sectionedAdapter);
    }

    private void addItem() {
        for (int i = 0; i < group.size(); i++) {
            modelBean = new TaskModelBean();
            modelBean.setGroupName(group.get(i));
            List<String> test = new ArrayList<String>();
            for (int j = 0; j <4; j++) {
                test.add(group.get(i)+" "+j);
                modelBean.setItemName(test);
            }
            taskList.add(modelBean);
        }
    }
    private void onLoad(boolean isOver) {
        if(isOver){
            pinnedlistView.setPullLoadEnable(false);
        }else{

            pinnedlistView.setPullLoadEnable(true);
            pinnedlistView.stopLoadMore();
        }
        pinnedlistView.stopRefresh();
        pinnedlistView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        if(count >=3){
            count =0;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                taskList.clear();
                addItem();
                sectionedAdapter.notifyDataSetChanged();
                onLoad(false);
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count++;
                if(count >= 3){
                    onLoad(true);
                    sectionedAdapter.notifyDataSetChanged();
                }else{
                    addItem();
                    sectionedAdapter.notifyDataSetChanged();
                    onLoad(false);
                }
            }
        }, 2000);

    }

}
