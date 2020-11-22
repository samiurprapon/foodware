package life.nsu.activitylifecycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> shoppingList;
    SharedPreferences preferences;

    FloatingActionButton mAdd;
    TextView mText1;
    TextView mText2;
    TextView mText3;
    TextView mText4;
    TextView mText5;
    TextView mText6;
    TextView mText7;
    TextView mText8;
    TextView mText9;
    TextView mText10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        shoppingList = Paper.book().read("items", new ArrayList<>());

        if(getIntent().getStringExtra("item") != null) {
            shoppingList.add(getIntent().getStringExtra("item"));
            Paper.book().write("items", shoppingList);
        }

        // ui elements
        mText1 = findViewById(R.id.tv_text1);
        mText2 = findViewById(R.id.tv_text2);
        mText3 = findViewById(R.id.tv_text3);
        mText4 = findViewById(R.id.tv_text4);
        mText5 = findViewById(R.id.tv_text5);
        mText6 = findViewById(R.id.tv_text6);
        mText7 = findViewById(R.id.tv_text7);
        mText8 = findViewById(R.id.tv_text8);
        mText9 = findViewById(R.id.tv_text9);
        mText10 = findViewById(R.id.tv_text10);

        mAdd = findViewById(R.id.fab_add);

        if(!shoppingList.isEmpty()) {
            if(shoppingList.get(0) != null) {
                mText1.setText(shoppingList.get(0));
            }
            if(shoppingList.size() >= 2 && shoppingList.get(1) != null) {
                mText2.setText(shoppingList.get(1));
            }
            if(shoppingList.size() >= 3 && shoppingList.get(2) != null) {
                mText3.setText(shoppingList.get(2));
            }
            if(shoppingList.size() >= 4 && shoppingList.get(3) != null) {
                mText4.setText(shoppingList.get(3));
            }
            if(shoppingList.size() >= 5 && shoppingList.get(4) != null) {
                mText5.setText(shoppingList.get(4));
            }
            if(shoppingList.size() >= 6 && shoppingList.get(5) != null) {
                mText6.setText(shoppingList.get(5));
            }
            if(shoppingList.size() >= 7 && shoppingList.get(6) != null) {
                mText7.setText(shoppingList.get(6));
            }
            if(shoppingList.size() >= 8 && shoppingList.get(7) != null) {
                mText8.setText(shoppingList.get(7));
            }
            if(shoppingList.size() >= 9 && shoppingList.get(8) != null) {
                mText9.setText(shoppingList.get(8));
            }
            if(shoppingList.size() >= 10 && shoppingList.get(9) != null) {
                mText10.setText(shoppingList.get(9));
            }

        }

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                startActivity(intent);
            }
        });
    }
}