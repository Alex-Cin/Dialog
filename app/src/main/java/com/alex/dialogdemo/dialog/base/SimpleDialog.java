package com.alex.dialogdemo.dialog.base;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.alex.dialogdemo.R;

import org.alex.dialog.annotation.AnimType;
import org.alex.dialog.base.BaseDialog;

/**
 * 作者：Alex
 * 时间：2016/12/7 13 22
 * 简述：
 */

public abstract class SimpleDialog<D extends SimpleDialog> extends BaseDialog<D> {
    public SimpleDialog(Context context) {
        super(context, R.style.alex_dialog_base_light_style);
    }

    public SimpleDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 显示对话框，无动画
     */
    @Override
    public void show() {
        if (Gravity.BOTTOM == gravity) {
            show(AnimType.BOTTOM_2_TOP);
        } else if (Gravity.TOP == gravity) {
            show(AnimType.TOP_2_BOTTOM);
        } else if (Gravity.CENTER == gravity) {
            show(animType);
        } else {
            super.show();
        }
    }

    /**
     * 显示对话框，强制转换对话框的动画类型
     */
    public void show(@AnimType int animType) {
        Window window = getWindow();
        /*如果根据  AnimType 的类型，强制选择Dialog出现的位置*/
        if (AnimType.BOTTOM_2_TOP == animType) {
            setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.alex_dialog_anim_bottom2top);
        } else if (AnimType.TOP_2_BOTTOM == animType) {
            setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.alex_dialog_anim_top2bottom);
        } else if (AnimType.CENTER_SCALE == animType) {
            setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.alex_dialog_anim_scale);
        } else if (AnimType.CENTER_NORMAL == animType) {
            setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.alex_dialog_anim_alpha);
        }
        super.show();
    }

}
