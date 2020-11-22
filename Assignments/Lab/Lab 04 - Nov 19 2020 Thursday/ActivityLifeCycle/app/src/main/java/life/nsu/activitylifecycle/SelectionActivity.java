package life.nsu.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCheese;
    private Button mRice;
    private Button mApple;
    private Button mPickles;
    private Button mMilk;
    private Button mMango;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mCheese = findViewById(R.id.btn_cheese);
        mRice = findViewById(R.id.btn_rice);
        mApple = findViewById(R.id.btn_apple);
        mPickles = findViewById(R.id.btn_pickles);
        mMilk = findViewById(R.id.btn_milk);
        mMango = findViewById(R.id.btn_mango);

        mCheese.setOnClickListener(this);
        mRice.setOnClickListener(this);
        mApple.setOnClickListener(this);
        mPickles.setOnClickListener(this);
        mMilk.setOnClickListener(this);
        mMango.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cheese:
                select("Cheese");
                break;
            case R.id.btn_rice:
                select("Rice");
                break;
            case R.id.btn_apple:
                select("Apple");
                break;
            case R.id.btn_pickles:
                select("Pickles");
                break;
            case R.id.btn_milk:
                select("Milk");
                break;
            case R.id.btn_mango:
                select("Mango");
                break;
        }

    }

    private void select(String item) {
        Intent intent = new Intent(SelectionActivity.this, MainActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }


}