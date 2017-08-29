package net.adaiandy.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import net.adaiandy.common.R;

/**
 * 正方形Framelayout
 */
public class SquareLayout extends FrameLayout {
    

    public SquareLayout(Context context) {
        super(context);
        
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
