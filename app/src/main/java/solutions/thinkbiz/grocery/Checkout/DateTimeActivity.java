package solutions.thinkbiz.grocery.Checkout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import solutions.thinkbiz.grocery.R;

public class DateTimeActivity extends AppCompatActivity {

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button submit;
    ProgressDialog progressDialog;
    String userId, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Select date and time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = pref.getString("user_id", "");
        price = pref.getString("Tprice", "");

       // Log.e("name", userId);
      //  Log.e("name", price);

        btnDatePicker=(Button)findViewById(R.id.selectdate);
        btnTimePicker=(Button)findViewById(R.id.selecttime);
        txtDate=(EditText)findViewById(R.id.set_date);
        txtTime=(EditText)findViewById(R.id.set_time);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(DateTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(DateTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

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
        if (txtDate.getText().toString().length() == 0) {
            txtDate.setError("Please select date");
            txtDate.requestFocus();
            return false;
        }

        if (txtTime.getText().toString().length() == 0) {
            txtTime.setError("Please select time");
            txtTime.requestFocus();
            return false;
        }

        return true;
    }

    private void Senddata() {

        progressDialog = new ProgressDialog(DateTimeActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final String datetxt=txtDate.getText().toString();
        final String timetxt=txtTime.getText().toString();
        final String datetimetxt=datetxt+" "+timetxt;

        String url="http://memorstoreonline.com/webservices/collectByStore?user_id="+userId+"&date_time="+datetimetxt+"&total_amount="+price;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("resp",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg=obj.getString("status");
                            String orderid=obj.getString("order_id");

                            Toast.makeText(DateTimeActivity.this, msg, Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(DateTimeActivity.this,StoreRespActivity.class));
                            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putString("OrderId", orderid);

                            edit.apply();
                            Intent intent = new Intent(DateTimeActivity.this, StoreRespActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DateTimeActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DateTimeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(DateTimeActivity.this);
        requestQueue.add(stringRequest);

    }
}