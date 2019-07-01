package solutions.thinkbiz.grocery.Checkout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import solutions.thinkbiz.grocery.AddressActivity;
import solutions.thinkbiz.grocery.LoginActivity;
import solutions.thinkbiz.grocery.PaypalActivity;
import solutions.thinkbiz.grocery.R;
import solutions.thinkbiz.grocery.RegisterActivity;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOfferDetaillsActivity;


public class CheckOutActivity extends AppCompatActivity {

    private OrderAdapter mExampleAdapter1;
    private ArrayList<OrderModel> mExampleList1;
    private ArrayList<OrderModel> pricelist=new ArrayList<>();
    private RequestQueue mRequestQueue1;
    private RecyclerView mRecyclerview1;

    TextView currencytxt, pricetxtt;
    Button continuebtn;
   public static int total;
    String userId,symbol;

    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;
   // Button dlivrbtn, Clctstrbtn;

    String mname,memail,mcontact;

    EditText nametxt,streettxt,towntxt,pincodetxt,contacttxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        getSupportActionBar().setTitle("Cart Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = pref.getString("user_id", "");
        symbol = pref.getString("crncy", "");

        mname = pref.getString("Myname", "");
        memail = pref.getString("email", "");
        mcontact = pref.getString("phone", "");

        //Log.e("name",mname);
       // Log.e("name",memail);
       // Log.e("name",mcontact);


        currencytxt=(TextView)findViewById(R.id.crncytype);
        pricetxtt=(TextView)findViewById(R.id.pricetotal);
        continuebtn=(Button)findViewById(R.id.checkout);

        nametxt=(EditText)findViewById(R.id.nametxt);
        streettxt=(EditText)findViewById(R.id.streetname);
        towntxt=(EditText)findViewById(R.id.towntxt);
        pincodetxt=(EditText)findViewById(R.id.zipcode);
        contacttxt=(EditText)findViewById(R.id.phone);

       // nametxt.setText(mname);
        //contacttxt.setText(mcontact);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.rb1);
        radioButton2 = (RadioButton) findViewById(R.id.rb2);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CheckOutActivity.this, DeliverToMeActivity.class);
                startActivity(intent);

            }

        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("Tprice", String.valueOf(total));

                edit.apply();
                Intent intent=new Intent(CheckOutActivity.this, DateTimeActivity.class);
                startActivity(intent);

            }
        });

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("Tprice", String.valueOf(total));

                edit.apply();
                Intent intent = new Intent(CheckOutActivity.this, PaypalActivity.class);
                startActivity(intent);
                //intent.putExtra("Tprice", String.valueOf(total));
            }
        });

        mExampleList1 = new ArrayList<>();
        mRequestQueue1 = Volley.newRequestQueue(this);
        mRecyclerview1=(RecyclerView)findViewById(R.id.myRecyclerID);
        mRecyclerview1.setNestedScrollingEnabled(false);
        mRecyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerview1.setHasFixedSize(true);

        parseJSON1();
    }



    public void parseJSON1() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        String url="http://demotbs.com/dev/grocery/webservices/getAllCart?user_id="+userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Log.e("rootJsonArray",response);
                        progressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONArray rootJsonArray = new JSONArray(response);
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                mExampleList1.add(new OrderModel(object.optString("product_id"),
                                        object.optString("cart_id"),
                                        object.optString("image"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("sub_total"),
                                        object.optString("price"),
                                        object.optString("qty")));
                            }

                            TotalPrice();
                            
                            Log.e("rootJsonArray",mExampleList1.size()+"");                  //deleteitem,updatemethod
                            mExampleAdapter1 = new OrderAdapter(CheckOutActivity.this, mExampleList1);
                            mRecyclerview1.setAdapter(mExampleAdapter1);
                            mExampleAdapter1.notifyDataSetChanged();
                            mRecyclerview1.setHasFixedSize(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAg",error.getMessage());
                    }
                });

        mRequestQueue1 = Volley.newRequestQueue(this);
        mRequestQueue1.add(stringRequest);
    }

    public long AddMethod(final String cartID, final int qunty1) {

        //Log.e("qty", String.valueOf(qunty1));

        String url="http://demotbs.com/dev/grocery/webservices/update_cart?quantity="+qunty1+"&cart_id="+cartID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.e("cid",response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success= obj.getString("s");
                            String error= obj.getString("e");
                            String msg=obj.getString("m");

                            if (success.equalsIgnoreCase("1"))
                            {
                                Toast.makeText(CheckOutActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CheckOutActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                           mExampleList1.clear();
                               parseJSON1();
                            mExampleAdapter1.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CheckOutActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(CheckOutActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(CheckOutActivity.this);
        queue.add(stringRequest);

        return 1;
    }

    public void DeleteMethod(final String cartID) {

        String url="http://demotbs.com/dev/grocery/webservices/delete_cart?cart_id="+cartID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                     // Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success= obj.getString("s");
                            String error= obj.getString("e");
                            String msg=obj.getString("m");

                            Log.e("cid",msg);

                            if (success.equalsIgnoreCase("1"))
                            {
                                Toast.makeText(CheckOutActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(CheckOutActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                            mExampleList1.clear();
                            parseJSON1();
                            mExampleAdapter1.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CheckOutActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(CheckOutActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue2 = Volley.newRequestQueue(CheckOutActivity.this);
        queue2.add(stringRequest);

    }
   public void TotalPrice() {

        total = 0;

        for(int i = 0; i < mExampleList1.size(); i++)
        {
            total += mExampleList1.get(i).getTotalprice();

        }

      //  Log.e("stotal", String.valueOf(total));
         currencytxt.setText(symbol);
         pricetxtt.setText(String.valueOf(total));

    }

}
