package com.example.Project1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.example.Project1.R;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.example.Project1.Model.Flight;

public class PaymentActivity extends AppCompatActivity {

    RadioGroup paymentGroup;
    RadioButton radioCard, radioUpi, radioNetBanking;
    LinearLayout cardLayout, upiLayout, netLayout;
    Button payButton;
    ImageView backBtn;
    Spinner bankSpinner;
    Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentGroup = findViewById(R.id.paymentGroup);
        radioCard = findViewById(R.id.radioCard);
        radioUpi = findViewById(R.id.radioUpi);
        radioNetBanking = findViewById(R.id.radioNetBanking);
        cardLayout = findViewById(R.id.cardLayout);
        upiLayout = findViewById(R.id.upiLayout);
        netLayout = findViewById(R.id.netLayout);
        payButton = findViewById(R.id.payButton);
        backBtn = findViewById(R.id.backBtn);
        bankSpinner = findViewById(R.id.bankSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select Bank", "HDFC", "ICICI", "SBI", "Axis"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(adapter);

        double amount = getIntent().getDoubleExtra("amount", -1.0);
        flight = (Flight) getIntent().getSerializableExtra("flight");

        if (amount == -1.0 || flight == null) {
            Toast.makeText(this, "Missing data!", Toast.LENGTH_LONG).show();
        } else {
            TextView amountText = findViewById(R.id.amountText);
            amountText.setText("Amount: ₹" + amount);
        }

        paymentGroup.setOnCheckedChangeListener((group, checkedId) -> {
            cardLayout.setVisibility(View.GONE);
            upiLayout.setVisibility(View.GONE);
            netLayout.setVisibility(View.GONE);

            if (checkedId == R.id.radioCard) {
                cardLayout.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioUpi) {
                upiLayout.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioNetBanking) {
                netLayout.setVisibility(View.VISIBLE);
            }
        });

        payButton.setOnClickListener(view -> {
            int selectedId = paymentGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(PaymentActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = false;

            if (selectedId == R.id.radioCard) {
                EditText cardNumber = findViewById(R.id.cardNumber);
                EditText cardName = findViewById(R.id.cardName);
                EditText cardExpiry = findViewById(R.id.cardExpiry);
                EditText cardCvv = findViewById(R.id.cardCvv);

                if (cardNumber.getText().toString().isEmpty() ||
                        cardName.getText().toString().isEmpty() ||
                        cardExpiry.getText().toString().isEmpty() ||
                        cardCvv.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please fill all card details", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = true;
                }

            } else if (selectedId == R.id.radioUpi) {
                EditText upiId = findViewById(R.id.upiId);

                if (upiId.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please enter UPI ID", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = true;
                }

            } else if (selectedId == R.id.radioNetBanking) {
                EditText accountName = findViewById(R.id.accountName);

                if (bankSpinner.getSelectedItemPosition() == 0 || accountName.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please select bank and enter account name", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = true;
                }
            }

            if (isValid) {
                Toast.makeText(PaymentActivity.this, "Processing Payment...", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> {
                    Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PaymentActivity.this, TicketDetailActivity.class);
                    intent.putExtra("flight", flight);
                    startActivity(intent);
                    finish();

                }, 2000);
            }
        });

        backBtn.setOnClickListener(view -> finish());
    }
}
