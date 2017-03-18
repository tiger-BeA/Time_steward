package com.example.jamesljk.project.pickerview;

import android.view.Gravity;

import com.example.jamesljk.project.R;

/**
 * Created by jamesljk on 2016/12/9.
 */

public class PickerViewAnimateUtil {
    private static final int INVALID = -1;
    // 插入/消失动画
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
        }
        return INVALID;
    }
}
