package life.nsu.calculator;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Calculator mCalculator;

    private EditText mNumber1;
    private EditText mNumber2;
    private TextView mResult;

    Button mAdd;
    Button mSubtraction;
    Button mMultiplication;
    Button mDivision;
    Button mPower;
    Button mAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator();

        mResult = findViewById(R.id.tv_result);
        mNumber1 = findViewById(R.id.et_number_one);
        mNumber2 = findViewById(R.id.et_number_two);

        mAdd = findViewById(R.id.btn_add);
        mSubtraction = findViewById(R.id.btn_sub);
        mMultiplication = findViewById(R.id.btn_mul);
        mDivision = findViewById(R.id.btn_div);
        mPower = findViewById(R.id.btn_pow);
        mAC = findViewById(R.id.btn_ac);

        mAdd.setOnClickListener(v -> onAddition());

        mSubtraction.setOnClickListener(v -> onSubtraction());

        mMultiplication.setOnClickListener(v -> onMultiplication());

        mDivision.setOnClickListener(v -> onDivision());

        mPower.setOnClickListener(v -> onPower());

        mAC.setOnClickListener(v -> onClear());

    }

    public void onAddition() {
        compute(Calculator.Operator.ADD);
    }

    public void onSubtraction() {
        compute(Calculator.Operator.SUB);
    }

    public void onDivision() {
        try {
            compute(Calculator.Operator.DIV);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException", iae);
            mResult.setText(getString(R.string.computationError));
        }
    }

    public void onMultiplication() {
        compute(Calculator.Operator.MUL);
    }

    private void compute(Calculator.Operator operator) {
        double number1;
        double number2;

        try {
            number1 = getOperand(mNumber1);
            number2 = getOperand(mNumber2);
        } catch (NumberFormatException exception) {
            Log.e(TAG, "NumberFormatException", exception);
            mResult.setText(getString(R.string.computationError));
            return;
        }

        String result;

        switch (operator) {
            case ADD:
                result = String.valueOf(mCalculator.addition(number1, number2));
                break;
            case SUB:
                result = String.valueOf(mCalculator.subtraction(number1, number2));
                break;
            case DIV:
                if (number2 == 0.0){
                    result = "Error: Division by 0";
                } else{
                    result = String.valueOf(mCalculator.division(number1, number2));
                }

                break;
            case MUL:
                result = String.valueOf(mCalculator.multiplication(number1, number2));
                break;
            case POW:
                result = String.valueOf(mCalculator.power(number1, number2));
                break;
            default:
                result = getString(R.string.computationError);
                break;
        }
        mResult.setText(result);
    }

    private Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        if (! operandText.equals("")){
            return Double.valueOf(operandText);
        }
        else{
            Log.e(TAG, "Empty string");
            mResult.setText(R.string.blank);
            return 0.0;
        }
    }

    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }

    public void onPower() {
        compute(Calculator.Operator.POW);
    }

    public void onClear() {
        mNumber1.setText("");
        mNumber2.setText("");

        mResult.setText("0.00");
    }

}
