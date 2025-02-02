package com.github.pawel09890.obliczanienapiwku;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Interface -  1 linia
    private EditText amouEditText;
    private TextView amountTextView;

    // Interface -  2 linia
    private TextView percentTextView;
    private SeekBar percentseekBar;


    // Interface -  3 linia
    private TextView tipLabelTextView;
    private TextView tipTextView;

    // Interface -  4 linia
    private TextView totalLabelTextView;
    private TextView totalTextView;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    // Rachunek wartosc - user

    private double billAmount = 0.0;
    private double tipPercent = 0.15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        amouEditText = findViewById(R.id.amountEditText);
        amountTextView = findViewById(R.id.amountTextView);

        percentTextView = findViewById(R.id.percentTextView);
        percentseekBar = findViewById(R.id.percentseekBar);

        tipLabelTextView = findViewById(R.id.tipLabelTextView);
        tipTextView = findViewById(R.id.tipTextView);

        totalLabelTextView = findViewById(R.id.totalLabelTextView);
        totalTextView = findViewById(R.id.totalTextView);

        amouEditText.requestFocus();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        amouEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    billAmount = Double.parseDouble(s.toString())/100.0;
                    amountTextView.setText(currencyFormat.format(billAmount));

                } catch (NumberFormatException e) {
                    amountTextView.setText("");
                    billAmount=0.0;
                }
                calculateTipAndTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        percentseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercent = progress / 100.0;
                calculateTipAndTotalAmount();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void calculateTipAndTotalAmount(){

        double tipAmount= billAmount * tipPercent;
        double totalAmount = billAmount + tipAmount;

        percentTextView.setText(percentFormat.format(tipPercent));
        tipTextView.setText(currencyFormat.format(tipAmount));
        totalTextView.setText(currencyFormat.format(totalAmount));

    }
}