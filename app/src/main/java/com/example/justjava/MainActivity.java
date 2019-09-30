package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this, "You Cannot have more than 100 coffes", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this, "You Cannot have less than 1 coffes", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField= (EditText) findViewById(R.id.name_field);
        String name= nameField.getText().toString();

        CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.checkbox_whippedCream);
        boolean hasWhippedcream= whippedCreamCheckBox.isChecked();

        CheckBox cocholateCheckBox= (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean hasCocholate= cocholateCheckBox.isChecked();

        int price= calculatePrice(hasWhippedcream,hasCocholate);
        String PriceMessage= createOrderSumary(name,price,hasWhippedcream, hasCocholate);
        //displayMessage(PriceMessage);

        Intent intent= new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.Just_java_order_for)+ name);
        intent.putExtra(Intent.EXTRA_TEXT,PriceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    private int calculatePrice (boolean hasWhippedCream, boolean hasChocolate){
        int price= 10000;
        if(hasWhippedCream){
            price = price + 3000;
        }
        if (hasChocolate){
            price= price + 2000;
        }
        price *= quantity;
        return price;
    }

    private String createOrderSumary(String name, int price, boolean addWhippedCream, boolean addChocolate){
       String PriceMessage= getString(R.string.name)+":" + name;
       PriceMessage +="\n"+getString(R.string.add_whipped_cream)+addWhippedCream;
       PriceMessage +="\n"+ getString(R.string.add_chocolate) +addChocolate;
       PriceMessage +="\n"+ getString(R.string.quantity) +":"+ quantity;
       PriceMessage +="\n"+getString(R.string.total)+": Rp." + price;
       PriceMessage +="\n"+getString(R.string.thank_you);
       return PriceMessage;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
//    private void displayprice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
//    private void displayMessage (String message ){
//        TextView priceTextView=(TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(message);
//    }
}