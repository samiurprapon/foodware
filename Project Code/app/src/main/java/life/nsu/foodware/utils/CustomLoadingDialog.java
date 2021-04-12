package life.nsu.foodware.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import life.nsu.foodware.R;

public class CustomLoadingDialog {

    private final ProgressDialog dialog;

    public CustomLoadingDialog(Context context) {
        dialog = new ProgressDialog(context);

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_loader);
    }

    public void show() {
        dialog.show();
    }


    public void hide() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
