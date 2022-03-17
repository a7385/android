package com.sample.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * 圓圖
 */

public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView
{
    private Bitmap m_Bitmap;
    private Animation m_Animation;

    public CircleImageView(Context context)
    {
        super(context);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        m_Animation = new AlphaAnimation(0.2f, 1f);
        m_Animation.setDuration(600);
        m_Animation.setRepeatCount(0);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getDrawable() == null
                || getDrawable().getIntrinsicWidth() == 0
                || getDrawable().getIntrinsicHeight() == 0) return;

        if (m_Bitmap != null) m_Bitmap.recycle();
        m_Bitmap = getRoundedBitmap(getDrawable());
        canvas.drawBitmap(m_Bitmap, 0, 0, null);
    }

    @Override
    public void setImageDrawable(Drawable drawable)
    {
        super.setImageDrawable(drawable);
        if (drawable != null && m_Animation != null)
        {
            startAnimation(m_Animation);
        }
    }

    public Bitmap getRoundedBitmap(Drawable drawable)
    {
        Bitmap drawableBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(drawableBitmap);
        if (getImageMatrix() != null)
        {
            canvas.concat(getImageMatrix());
        }

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                .getIntrinsicHeight());
        drawable.draw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        float rx = Math.min(viewWidth, viewHeight);
        float ry = rx;
        int color = 0xffcccccc;
        Rect rect = new Rect(0, 0, viewWidth, viewHeight);
        RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        Bitmap output = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(output);
        canvas2.drawARGB(0, 0, 0, 0);
        canvas2.drawRoundRect(rectF, rx, ry, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas2.drawBitmap(drawableBitmap, rect, rect, paint);
        return output;
    }
}
