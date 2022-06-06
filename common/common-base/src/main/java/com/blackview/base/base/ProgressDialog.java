package com.blackview.base.base;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.blackview.base.R;

import androidx.appcompat.app.AppCompatDialog;


public class ProgressDialog extends AppCompatDialog {

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static class Builder {

        private ProgressDialog dialog;

        public Builder(Context context) {
            dialog = new ProgressDialog(context);
            dialog.getWindow().setDimAmount(0f);
        }

        public Builder noClose() {
            dialog.setCancelable(false);
            return this;
        }

        public Builder setOnDismissListener(final OnDismissListener listener) {
            dialog.setOnDismissListener(listener);
            return this;
        }

        public ProgressDialog get() {
            return dialog;
        }
    }

    public void hideNavigationBar() {
        if (Build.VERSION.SDK_INT < 19) { // lower api
            this.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.INVISIBLE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public ProgressDialog(Context context) {
        this(context, R.style.customDialog);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.base_custrom_toast);
    }
}
