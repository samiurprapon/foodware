package life.nsu.bangladictionary.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import io.paperdb.Paper;
import life.nsu.bangladictionary.R;
import life.nsu.bangladictionary.models.Vocabulary;
import life.nsu.bangladictionary.utils.OnItemClickListener;
import life.nsu.bangladictionary.utils.RecyclerViewClickLister;
import life.nsu.bangladictionary.utils.VocabularyAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView mLocale;

    private EditText mEnglishWord;
    private TextView mbanglaMeaning;
    private TextView mTitle;

    private ImageButton mSearch;
    private ImageButton mInsert;
    private ImageButton mList;

    private RecyclerView mRecyclerView;
    private VocabularyAdapter adapter;

    private boolean flag = false;
    private boolean languageFlag = false;

    ArrayList<Vocabulary> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        changeLocale("bn");
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_main);

        mLocale = findViewById(R.id.tv_locale);

        mEnglishWord = findViewById(R.id.et_search);
        mbanglaMeaning = findViewById(R.id.tv_result);
        mTitle = findViewById(R.id.tv_title_dictionary);

        mSearch = findViewById(R.id.ib_search);
        mInsert = findViewById(R.id.ib_save);
        mList = findViewById(R.id.ib_list);

        Paper.init(this);
        wordList = Paper.book().read("words", new ArrayList<>());

        if (wordList.size() == 0) {
            // load from csv file
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(true);

        mInsert.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
            intent.putExtra("english", mEnglishWord.getText().toString());
            startActivity(intent);
        });
        mList.setOnClickListener(view -> {
            flag = !flag;
            mList.setSelected(flag);

            if (flag) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mTitle.setVisibility(View.VISIBLE);

                initializeRecyclerView();
            } else {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mTitle.setVisibility(View.INVISIBLE);
            }

        });

        mSearch.setOnClickListener(view -> {
            if (!mEnglishWord.getText().toString().isEmpty()) {
                searchMeanings();
            }
        });

        mLocale.setOnClickListener(view -> {
            Log.d("fckt", Locale.getDefault().getDisplayLanguage());
            mLocale.setEnabled(true);

            languageFlag = !languageFlag;
            changeLocale(languageFlag);

            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);//Start the same Activity
            finish(); //finish Activity.

        });
    }

    private void searchMeanings() {
        mbanglaMeaning.setText(null);

        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getEnglish().toLowerCase().equals(mEnglishWord.getText().toString().toLowerCase())) {
                mbanglaMeaning.append(wordList.get(i).getBangla() + "\n");
            }
        }

        if (mbanglaMeaning.getText().toString().isEmpty()) {
            mbanglaMeaning.setText(R.string.toast_message_not_found);
        }

    }

    private void initializeRecyclerView() {
        adapter = new VocabularyAdapter(this);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickLister(MainActivity.this, mRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Vocabulary vocabulary = adapter.getVocabulary(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
                builder.setTitle(vocabulary.getEnglish());
                builder.setMessage(vocabulary.getBangla());

                builder.setCancelable(true);
                builder.show();

            }
        }));


        mRecyclerView.setAdapter(adapter);
        adapter.setVocabularyList(wordList);

    }

    private void changeLocale(boolean change) {
        Configuration config = new Configuration();

        if(change) {
            Locale locale = new Locale("bn");
            Locale.setDefault(locale);

            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        } else {
            Locale.setDefault(Locale.ENGLISH);

            config.locale = Locale.ENGLISH;
            onConfigurationChanged(config);
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

}