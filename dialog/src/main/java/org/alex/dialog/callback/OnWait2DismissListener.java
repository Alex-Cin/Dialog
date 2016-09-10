package org.alex.dialog.callback;

import android.app.Dialog;

/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public interface OnWait2DismissListener<D extends Dialog> {
    /**
     * @param second 为零的时候  对话框结束
     */
    public void onDismiss(D dialog, int second);
}
