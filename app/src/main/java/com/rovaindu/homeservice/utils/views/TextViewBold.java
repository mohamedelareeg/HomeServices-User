package com.rovaindu.homeservice.utils.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.rovaindu.homeservice.R;


@SuppressLint("AppCompatCustomView")
public class TextViewBold extends TextView {


    public TextViewBold(Context context) {
        super(context);
        init();
    }

    public TextViewBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public TextViewBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Ubuntu-Bold.ttf");
        setTypeface(tf, Typeface.BOLD);
    }

    void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomTextView);

            Drawable drawableStart = null;
            Drawable drawableEnd = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableStart = attributeArray.getDrawable(R.styleable.CustomTextView_drawableStartCompat);
                drawableEnd = attributeArray.getDrawable(R.styleable.CustomTextView_drawableEndCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.CustomTextView_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.CustomTextView_drawableTopCompat);
            } else {
                final int drawableStartId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableStartCompat, -1);
                final int drawableEndId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableEndCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableTopCompat, -1);

                if (drawableStartId != -1)
                    drawableStart = AppCompatResources.getDrawable(context, drawableStartId);
                if (drawableEndId != -1)
                    drawableEnd = AppCompatResources.getDrawable(context, drawableEndId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
            }

            // to support rtl
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, drawableTop, drawableEnd, drawableBottom);
                attributeArray.recycle();
            }

        }
    }

}
