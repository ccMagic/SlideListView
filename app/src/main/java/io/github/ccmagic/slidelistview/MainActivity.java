package io.github.ccmagic.slidelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SlideListView slideListView = findViewById(R.id.slide_list_view);
        final View root = findViewById(R.id.root);
        List<String> stringList = new ArrayList<>();
        stringList.add("tinker");
        stringList.add("影魔");
        stringList.add("痛苦女王");
        stringList.add("拉席克");
        stringList.add("丽娜");
        stringList.add("卡尔");
        stringList.add("灰烬之灵");
        stringList.add("敌法师");
        stringList.add("巨魔战将");
        stringList.add("主宰");
        stringList.add("混沌骑士");
        stringList.add("末日使者");
        stringList.add("先知");
        stringList.add("熊猫酒仙");
        stringList.add("昆卡");
        stringList.add("潮汐猎人");
        stringList.add("斯拉克");
        stringList.add("斯拉达");
        stringList.add("发条技师");
        stringList.add("伐木机");
        stringList.add("幻影刺客");
        stringList.add("灵魂守卫");
        stringList.add("死灵龙");
        stringList.add("天怒法师");
        stringList.add("复仇之魂");
        stringList.add("冥界亚龙");
        stringList.add("骨法");
        //
        MyAdapter myAdapter = new MyAdapter(this, stringList);
        slideListView.setAdapter(myAdapter);
        //
        slideListView.post(new Runnable() {
            @Override
            public void run() {
                //设置滑动的距离限制，最大高度减去最小高度，为0的时候将导致滑动效果失效
                slideListView.set(root.getMeasuredHeight() - slideListView.getMeasuredHeight());
            }
        });

        slideListView.setLoadListener(new SlideListView.LoadListener() {
            @Override
            public void onLoad() {
                Toast.makeText(MainActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
