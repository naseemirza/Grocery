package solutions.thinkbiz.grocery.Checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import solutions.thinkbiz.grocery.PaypalActivity;
import solutions.thinkbiz.grocery.R;

public class DeliverToMeActivity extends AppCompatActivity {

    EditText nametxt,streettxt,towntxt,pincodetxt,contacttxt;
    Button submit;
    String userId,name, street, town, pincode, contact,price;
    ProgressDialog progressDialog;

    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_to_me);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Delivery Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = pref.getString("user_id", "");
        price = pref.getString("Tprice", "");

        name = pref.getString("Myname", "");
        street = pref.getString("street", "");
        town = pref.getString("town", "");
        pincode = pref.getString("pincode", "");
        contact = pref.getString("Myphone", "");
        //contact1 = pref.getString("phone", "");


        //Log.e("uid",userId);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.rb1);
        radioButton2 = (RadioButton) findViewById(R.id.rb2);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("Tprice", price);

                edit.apply();
                Intent intent = new Intent(DeliverToMeActivity.this, PaypalActivity.class);
                startActivity(intent);

            }

        });


        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("Tprice", price);

                edit.apply();
                Intent intent=new Intent(DeliverToMeActivity.this, DateTimeForPODActivity.class);
                startActivity(intent);

            }
        });



        nametxt=(EditText)findViewById(R.id.nametxt);
        streettxt=(EditText)findViewById(R.id.streetname);
        towntxt=(EditText)findViewById(R.id.towntxt);
        pincodetxt=(EditText)findViewById(R.id.zipcode);
        contacttxt=(EditText)findViewById(R.id.phone);

        nametxt.setText(name);
        streettxt.setText(street);
        towntxt.setText(town);
        pincodetxt.setText(pincode);
        contacttxt.setText(contact);

        //contacttxt.setText(contact1);

        submit=(Button)findViewById(R.id.buttonSt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidate())
                {
                    Senddata();
                }
            }
        });

    }

    private boolean isValidate()
    {
        if (nametxt.getText().toString().length() == 0) {
            nametxt.setError("Please enter your name");
            nametxt.requestFocus();
            return false;
        }

        if (streettxt.getText().toString().length() == 0) {
            streettxt.setError("Please enter your street");
            streettxt.requestFocus();
            return false;
        }

        if (towntxt.getText().toString().length() == 0) {
            towntxt.setError("Please enter your town");
            towntxt.requestFocus();
            return false;
        }

        if (pincodetxt.getText().toString().length() == 0) {
            pincodetxt.setError("Please enter your postcode");
            pincodetxt.requestFocus();
            return false;
        }

        if (contacttxt.getText().toString().length() == 0) {
            contacttxt.setError("Please enter your contact number");
            contacttxt.requestFocus();
            return false;
        }
        return true;
    }

    private void Senddata() {

        progressDialog = new ProgressDialog(DeliverToMeActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final String name=nametxt.getText().toString();
        final String street=streettxt.getText().toString();
        final String town=towntxt.getText().toString();
        final String pincode=pincodetxt.getText().toString();
        final String contact=contacttxt.getText().toString();

     //   Log.e("name", userId);
        String url="https://demotbs.com/dev/grocery/webservices/shipping_address?user_id="+userId+"&name="+name+"&street_name="+street+"&town="+town+"&postal_code="+pincode+"&contact_number="+contact;
                        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("resp",response);
                                         progressDialog.dismiss();
                                        try {
                                            JSONObject obj = new JSONObject(response);
                                            String success= obj.getString("s");
                                            String error= obj.getString("e");
                                            String msg=obj.getString("m");

                                            if (success.equalsIgnoreCase("1"))
                                            {
                                                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor edit = pref.edit();
                                                edit.putString("Myname",name);
                                                edit.putString("street",street);
                                                edit.putString("town",town);
                                                edit.putString("pincode",pincode);
                                                edit.putString("phone",contact);
                                                edit.apply();

                                                Toast.makeText(DeliverToMeActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(DeliverToMeActivity.this, PaypalActivity.class));
                                                progressDialog.dismiss();

                                            }
                                            else {
                                                Toast.makeText(DeliverToMeActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(DeliverToMeActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(DeliverToMeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                         progressDialog.dismiss();
                                    }
                                });
//                        {
//                            @Override
//
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String, String> params = new HashMap<>();
//                                params.put("user_id", userId);
//                                params.put("name", name);
//                                params.put("street_name", street);
//                                params.put("town", town);
//                                params.put("postal_code", pincode);
//                                params.put("contact_number", contact);
//                                return params;
//                            }
//                        };
                        RequestQueue requestQueue= Volley.newRequestQueue(DeliverToMeActivity.this);
                        requestQueue.add(stringRequest);

    }
}
