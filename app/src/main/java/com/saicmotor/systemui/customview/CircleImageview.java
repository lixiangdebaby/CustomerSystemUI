package com.saicmotor.systemui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.saicmotor.systemui.R;

public class CircleImageview extends ImageView {
    private Paint mPaintCircle;      //画圆形图像的笔
    private Paint mPaintBorder;          //画圆形边界的笔
    private Paint mPaintBackgroud;      //画背景颜色的笔
    private BitmapShader mBitmapShader;      //图像着色器，可以用来画圆
    private Matrix mMatrix;          //图片变换处理器-用来缩放图片以适应view控件的大小
    private int mWidth;        //获得控件宽度
    private int mHeight;             //获得控件高度
    private int mRadius;             //中心园的半径
    private int mDiameter;
    private int mCircleBorderWidth;        //边界宽度
    private int mCirlcleBorderColor;             //边界边框颜色
    private int mCircleBackgroudColor;      //圆形头像背景色
    private boolean mIsHaveBoder;
    private int mBorderOffset = 2;
    private int mStrokeWidth = 12;
    public CircleImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleHead);//将获取的属性转化为我们最先设好的属性
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CircleHead_circleBorderHeadWidth:
                    mCircleBorderWidth = (int) typedArray.getDimension(attr, 0);
                    break;
                case R.styleable.CircleHead_ringHeadColor:
                    mCirlcleBorderColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CircleHead_backgroundHeadColor:
                    mCircleBackgroudColor = typedArray.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.CircleHead_ishaveboder:
                    mIsHaveBoder = typedArray.getBoolean(attr,false);
                    break;
                case R.styleable.CircleHead_mDiameter:
                    mDiameter = (int) typedArray.getDimension(attr, 0);
                    break;
            }
        }
        init();
    }
    private void init(){
        mMatrix = new Matrix();

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth(mStrokeWidth);

        mPaintBorder = new Paint();
        mPaintBorder.setAntiAlias(true);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(mCircleBorderWidth);
        mPaintBorder.setColor(mCirlcleBorderColor);

        //画背景颜色的笔
        mPaintBackgroud = new Paint();
        mPaintBackgroud.setColor(mCircleBackgroudColor);
        mPaintBackgroud.setAntiAlias(true);
        mPaintBackgroud.setStyle(Paint.Style.FILL);
    }
    //使用BitmapShader画圆图形
    private void setBitmapShader(){
        //将图片转换成Bitmap
        Drawable drawable = getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //将bitmap放进图像着色器，后面两个模式是x，y轴的缩放模式，CLAMP表示拉伸
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //初始化图片与view之间伸缩比例，因为比例一般非整数，所以用float，免得精度丢失
        float scale = 1.0f;
        //将图片的宽度高度的最小者作为图片的边长，用来和view来计算伸缩比例
        int bitmapSize = Math.min(bitmap.getHeight(), bitmap.getWidth());
        //计算缩放比例，view的大小和图片的大小比例
        scale = mWidth * 1.0f / bitmapSize;
        //利用这个图像变换处理器，设置伸缩比例，长宽以相同比例伸缩
        mMatrix.setScale(scale, scale);
        //给那个图像着色器设置变换矩阵，绘制时就根据view的size，设置图片的size
        //使图片的较小的一边缩放到view的大小一致，这样就可以避免图片过小导致CLAMP拉伸模式或过大导致显示不全
        mBitmapShader.setLocalMatrix(mMatrix);
        //为画笔套上一个Shader的笔套
        mPaintCircle.setShader(mBitmapShader);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 在这里设置高度宽度，以设置的较小值为准，防止不成圆
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        int mCircleSize = Math.min(mHeight, mWidth);
        //圆的半径短的二分之一作为半径
        //mRadius = mCircleSize / 2 - (mCircleBorderWidth+mBorderOffset+mStrokeWidth);
        mRadius = mDiameter/2;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //这里注释掉onDraw是为了不绘制原来的画布,如果使用的话就意味着又是渲染一层
        //        super.onDraw(canvas);
        if (getDrawable() != null) {
            //Log.d("CircleImageview","getDrawable() = "+getDrawable()+"  w ,h" +mWidth +mHeight);
            setBitmapShader();
            canvas.drawRect(0, 0, mWidth, mHeight, mPaintBackgroud);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaintCircle);
            //画边框
            if(mIsHaveBoder) {
                canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius + mCircleBorderWidth+mBorderOffset, mPaintBorder);
            }
        } else {
            //如果在xml中这个继承ImageView的类没有被set图片就用默认的ImageView方案咯
            super.onDraw(canvas);
        }

    }
}
