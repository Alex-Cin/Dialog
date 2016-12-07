package com.alex.dialogdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.alex.dialogdemo.R;
import com.alex.dialogdemo.dialog.BottomDialog;
import com.alex.dialogdemo.dialog.LoadingDialog;
import com.alex.dialogdemo.dialog.NoticeDialog;
import com.alex.dialogdemo.dialog.OneButtonDialog;
import com.alex.dialogdemo.dialog.SelectedProductDialog;
import com.alex.dialogdemo.dialog.base.SimpleDialog;

import org.alex.dialog.annotation.ClickPosition;
import org.alex.dialog.callback.OnDialogClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LoadingDialog loadingDialog;
    private NoticeDialog noticeDialog;
    private OneButtonDialog oneButtonDialog;
    private SelectedProductDialog selectedProductDialog;
    private BottomDialog bottomDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        MyOnDialogClickListener onDialogClickListener = new MyOnDialogClickListener();
        loadingDialog = new LoadingDialog(this);
        noticeDialog = new NoticeDialog(this).setOnDialogClickListener(onDialogClickListener);
        bottomDialog = new BottomDialog(this).setOnDialogClickListener(onDialogClickListener);
        oneButtonDialog = new OneButtonDialog(this).setOnDialogClickListener(onDialogClickListener);
        selectedProductDialog = new SelectedProductDialog(this, findViewById(Window.ID_ANDROID_CONTENT)).setOnDialogClickListener(onDialogClickListener);

        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
        findViewById(R.id.bt_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.bt_1 == view.getId()) {
            loadingDialog.show();

        } else if (R.id.bt_2 == view.getId()) {
            noticeDialog.show();

        } else if (R.id.bt_3 == view.getId()) {
            selectedProductDialog.show();

        } else if (R.id.bt_4 == view.getId()) {
            bottomDialog.show();
        } else if (R.id.bt_5 == view.getId()) {
            oneButtonDialog.show();
        }

    }
    private final class MyOnDialogClickListener implements OnDialogClickListener<SimpleDialog> {

        @Override
        public void onDialogClick(SimpleDialog dialog, @ClickPosition String clickPosition) {
            //LogUtil.e("tag = " + dialog.tag + " clickPosition =" + clickPosition);
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.destody();
        }
        loadingDialog = null;
        if (noticeDialog != null) {
            noticeDialog.destody();
        }
        noticeDialog = null;
        if (oneButtonDialog != null) {
            oneButtonDialog.destody();
        }
        oneButtonDialog = null;
        if (selectedProductDialog != null) {
            selectedProductDialog.destody();
        }
        selectedProductDialog = null;
        if(bottomDialog!=null){
            bottomDialog.destody();
        }
        bottomDialog = null;
    }


}
