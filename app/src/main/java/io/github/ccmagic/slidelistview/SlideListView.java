package io.github.ccmagic.slidelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by ccMagic on 2018/4/10.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：内外滑动状态切换的ListView,包括上拉加载更多
 */
public class SlideListView extends ListView {

    private static final String TAG = "SlideListView";

    /**
     * 上一个点的高度位置
     */
    private float prePoint;

    /**
     * 当前点击位置的Y轴值
     */
    private float mCurrentPointY;
    /**
     * 滑动的距离限制
     */
    private float mDistanceLimit;
    /**
     * 当前滑动操作滑动了的距离
     */
    private float mSlideSingleDistance;


    private boolean isFull = false;
    private boolean isDefault = true;

    /**
     * 初始高度
     */
    private int mDefaultHeight;
    /**
     * 满屏时高度
     */
    private int mFullHeight;
    private ViewGroup.LayoutParams mLayoutParams;
    /**
     * 显示的最上面的View
     */
    private View mFirstView;
    /**
     * 组件是否向上滑动
     */
    private boolean mUpSlide = true;

    /**
     * 是否初始化各个数据成功，没有成功的情况下 不能动态改变高度
     * */
    private boolean setSuccess = false;

    private LoadListener mLoadListener;

    public SlideListView(Context context) {
        this(context, null);
    }

    public SlideListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlideListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    /**
     * 因为要获取view的测绘的高度，所以必须在post()方法中调用此方法
     *
     * @param distanceLimit 滑动的距离限制，最大高度减去最小高度
     *                      distanceLimit为0的时候将导致滑动效果失效
     */
    public void set(int distanceLimit) {
        mDistanceLimit = distanceLimit;
        mDefaultHeight = getMeasuredHeight();
        mFullHeight = (int) (mDefaultHeight + mDistanceLimit);
        mLayoutParams = getLayoutParams();
        setSuccess = true;
    }

    @Override
    public boolean performClick() {
        //
        super.performClick();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        performClick();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if(setSuccess){
                    mCurrentPointY = ev.getRawY();
                    //当布局没有填充完整界面时
                    mUpSlide = prePoint > mCurrentPointY;
                    if (isFull) { //满屏
//                    Log.i(TAG, "onTouchEvent: 满屏");
                        if (mUpSlide) {//往上滑动
//                        Log.i(TAG, "onTouchEvent: 满屏 往上滑动");
                            //上滑浏览更多选项
                            prePoint = mCurrentPointY;
                            return super.onTouchEvent(ev);
                        } else { //往下滑动
//                        Log.i(TAG, "onTouchEvent: 满屏 往下滑动");
                            if (getChildCount() > 0) {
                                //因为View的复用，必须每次重新获取
                                mFirstView = getChildAt(0);
                            }
//                        if (mFirstView != null) {
//                            Log.i(TAG, "onTouchEvent mFirstView.getY(): " + mFirstView.getY());
//                        } else {
//                            Log.i(TAG, "onTouchEvent: mFirstView is null");
//                        }
//                        Log.i(TAG, "onTouchEvent getChildCount(): " + getChildCount());
                            if ((getFirstVisiblePosition() == 0
                                    && mFirstView != null
                                    && mFirstView.getY() == 0.0) || getChildCount() == 0) {//在ListView的所有Item的第一个Item在最顶部显示完全的时候，或者数据为空时，动态改变高度
//                            Log.i(TAG, "onTouchEvent: 第一个Item在最顶部显示完全 ");
                                actionMove();
                                return true;
                            } else {
//                            Log.i(TAG, "onTouchEvent: 正常下拉 ");
                                prePoint = mCurrentPointY;
                                return super.onTouchEvent(ev);
                            }
                        }
                    } else if (isDefault) {//初始位置
//                    Log.i(TAG, "onTouchEvent: 初始位置");
                        if (mUpSlide) { //往上滑动
//                        Log.i(TAG, "onTouchEvent: 初始位置 往上滑动");
                            actionMove();
                            return true;
                        } else { //往下滑动
//                        Log.i(TAG, "onTouchEvent: 初始位置 往下滑动");
                            prePoint = mCurrentPointY;
                            return super.onTouchEvent(ev);
                        }
                    } else {
//                    Log.i(TAG, "onTouchEvent: 动态变化高度");
                        actionMove();
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
                if (isFull) {
                    if (mLoadListener != null && getAdapter() != null && getLastVisiblePosition() == (getAdapter().getCount() - 1)) {
                        //上滑到底部，则进行更多的数据加载
                        mLoadListener.onLoad();
                    }
                    actionUp();
                    return true;
                } else {
                    return super.onTouchEvent(ev);
                }
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void actionDown(MotionEvent event) {
        prePoint = event.getRawY();
    }

    private void actionMove() {
        //本次滑动了的距离
        mSlideSingleDistance = prePoint - mCurrentPointY;
        //直接滑动到满屏
        //高度动态变化,在此直接定义一个局部变量h，是为了保证高度变化的平滑
        int h = (int) (getMeasuredHeight() + mSlideSingleDistance);
        if (h >= mFullHeight) {
            h = mFullHeight;
            isFull = true;
            isDefault = false;
        } else if (h <= mDefaultHeight) {
            h = mDefaultHeight;
            isFull = false;
            isDefault = true;
        } else {
            isFull = false;
            isDefault = false;
        }
        mLayoutParams.height = h;
        setLayoutParams(mLayoutParams);
        prePoint = mCurrentPointY;
    }

    private void actionUp() {
    }

    public void setLoadListener(LoadListener loadListener) {
        mLoadListener = loadListener;
    }

    public interface LoadListener {
        void onLoad();
    }
}
