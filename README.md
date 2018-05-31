# SlideListView

#### 项目介绍
可以上下滑动在一定范围内改变高度的ListView，不与Adapter项滑动冲突。当往上滑时，高度跟随手势变化，到达高度最大值，开始滑动Adapter项。在最大高度时向下滑动，当Adapter项滑动到最上面，开始随手势改变ListView高度。另外向上拉动时，当到达最底部会触发Load监听，可用于网络加载数据时的分页加载更多数据

[源代码:https://github.com/ccMagic/SlideListView](https://github.com/ccMagic/SlideListView)

[源代码:https://gitee.com/ccMagic/SlideListView](https://gitee.com/ccMagic/SlideListView)

#### 使用说明

1. 设置Adapter与ListView一致
2. 在view.post方法中设置滑动的距离限制，最大高度减去最小高度，为0的时候将导致滑动效果失效

        slideListView.post(new Runnable() {
               @Override
               public void run() {
                   //设置滑动的距离限制，最大高度减去最小高度，为0的时候将导致滑动效果失效
                   slideListView.set(root.getMeasuredHeight() - slideListView.getMeasuredHeight());
               }
           });
3. 设置滑动到最底部的监听

           slideListView.setLoadListener(new SlideListView.LoadListener() {
               @Override
               public void onLoad() {
                   Toast.makeText(MainActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
               }
           });

#### 效果展示
![image](http://wx2.sinaimg.cn/large/bcc7d265gy1frj1z7vmkxg20go0tn7wj.gif)
