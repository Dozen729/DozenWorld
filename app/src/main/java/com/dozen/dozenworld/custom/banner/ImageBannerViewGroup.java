package com.dozen.dozenworld.custom.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.dozen.dozenworld.utils.L;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dozen on 19-7-22.
 * Describe:
 */
public class ImageBannerViewGroup extends ViewGroup {

    //子视图的个数
    private int children;
    private int childrenWidth;//子视图宽度
    private int childrenHeight;//子视图高度

    private int x;//此时 的x的值代表的是第一次按下的位置 的横坐标，每一次移动过程中移动之前的位置横坐标
    private int index=0;//代表第一张图片索引

    private Scroller scroller;

    private boolean isAuto=true;//默认自动轮播

    private ImageBannerLister lister;

    /*
    * 单击事件获取点击变量
    * */

    public void setLister(ImageBannerLister lister) {
        this.lister = lister;
    }

    public interface ImageBannerLister{
        void clickImageIndex(int pos);
    }

    private ImageBannerSelect imageBannerSelect;

    public void setImageBannerSelect(ImageBannerSelect imageBannerSelect) {
        this.imageBannerSelect = imageBannerSelect;
    }

    public interface ImageBannerSelect{
        void selectIndex(int index);
    }



    //自动轮播
    /*
     * 采用Timer,TimerTask,Handler三者结合 的方式来实现自动 轮播
     * 我们会抽出 2个方法来控制 ，是否启动自动 轮播我们称之为startAuto(),stopAuto()
     * 那么我们在2个方法的控制过程中，我们实际 上希望 控制 的是自动开启轮播图的开关
     * 那么我们就需要一个变量 参数来作为我们自动 开启轮播图的开关，我们称之为isAuto,boolean true代表关闭
     * */
    private boolean isClick;//true的时候，代表是点击事件，false的时候代表不是点击事件
    private Timer timer=new Timer();
    private TimerTask task;
    @SuppressLint("HandlerLeak")
    private Handler autoHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0://此时我们需要 图片的自动轮播
                    if (++index>=children){
                        index=0;
                    }
                    scrollTo(childrenWidth*index,0);
                    if (imageBannerSelect!=null){
                        imageBannerSelect.selectIndex(index);
                    }
                    break;
            }

        }
    };

    private void startAuto(){
        isAuto=true;
    }

    private void stopAuto(){
        isAuto=false;
    }


    /*
    * 要想实现图片轮播底部圆点以及底部圆点切换功能步骤思路
    * 1.我们需要自定义一个继承自FrameLayout的布局，利用FrameLayout布局的特性（在同一位置 不丗的View最终显示的是最后 一个View），我们就可以 实现底部圆点的布局
    * 2.我们需要准备素材，就是底部的素材，我们可以利用Drawable的功能，去实现一个圆点图片的展示
    * 3.我们就需要 继承FrameLayout来自定义一个类，在该类的实现过程 中，我们去加载我们刚才自定义的ImageBannerViewGroup和我们需要 实现的布局LinearLayout来实现
    * */


    public ImageBannerViewGroup(Context context) {
        this(context,null);

    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller=new Scroller(getContext());

        task=new TimerTask() {
            @Override
            public void run() {
                if (isAuto){//开启轮播图
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };

        timer.schedule(task,100,3000);

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    /*
     * 我们在自定义viewGroup中，我们必须要实现的方法有：测量 -》布局-》绘制
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*
         * 由于我们要实现的是一个viewGroup的窗口，那么我们就应该需要知道该窗口中的所有子视图
         * 我们要想测量 我们的viewGroup的宽度和高度，妓我们就必须 先要去测量子视图的宽度 和高度之和，
         * 才能 知道我们的viewGroup的宽度 和高度是多少
         * */


        //1.求出子视图的个数
        children = getChildCount();

        if (0 == children) {
            setMeasuredDimension(0, 0);
        } else {
            //2.测量子视图的宽度和高度
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //此时我们以第一个子视图为基准，也就是说我们的ViewGroup的高度就是我们第一个子视图的高度，宽度就是我们第一个子视图*子视图的个数
            View view = getChildAt(0);

            //3.根据子视力的宽度和高度，来求出viewGroup的宽度和高度
            childrenHeight = view.getMeasuredHeight();
            childrenWidth = view.getMeasuredWidth();

            int width = childrenWidth * children;//所有子视图的宽度之和
            setMeasuredDimension(width, childrenHeight);
        }


    }

    /*
     * 事件的传递过程中的调用 方法 ：我们需要调用窗口的拦截方法 onInterceptTouchEvent
     * 针对于该方法 我们可以理解为如果说该方法的返回值为true的时候,那么我们自定义 的ViewGroup窗口就会处理此次拦截事件
     * 如果说返回值为false的时候 ，那么我们自定义的ViewGroup窗口将不会接受此次事件的处理过程，将会继续向下传递该事件
     * 针对于我们自定义的ViewGroup我们当然是希望我们的ViewGroup窗口处理接受事件那么我们的返回值就是true
     * 如果返回true的话，真正处理该事件 的方法是onTouchEvent
     * */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;

    }

    /*
    * 用2种方式来实现轮播图的手动轮播
    * 1.利用ScrollTo,ScrollBy 完成轮播图的手动轮播
    * 2.利用Scroller对象完成轮播图的手动轮播
    *
    * 第一：我们滑动屏幕图片的过程中，其实就是我们自定义 Viewgroup的子视图的移动过程，那么我们只需要 知道
    *滑动之前的横坐标和滑动之后的横坐标，此时我们 就可以 求出我们这次过程中移动 的距离，我们在利用scrollBy方法实现图片的滑动
    * 所以 此时我们需要 有2个值是需要 我们求出的：移动之前，移动之后的横坐标值
    *
    * 第二：在我们第一次按下的那一瞬间，此时 的移动 之前和移动之后 的值是相等的，也就是我们此时 按下那一瞬间的一个点的横坐标的值
    *
    * 第三：我们在不断的尝过程中，是不断地调用 我们ACTION_MOVE方法，那么 此时 我们就应该将移动 之前 的值和移动 之后 的时行 保存以便我们能够 算出 滑动的距离
    *
    * 第四：在我们择的那一瞬间 ，我们需要 计算 出我们此时 将要尝到 哪张图片的位置 上
    *
    * 我们此时 就需要 求得出将要滑动到 哪张图片的索引值
    * （我们当前ViewGroup的滑动位置+我们第一张图片的宽度/2）/我们的第一张图片的宽度值
    *
    * 此时我们就可以利用ScrollTo方法滑动 到 该图片的位置上
    * */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://表示用户按下的一瞬间
                stopAuto();
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }

                isClick=true;
                x= (int) event.getX();

                break;
            case MotionEvent.ACTION_MOVE://表示用户 按下之后在屏幕上移动的过程
                int moveX= (int) event.getX();
                int distance=moveX-x;
                if (distance!=0){
                    isClick=false;
                }
                scrollBy(-distance,0);
                x=moveX;
                break;
            case MotionEvent.ACTION_UP://表示用户 抬起一瞬间

                int scrollX=getScrollX();
                index=(scrollX+childrenWidth/2)/childrenWidth;
                if (index<0){//说明了此时已经滑动到 了最左边第一张图片
                    index=0;
                }else if (index>children-1){//说明了此时 已经 滑动 到 了最右边最后一张图片
                    index=children-1;
                }


                if (isClick){
                    lister.clickImageIndex(index);
                }else {

                    int dx=index*childrenWidth-scrollX;
                    scroller.startScroll(scrollX,0,dx,0);
                    postInvalidate();
                    if (imageBannerSelect!=null){
                        imageBannerSelect.selectIndex(index);
                    }
                }

                startAuto();
                break;
            default:
                break;
        }

        return true;//返回true的目的是告诉我们该viewGroup

    }

    /*
     * 继承ViewGroup必须实现onLayout
     * @param changed 代表的时候 是当我们 的ViewGroup布局位置发生改变的时候为true,没有发生改变为false
     *
     *
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {

            int leftMargin = 0;
            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childrenWidth, childrenHeight);
                leftMargin += childrenWidth;
            }
        }
    }
}
