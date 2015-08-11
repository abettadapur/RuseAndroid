package com.bettadapur.ruseandroid.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Alex on 8/9/2015.
 */

public class ArtistImageView  extends ImageView
{
    public ArtistImageView(Context context) {
        super(context);
    }

    public ArtistImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArtistImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width/2);
    }
}
