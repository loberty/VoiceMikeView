package cn.loberty.voicemikedemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Create by WangChen on 2020/5/27
 */
public class VoiceMikeView extends View {

    private int rectLeft = 40;
    private int rectTop = 20;
    private int rectHeight = 135;
    private int rectWidth = 70;
    private int levelCount = 30;

    private Paint mPaint;
    private RectF levelRectF,roundRectF;
    private PorterDuffXfermode porterDuffXfermode;
    private Bitmap bitmap;
    private Context context;

    public VoiceMikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VoiceMikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);

        int x = (getWidth()-bitmap.getWidth()) >> 1;
        int y = (getHeight()-bitmap.getHeight()) >> 1;

        canvas.drawBitmap(bitmap,x,y,mPaint);

        initRect(x,y);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawRoundRect(roundRectF,rectWidth >> 1,rectWidth >> 1,mPaint);

        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawRect(levelRectF,mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(0xff0dac67);
        mPaint.setAntiAlias(true);
        levelRectF = new RectF();
        roundRectF = new RectF();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        bitmap = getBitmapFromVectorDrawable(context,R.drawable.ic_svg_mike);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        rectLeft = x(70);
        rectTop = x(18);
        rectWidth = x(60);
        rectHeight = x(99);
    }

    private int x(int dp){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int)(dp * (metrics.densityDpi / 160f));
    }

    private void initRect(int x, int y){
        x = x + rectLeft;
        y = y + rectTop;

        levelRectF.left = x;
        levelRectF.top = y;
        levelRectF.right = x+rectWidth;
        levelRectF.bottom = y + getLevelStep();

        roundRectF.left = x;
        roundRectF.top = y;
        roundRectF.right = x+rectWidth;
        roundRectF.bottom = y + rectHeight;
    }

    private int getLevelStep(){
        int x = rectHeight/levelCount;
        level = levelCount - level;
        level = Math.max(level, 1);
        return level * x;
    }

    private int level;
    public void setLevel(int f){
        level = f ;
        invalidate();
    }

    public Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            assert drawable != null;
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        assert drawable != null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
