package com.zhy.practice;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.ccbCricleMenu.R;

import java.util.jar.Attributes;

public class CircleMenuLayoutPrictice extends ViewGroup {
    //layout的半径
    private int mRadius;
    private float mMaxChildDimensionRadius = 1 / 4f;  //周围项目的最大半径维度
    private float mCenterItemDimesionRadious = 1/3f;
    /*一个用于加载布局的系统服务，
    就是实例化与Layout XML文件对应的View对象，不能直接使用，
     需要通过getLayoutInflater( )方法或getSystemService( )方法来获得与当前
     Context绑定的 LayoutInflater实例!*/
    private LayoutInflater mInflater;
    private double mStartAngle = 0;  //设置角度

    private String[] mItemTexts = new String[]{"HTML", "CSS", "JS",
            "JQuery", "DOM", "TEMPLETE"};
    private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal };
    private int mTouchSlop;
    /**
     * 加速度检测
     */

    private float mDownAngle;
    private float mTmpAngle;
    private long mDownTime;
    private boolean isFling;
    //定义和创建组件
    public CircleMenuLayoutPrictice(Context context, AttributeSet attrs){
        super(context,attrs);
        //动态加载一个布局文件。。将我们的布局文件封装成一个view对象
        mInflater = LayoutInflater.from(context);
        for(int i = 0; i < mItemImgs.length; i++)
        {
            final int j = i;
            /*public View inflate (int resource, ViewGroup root, boolean attachToRoot) 该方法的三个参数依次为:
            ①要加载的布局对应的资源id
            ②为该布局的外部再嵌套一层父布局，如果不需要的话，写null就可以了!
            ③是否为加载的布局文件的最外层套一层root布局，不设置该参数的话，
            如果root不为null的话，则默认为true 如果root为null的话，attachToRoot就没有作用了!
             root不为null，attachToRoot为true的话，会在加载的布局文件最外层嵌套一层root布局;
             为false的话，则root失去作用! 简单理解就是：是否为加载的布局添加一个root的外层容器~!*/
            View view  = mInflater.inflate(R.layout.turnpalte_inner_view, this, false);
            //R.layout.turnpalte_inner_view里面定义了TextView和ImageView两个组件
            ImageView iv = findViewById(R.id.id_circle_menu_item_image);  //寻找到相应的组件
            TextView tv = findViewById(R.id.id_circle_menu_item_text);  //寻找到相应的
            iv.setImageResource(mItemImgs[i]);
            tv.setText(mItemTexts[i]);
            //相当于TextView和ImageView当成一个整体变成VIew

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //设置监听：点击图片或者文本组件的时候弹出信息
                    Toast.makeText(getContext(),mItemTexts[j], Toast.LENGTH_SHORT).show();
                }
            });
            addView(view);  //放入到一个View集合当中...
        }
        /*
        * getScaledTouchSlop是一个距离，表示滑动的时候，
        * 手的移动要大于这个距离才开始移动控件。
        * 如果小于这个距离就不触发移动控件
        * ViewConfiguration 实例获取
        ViewConfiguration viewConfiguration = ViewConfiguration.get(Context);
        1
        常用对象方法
        获取touchSlop （系统 滑动距离的最小值，大于该值可以认为滑动）
        int touchSlop = viewConfiguration.getScaledTouchSlop();
        获得允许执行fling （抛）的最小速度值
        int minimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        获得允许执行fling （抛）的最大速度值
        int maximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
*/
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    //测量过程
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
         * 调用这个方法的目的是为View设置大小...
         * 直接设置成系统根据父容器算出的一个推荐的最小值...
         * */
        setMeasuredDimension(getSuggestedMinimumWidth(),getSuggestedMinimumHeight());
        System.out.println(getSuggestedMinimumWidth()+""+getSuggestedMinimumHeight());
        //获取半径
        mRadius = Math.max(getWidth(),getHeight());  //?直径
        System.out.println(getWidth()+""+getHeight());
        //获取menu的数量
        final int count =getChildCount();
        //子View的大小等于整个布局（大的圆圈）的1/4
        int childSize = (int) (mRadius*mMaxChildDimensionRadius);
        //设置测量模式设置为精确...
        int childMode = MeasureSpec.EXACTLY;
        for (int i = 0; i < count; i++)
        {
            //对所有的子View进行迭代测量...
            final View child = getChildAt(i);
            //子控件不可显示，直接跳过..
            if (child.getVisibility() == GONE)
            {
                continue;
            }
            int makeMeasureSpec = -1;
            //子控件为中心图标的时候，设置其半径大小为1/3父容器半径的大小...
            //如果子控件是其他，也就表示为周围空间的时候，设置为父容器半径的1/4大小...
            if (child.getId() == R.id.id_circle_menu_item_center){
                //此步骤是对数据和模式的一个封装...最后measure()方法会根据封装的模式，对我们设置的参数进行设置...
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(
                        (int) (mRadius * mCenterItemDimesionRadious), childMode);
            }else{
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
                        childMode);
            }
            //由于是圆形menu，因此传递的值是相同的...
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

    }

    //当View要为所有子对象分配大小和位置时，调用此方法
    //完成了测量之后，正式根据参数进行布局...
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutWidth = r - l;  //宽度等于右边 - 左边
        int layoutHeight = b - t; //高度等于底部 - 顶部

        //对父容器进行布局
        int layoutRadius = Math.max(layoutWidth,layoutHeight);
        //对子View进行布局
        final int childCount = getChildCount();
        int left, top;
        int radius = (int) (layoutRadius * mMaxChildDimensionRadius);

        //根据子元素的个数，设置角度
        float angleDelay = 360 / (getChildCount() - 1);  //这里出去中心菜单按钮
        //循环放置子布局
        for(int i = 0; i < childCount; i++){
            final View child = getChildAt(i);
            //如果是中心元素就跳过
            if (child.getId() == R.id.id_circle_menu_item_center)
                continue;
            //如果子元素是不可见的也跳过
            if (child.getVisibility() == GONE){
                continue;
            }
            //取角度值..
            mStartAngle %= 360;

            //?以下代码没看懂
            float tmp = layoutRadius * 1f / 3 - 1 / 22f * layoutRadius;
            left = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
                    * radius);
            top = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
                    * radius);
            child.layout(left, top, left + radius, top + radius);
            mStartAngle += angleDelay;
        }
        //放置中心元素的布局
        View cView = findViewById(R.id.id_circle_menu_item_center);
        cView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println(cView.getMeasuredWidth() + " " + cView.getMeasuredWidth());
        //设置中心...
        /*
        * 中心菜单的存放位置的
        * 左边 = 布局的宽度/2 - 中心菜单圆圈的直径/2
        * 右边 = 布局的宽度/2 + 中心菜单圆圈的直径/2  或者左边+中心圆圈的直径*/
        int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
        int cr = cl + cView.getMeasuredWidth();  //
        cView.layout(cl, cl, cr, cr);  //左 上 右 下
    }
    private float mLastX;
    private float mLastY;

    private FlingRunnable mFlingRunnable;

    //学习
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //随手指滑动特效...
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mDownAngle = getAngle(x, y);//获取角度...
                mDownTime = System.currentTimeMillis();  //系统的当前时间...
                mTmpAngle = 0;

                //如果当前在进行快速滚动，那么移除对快速移动的回调...
                if (isFling) {
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                //计算每秒钟移动的角度...
                float anglePrMillionSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);

                //如果数值大于这个指定的数值，那么就会认为是加速滚动...
                if (Math.abs(anglePrMillionSecond) > 230 && !isFling)
                {
                    //开启一个新的线程，让其进行自由滚动...
                    post(mFlingRunnable = new FlingRunnable(anglePrMillionSecond));

                }
                if(Math.abs(anglePrMillionSecond) >230 || isFling)
                {
                    return true ;
                }

                break;
        }
        return super.dispatchTouchEvent(event);
    }
    private float getAngle(float xTouch, float yTouch)
    {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    private int getQuadrant(float x, float y)
    {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0)
        {
            return tmpY >= 0 ? 4 : 1;
        } else
        {
            return tmpY >= 0 ? 3 : 2;
        }

    }

    private class FlingRunnable implements Runnable
    {

        private float velocity;

        public FlingRunnable(float velocity)
        {
            this.velocity = velocity;
        }

        public void run()
        {
            if ((int) Math.abs(velocity) < 20)
            {
                isFling = false;
                return;
            }
            isFling = true;
            // rotateButtons(velocity / 75);
            mStartAngle += (velocity / 30);
            velocity /= 1.0666F;
            postDelayed(this, 30);
            requestLayout();
            Log.e("TAG", velocity + "");

        }
    }
}
