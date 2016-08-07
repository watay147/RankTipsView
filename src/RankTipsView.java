

/**
 * An attachable rank tips view that show 2 lines text containing a tips and a
 * number.
 *
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;



public class RankTipsView extends View {

    private int mBackgroundColor;
    private int mTextColor;

    private int mWidth, mHeight;
    private int mHalfWidth,mHalfHeight;
    private Paint mPathPaint;
    private Paint mTextPaint;
    private Path mPath;

    private String mTipsText;
    private int mRank;
    private int mTextSize;
    public RankTipsView(Context context) {
        this(context, null);
    }

    public RankTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public RankTipsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(
                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                            Gravity.RIGHT | Gravity.TOP);
            setLayoutParams(layoutParams);
        }


        setMargin(0, 0, DensityUtils.dip2px(getContext(),5), 0);
        mBackgroundColor =Color.parseColor("#d3321b");
        mTextColor = Color.parseColor("#ffffff");
        mHalfWidth=DensityUtils.dip2px(getContext(),14);
        mHalfHeight=DensityUtils.dip2px(getContext(),24);


        mPathPaint = new Paint();
        mPathPaint.setColor(mBackgroundColor);
        mPathPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextSize =DensityUtils.sp2px(getContext(),10);
        mTextPaint.setTextSize(mTextSize);
        mTipsText="TOP";
        mRank=1;

        mPath = new Path();
        mPath.setFillType(Path.FillType.WINDING);

        mPath.lineTo(-mHalfWidth,mHalfHeight/2);
        mPath.lineTo(-mHalfWidth,-mHalfHeight);
        mPath.lineTo(mHalfWidth,-mHalfHeight);
        mPath.lineTo(mHalfWidth,mHalfHeight/2);
        mPath.lineTo(0,0);





    }

    public void setWidth(int widthDip){
        mHalfWidth=DensityUtils.dip2px(getContext(),widthDip/2);
        invalidate();
        requestLayout();
    }

    public void setHeight(int heightDip){
        mHalfHeight=DensityUtils.dip2px(getContext(),heightDip*2/3);
        invalidate();
        requestLayout();
    }
    public void setTipsText(String text){
        mTipsText =text;
        invalidate();
        requestLayout();
    }
    public void setNumber(int num){
        mRank=num;
        invalidate();
        requestLayout();
    }

    public void setTextSize(int sizeSp){
        mTextSize =DensityUtils.sp2px(getContext(),sizeSp);
        invalidate();
        requestLayout();
    }

    public void setTextColor(int color){
        mTextColor=color;
        invalidate();
        requestLayout();
    }

    public void setBackgroundColor(int color){
        mBackgroundColor =color;
        invalidate();
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        double minw = getPaddingLeft() + getPaddingRight() + 2*mHalfWidth;
        int w = resolveSizeAndState((int)minw, widthMeasureSpec, 1);


        double minh = getPaddingBottom() + getPaddingTop()+2*mHalfHeight;
        int h = resolveSizeAndState((int)minh, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
    @Override
   public void onDraw(Canvas canvas){
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawPath(mPath,mPathPaint);
        canvas.drawText(mTipsText,0,-mHalfHeight*3/4+mTextSize/2,mTextPaint);
        canvas.drawText(String.valueOf(mRank),0,-mHalfHeight/4+mTextSize/2,
                mTextPaint);

    }



    public void setGravity(int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.gravity = gravity;
        setLayoutParams(params);
        invalidate();
        requestLayout();
    }

    public int getGravity() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        return params.gravity;
    }

    public void setMargin(int dipMargin) {
        setMargin(dipMargin, dipMargin, dipMargin, dipMargin);
    }

    public void setMargin(int leftDipMargin, int topDipMargin, int rightDipMargin, int bottomDipMargin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.leftMargin = DensityUtils.dip2px(getContext(),leftDipMargin);
        params.topMargin = DensityUtils.dip2px(getContext(),topDipMargin);
        params.rightMargin = DensityUtils.dip2px(getContext(),rightDipMargin);
        params.bottomMargin = DensityUtils.dip2px(getContext(),bottomDipMargin);
        setLayoutParams(params);
        invalidate();
        requestLayout();
    }

    public int[] getMargin() {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        return new int[] { params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin };
    }





    

    /**
     * Attach the RankTipsView to the target view
     *
     * @param target the view to attach the RankTipsView
     */
    public void setTargetView(View target) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }

        if (target == null) {
            return;
        }

        if (target.getParent() instanceof FrameLayout) {
            ((FrameLayout) target.getParent()).addView(this);

        } else if (target.getParent() instanceof ViewGroup) {
            // use a new Framelayout container for adding
            ViewGroup parentContainer = (ViewGroup) target.getParent();
            int groupIndex = parentContainer.indexOfChild(target);
            parentContainer.removeView(target);

            FrameLayout badgeContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();

            badgeContainer.setLayoutParams(parentLayoutParams);
            target.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
            badgeContainer.addView(target);

            badgeContainer.addView(this);
        } else if (target.getParent() == null) {
            Log.e(getClass().getSimpleName(), "ParentView is needed");
        }

    }


}

class DensityUtils {
    public DensityUtils() {
    }
    /**
     * convert the dp to px depend on the device density.
     *
     * @param context the context
     * @param dpValue a value of dp
     * @return the result of px
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }
    /**
     * convert the px to dp depend on the device density.
     *
     * @param context the context
     * @param pxValue a value of px
     * @return the result of dp
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }
    /**
     * convert the sp to px depend on the device scaledDensity.
     *
     * @param context the context
     * @param spValue a value of sp
     * @return the result of px
     */
    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * getFontDensity(context) + 0.5);
    }
    /**
     * convert the px to sp depend on the device scaledDensity.
     *
     * @param context the context
     * @param pxValue a value of px
     * @return the result of sp
     */
    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / getFontDensity(context) + 0.5);
    }
    /**
     * get the density of device screen.
     *
     * @param context the context
     * @return the screen density
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
    /**
     * get the scale density of device screen.
     * usually this value is the same as density.
     * but it can adjust by user.
     *
     * @param context the context
     * @return the screen scale density.
     */
    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }
}
