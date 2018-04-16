package com.vessel.gather.widght;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author vesselzhang
 * @date 2017/11/28
 */

public class RoundImage extends AppCompatImageView {
    public RoundImage(Context context) {
        super(context);
        init();
    }

    public RoundImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private Paint dstPaint;

    protected void init() {
        dstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dstPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = getWidth();
            int height = getHeight();
            int size = width >= height ? height : width;
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.addCircle((float) width / 2, (float) height / 2, (float) size * 9 / 20, Path.Direction.CCW);
            path.addRect(0, 0, width, height, Path.Direction.CCW);
            path.close();
            drawable.setBounds(0, 0, width, height);
            canvas.saveLayerAlpha(0, 0, width, height, 255, Canvas.ALL_SAVE_FLAG);
            drawable.draw(canvas);
            canvas.drawPath(path, dstPaint);
            canvas.restore();
        }
    }
}
