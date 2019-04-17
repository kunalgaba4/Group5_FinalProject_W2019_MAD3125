package com.goodcompany.group5_finalproject_w2019_mad3125.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Singelton.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private CardForm cardForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        cardForm = findViewById(R.id.card_form);
        TextView amount = (TextView) (cardForm.getRootView().findViewById(R.id.payment_amount));
        amount.setText(String.valueOf(ShoppingCart.ourInstance.getTotalPrice()));
        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                ShoppingCart.ourInstance.removeEverythingFromCart();
                Intent i = new Intent(CheckoutActivity.this, ConformationActivity.class);
                startActivity(i);
            }
        });

        handleSpinner();
    }

    private void handleSpinner() {

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.addressSpin);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("59 First Gulf Blvd");
        categories.add("B25 Peel Centre Drive");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
