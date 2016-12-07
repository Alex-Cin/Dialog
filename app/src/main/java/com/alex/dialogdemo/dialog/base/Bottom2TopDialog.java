package com.alex.dialogdemo.dialog.base;

import android.content.Context;

import com.alex.dialogdemo.R;

import org.alex.dialog.annotation.AnimType;

/**
 * 作者：Alex
 * 时间：2016/12/7 13 27
 * 简述：
 */
public abstract class Bottom2TopDialog<D extends Bottom2TopDialog> extends SimpleDialog<D> {
    public Bottom2TopDialog(Context context) {
        super(context, R.style.alex_dialog_base_light_style);
    }
    /**
     * 显示对话框，无动画
     */
    @Override
    public void show() {
        show(AnimType.BOTTOM_2_TOP);
    }
    /**
     * 显示对话框，强制转换对话框的动画类型
     *
     * @param animType
     */
    @Override
    public void show(@AnimType int animType) {
        super.show(animType);
    }
}