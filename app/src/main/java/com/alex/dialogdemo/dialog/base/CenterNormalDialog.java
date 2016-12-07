package com.alex.dialogdemo.dialog.base;

import android.content.Context;

import com.alex.dialogdemo.R;

import org.alex.dialog.annotation.AnimType;

/**
 * 作者：Alex
 * 时间：2016年09月10日    17:33
 * 简述：
 */

public abstract class CenterNormalDialog<D extends CenterNormalDialog> extends SimpleDialog<D> {
    public CenterNormalDialog(Context context) {
        super(context, R.style.alex_dialog_base_light_style);

    }

    /**
     * 显示对话框，无动画
     */
    @Override
    public void show() {
        show(AnimType.CENTER_NORMAL);
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
