package com.alex.dialogdemo.dialog;

import android.content.Context;
import android.view.View;

import com.alex.dialogdemo.R;
import com.alex.dialogdemo.dialog.base.SimpleDialog;

/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class LoadingDialog extends SimpleDialog<LoadingDialog> {

    /*super(context,R.style.alex_dialog_base_light_style);*/
    public LoadingDialog(Context context) {
        super(context);
    }

    /**
     * 配置 对话框的 布局文件
     */
    @Override
    public int getLayoutRes() {
        return R.layout.dialog_loading;
    }

    /**
     * 在 这里 进行 finndView  设置点击事件
     */
    @Override
    public void onCreateData() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v, int id) {

    }
}
