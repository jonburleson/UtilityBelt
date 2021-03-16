package com.jonburleson.utilitybelt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

class LineEditText extends androidx.appcompat.widget.AppCompatEditText {
    private final Rect mRect = new Rect();
    private final Paint mPaint = new Paint();

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // define the style of line
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // define the color of line
        mPaint.setColor(Color.parseColor("#808080"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int lHeight = getLineHeight();
        // the number of line
        int count = height / lHeight;
        if (getLineCount() > count) {
            // for long text with scrolling
            count = getLineCount();
        }
        Rect r = mRect;

        // first line
        int baseline = getLineBounds(0, r);

        // draw the remaining lines.
        for (int i = 0; i < count; i++) {
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, mPaint);
            // next line
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }
}