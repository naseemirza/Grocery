package solutions.thinkbiz.grocery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import solutions.thinkbiz.grocery.Checkout.CheckOutActivity;
import solutions.thinkbiz.grocery.Checkout.OrderModel;

public class AddressActivity extends AppCompatActivity {

    String userId,symbol, name, email, phone, price;
    TextView currencytxt, pricetxtt, nametxt;
    EditText emailtxt,editTextphone,citytext,ziptext,addrsstext,msgtext,statetext;
    Button continuebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        getSupportActionBar().setTitle("Shipping Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //CheckOutActivity main = new CheckOutActivity();
        //main.parseJSON1();

//        Intent iin= getIntent();
//        Bundle b = iin.getExtras();
//
//        if(b!=null)
//        {
//            String j =(String) b.get("Tprice");
//            Log.e("price",j);
//            pricetxtt.setText(j);
//        }

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = pref.getString("user_id", "");
        symbol = pref.getString("crncy", "");
        name = pref.getString("Myname", "");
        email = pref.getString("Myemail", "");
        phone = pref.getString("Myphone", "");
        price = pref.getString("Tprice", "");

        //Log.e("Myname",name);

//        emailtxt = (EditText) findViewById(R.id.editTextem);
//        editTextphone = (EditText) findViewById(R.id.phone);
//        citytext = (EditText) findViewById(R.id.city);
//        statetext = (EditText) findViewById(R.id.state);
//        ziptext = (EditText) findViewById(R.id.zipcode);
        addrsstext = (EditText) findViewById(R.id.editText2);
       // msgtext = (EditText) findViewById(R.id.message);

        currencytxt=(TextView)findViewById(R.id.crncytype);
        pricetxtt=(TextView)findViewById(R.id.pricetotal);
        nametxt=(TextView)findViewById(R.id.textname);
       // emailtxt.setText(email);
       // editTextphone.setText(phone);

        nametxt.setText(name);
        currencytxt.setText(symbol);
        pricetxtt.setText(price);


        continuebtn=(Button)findViewById(R.id.checkout);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate())
                {
                    PayPalPage();
                }

                //Intent intent = new Intent(AddressActivity.this, PaypalActivity.class);
                //startActivity(intent);
            }
        });
    }

    private boolean isValidate()
    {
        if (addrsstext.getText().toString().length() == 0) {
            addrsstext.setError("Please enter your address");
            addrsstext.requestFocus();
            return false;
        }
        return true;
    }

    private void PayPalPage() {

        String addressTxt=addrsstext.getText().toString();

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("Address",addressTxt);
        edit.apply();

        Intent intent = new Intent(AddressActivity.this, PaypalActivity.class);
        startActivity(intent);
    }

}
