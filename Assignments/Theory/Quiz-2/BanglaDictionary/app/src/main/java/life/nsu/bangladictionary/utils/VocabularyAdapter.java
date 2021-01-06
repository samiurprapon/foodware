package life.nsu.bangladictionary.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import life.nsu.bangladictionary.R;
import life.nsu.bangladictionary.models.Vocabulary;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;

    private List<Vocabulary> vocabularyList;

    public VocabularyAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setVocabularyList(List<Vocabulary> vocabularyList) {
        this.vocabularyList = vocabularyList;
        notifyDataSetChanged();
    }

    public Vocabulary getVocabulary(int position) {
        return vocabularyList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_vocabulary, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (vocabularyList != null) {
            Vocabulary vocabulary = vocabularyList.get(position);

            holder.mEnglish.setText(vocabulary.getEnglish());

            if(vocabulary.getImage() != null) {
                Glide.with(context)
                        .load(Uri.parse(vocabulary.getImage()))
                        .placeholder(R.drawable.ic_upload_placeholder)
                        .circleCrop()
                        .into(holder.mImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (vocabularyList != null) {
            return vocabularyList.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mEnglish;
        CircleImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.cv_image);
            mEnglish = itemView.findViewById(R.id.tv_english);
        }
    }
}
