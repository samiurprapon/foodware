package life.nsu.bangladictionary.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;

import life.nsu.bangladictionary.R;
import life.nsu.bangladictionary.models.Vocabulary;

public class CustomDialog {

    Dialog dialog;
    Context context;

    ImageView mImage;
    TextView mEnglishWord;
    TextView mBanglaMeaning;

    public CustomDialog(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog, null, false);

        ConstraintLayout layout = view.findViewById(R.id.cp_bg_view);
        CardView mCardView = view.findViewById(R.id.cp_cardview);

        mEnglishWord = view.findViewById(R.id.tv_english_word);
        mBanglaMeaning = view.findViewById(R.id.tv_bangla_meaning);
        mImage = view.findViewById(R.id.iv_word_image);

        layout.setBackgroundColor(Color.parseColor("#60000000"));
        mCardView.setCardBackgroundColor(Color.parseColor("#70000000"));

        setColorFilter(mImage.getDrawable(), ResourcesCompat.getColor(context.getResources(), R.color.faded_white, null));

        dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(view);

    }

    public void show(Vocabulary vocabulary) {
        mEnglishWord.setTextColor(Color.WHITE);
        mBanglaMeaning.setTextColor(Color.WHITE);

        mEnglishWord.setText(vocabulary.getEnglish());
        mBanglaMeaning.setText(vocabulary.getBangla());

        if(vocabulary.getImage() != null) {
            Glide.with(context)
                    .load(Uri.parse(vocabulary.getImage()))
                    .placeholder(R.drawable.ic_upload_placeholder)
                    .circleCrop()
                    .into(mImage);
        }

        dialog.setCancelable(true);
        dialog.show();

    }

    public void hide() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void setColorFilter(Drawable drawable, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        }
    }
}
