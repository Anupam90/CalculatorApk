package com.timbuchalka.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText resultEditText;
    private EditText newNumberEditText;
    private TextView displayOperation;

    //variables to hold the operands and type of calculation
    private Double operand1 = null;
    private String pendingOperation = "=";

    private static final String STATE_PENDING_OPERATION =  "pendingOperation";
    private static final String STATE_OPERAND1 =  "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEditText = (EditText) findViewById(R.id.resultEdittext);
        newNumberEditText = (EditText) findViewById(R.id.newNumberEditText);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumberEditText.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String operation = b.getText().toString();
                String value = newNumberEditText.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, operation);
                } catch (NumberFormatException e) {
                    newNumberEditText.setText("");
                }
                pendingOperation = operation;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        findViewById(R.id.buttonNeg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumberEditText.getText().toString();
                if(value.length() == 0)
                {
                    newNumberEditText.setText("-");
                }
                else
                {
                    try
                    {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumberEditText.setText(doubleValue.toString());
                    }
                    catch (NumberFormatException e)
                    {
                        //new number  was " - " & " . " , so clear it
                        newNumberEditText.setText("");
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,pendingOperation);
        if(operand1 != null)
        {
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }

        }
        resultEditText.setText(operand1.toString());
        newNumberEditText.setText("");
    }
}
