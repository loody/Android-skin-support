package skin.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;

import skin.support.R;
import skin.support.content.res.SkinCompatResources;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 17-1-14.
 */

public class SkinCompatMultiAutoCompleteTextView extends MultiAutoCompleteTextView implements SkinCompatSupportable {
    private static final int[] TINT_ATTRS = {
            android.R.attr.popupBackground
    };
    private int mDropDownBackgroundResId = INVALID_ID;
    private SkinCompatTextHelper mTextHelper;
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatMultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public SkinCompatMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public SkinCompatMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, TINT_ATTRS, defStyleAttr, 0);
        if (a.hasValue(0)) {
            mDropDownBackgroundResId = a.getResourceId(0, INVALID_ID);
        }
        a.recycle();
        applyDropDownBackgroundResource();
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        mTextHelper = SkinCompatTextHelper.create(this);
        mTextHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setDropDownBackgroundResource(@DrawableRes int resId) {
        super.setDropDownBackgroundResource(resId);
        mDropDownBackgroundResId = resId;
        applyDropDownBackgroundResource();
    }

    private void applyDropDownBackgroundResource() {
        mDropDownBackgroundResId = SkinCompatHelper.checkResourceId(mDropDownBackgroundResId);
        if (mDropDownBackgroundResId != INVALID_ID) {
            String typeName = getResources().getResourceTypeName(mDropDownBackgroundResId);
            if ("color".equals(typeName)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    int color = SkinCompatResources.getInstance().getColor(mDropDownBackgroundResId);
                    setDrawingCacheBackgroundColor(color);
                } else {
                    ColorStateList colorStateList =
                            SkinCompatResources.getInstance().getColorStateList(mDropDownBackgroundResId);
                    Drawable drawable = getDropDownBackground();
                    if (drawable != null) {
                        DrawableCompat.setTintList(drawable, colorStateList);
                        setDropDownBackgroundDrawable(drawable);
                    } else {
                        ColorDrawable colorDrawable = new ColorDrawable();
                        colorDrawable.setTintList(colorStateList);
                        setDropDownBackgroundDrawable(colorDrawable);
                    }
                }
            } else if ("drawable".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(mDropDownBackgroundResId);
                setDropDownBackgroundDrawable(drawable);
            } else if ("mipmap".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getMipmap(mDropDownBackgroundResId);
                setDropDownBackgroundDrawable(drawable);
            }
        }
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void setTextAppearance(int resId) {
        setTextAppearance(getContext(), resId);
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (mTextHelper != null) {
            mTextHelper.onSetTextAppearance(context, resId);
        }
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        if (mTextHelper != null) {
            mTextHelper.onSetCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (mTextHelper != null) {
            mTextHelper.onSetCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
        if (mTextHelper != null) {
            mTextHelper.applySkin();
        }
        applyDropDownBackgroundResource();
    }

}