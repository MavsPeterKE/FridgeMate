package com.example.peter_pc.fridgemate.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.peter_pc.fridgemate.R;

/**
 * Created by Peter-PC on 3/13/2018.
 */

public class SimpleDecorator extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public SimpleDecorator(Context context) {
        this.mDivider =context.getResources().getDrawable(R.drawable.recycler_list_layout);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
