package org.alex.sweeploadingview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import org.alex.callback.SimpleAnimatorListener;


/**
 * 作者：Alex
 * <br/>
 * 时间：2016/8/24 16:31
 * <br/>
 * 博客地址：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 * <br/>
 * <br/>
 */
@SuppressWarnings("all")
public class SweepLoadingView extends View {
    private RectF rectF;
    private Paint paint;
    private int circleColor;
    private int circleWidth;
    private int strokeCap;
    /**
     * 扫描一周 需要的时间
     */
    private int swipeDuration;
    /**
     * 起点的角度
     */
    private float startAngle;
    /**
     * 扫描的角度
     */
    private float sweepAngle;

    /**
     * 终点 和 起点的间隔 角度
     */
    private float gapAngle;
    /**
     * 起始点的偏移量
     */
    private float startAngleOffset;
    private ObjectAnimator sweepObjectAnimator;
    private ObjectAnimator startAngleObjectAnimator;
    /**
     * 扫描一周
     */
    private boolean isSweepRepeat;

    public SweepLoadingView(Context context) {
        super(context);
        initView(null);
    }

    public SweepLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        startAngleOffset = 0;
        isSweepRepeat = false;
        Context context = getContext();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SweepLoadingView);
        circleWidth = typedArray.getDimensionPixelSize(R.styleable.SweepLoadingView_slv_circleWidth, 0);
        circleColor = typedArray.getColor(R.styleable.SweepLoadingView_slv_circleColor, Color.parseColor("#FF5722"));
        strokeCap = typedArray.getInt(R.styleable.SweepLoadingView_slv_strokeCap, 0);
        swipeDuration = typedArray.getInt(R.styleable.SweepLoadingView_slv_duration, 1200);
        gapAngle = typedArray.getFloat(R.styleable.SweepLoadingView_slv_gapAngle, 45F);
        rectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap((strokeCap == 0) ? Paint.Cap.ROUND : Paint.Cap.SQUARE);
        paint.setStrokeWidth(circleWidth);
        paint.setColor(circleColor);
        initObjectAnimation();
    }

    private void initObjectAnimation() {
        sweepObjectAnimator = ObjectAnimator.ofFloat(this, new AngleProperty(Float.class, "sweepAngle"), 360F - gapAngle * 2);
        sweepObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        sweepObjectAnimator.setDuration(swipeDuration);
        sweepObjectAnimator.setRepeatCount(Integer.MAX_VALUE);
        sweepObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        sweepObjectAnimator.addListener(new SweepAngleAnimatorListener());

        startAngleObjectAnimator = ObjectAnimator.ofFloat(this, new AngleProperty(Float.class, "startAngle"), 360F);
        startAngleObjectAnimator.setInterpolator(new LinearInterpolator());
        startAngleObjectAnimator.setDuration(1500);
        startAngleObjectAnimator.setRepeatCount(Integer.MAX_VALUE);
        startAngleObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        int dWidth = circleWidth / 2;
        rectF.left = dWidth;
        rectF.right = min - dWidth;
        rectF.top = dWidth;
        rectF.bottom = min - dWidth;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        float startAngle = this.startAngle - startAngleOffset;
        float sweepAngle = this.sweepAngle;
        if (isSweepRepeat) {
            startAngle += sweepAngle;
            sweepAngle = 360 - sweepAngle - gapAngle;
        } else {
            sweepAngle += gapAngle;
        }
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startSweepAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopSweepAnimation();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startSweepAnimation();
        } else {
            stopSweepAnimation();
        }
    }

    private void startSweepAnimation() {
        if (!sweepObjectAnimator.isStarted()) {
            //LogUtil.e("开始");
            sweepObjectAnimator.start();
        }
        if (!startAngleObjectAnimator.isStarted()) {
            startAngleObjectAnimator.start();
        }
    }

    private void stopSweepAnimation() {
        //LogUtil.e("停止");
        sweepObjectAnimator.cancel();
        startAngleObjectAnimator.cancel();
    }

    private final class SweepAngleAnimatorListener extends SimpleAnimatorListener {
        @Override
        public void onAnimationRepeat(Animator animation) {
            handleSweepRepeat();
        }
    }

    /**
     * 处理扫描一周的 情况
     */
    private void handleSweepRepeat() {
        if (isSweepRepeat) {
            startAngleOffset = (startAngleOffset + gapAngle * 2) % 360;
        }
        isSweepRepeat = !isSweepRepeat;
    }

    private final class AngleProperty extends Property<SweepLoadingView, Float> {
        private String name;

        public AngleProperty(Class<Float> type, String name) {
            super(type, name);
            this.name = name;
        }

        @Override
        public Float get(SweepLoadingView object) {
            if ("sweepAngle".equals(name)) {
                return object.getSweepAngle();
            } else {
                return object.getStartAngle();
            }
        }

        @Override
        public void set(SweepLoadingView object, Float value) {
            if ("sweepAngle".equals(name)) {
                object.setSweepAngle(value);
            } else {
                object.setStartAngle(value);
            }
        }
    }

    private float getSweepAngle() {
        return sweepAngle;
    }

    private void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    private float getStartAngle() {
        return startAngle;
    }

    private void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    private void setCircleColor(@ColorInt int color) {
        this.circleColor = color;
        invalidate();
    }

    private void setCircleWidth(int width) {
        if (width < 1) {
            width = 4;
        }
        width = (int) dp2Px(width);
        this.circleWidth = width;
        invalidate();
    }

    private void setGapAngle(@FloatRange(from = 5F, to = 60F) float angle) {
        if ((angle < 5) || (angle > 60)) {
            angle = 45F;
        }
        this.gapAngle = angle;
        invalidate();
    }

    /**
     * 数据转换: dp---->px
     */
    private float dp2Px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }
}
