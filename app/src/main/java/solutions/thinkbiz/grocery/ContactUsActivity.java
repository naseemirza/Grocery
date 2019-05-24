package solutions.thinkbiz.grocery;

import android.app.ProgressDialog;
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

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {

    TextView Cphone,Cemail,Caddress;
    EditText Uname,Uemail,Uphone,Umsg;

    Button Submitbtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cphone=(TextView)findViewById(R.id.textViewname);
        Cemail=(TextView)findViewById(R.id.textViewname1);
        Caddress=(TextView)findViewById(R.id.textViewname2);

        Uname=(EditText)findViewById(R.id.fname);
        Uemail=(EditText)findViewById(R.id.editTextemail);
        Uphone=(EditText)findViewById(R.id.phone);
        Umsg=(EditText)findViewById(R.id.editText2);

        ContactUs();

        Submitbtn=(Button)findViewById(R.id.buttonsbmt);

        Submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate())
                {
                    ContctUs();
                }
            }
        });

    }

    private void ContactUs() {

            String url="http://demotbs.com/dev/grocery/webservices/contact_address";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray rootJsonArray = new JSONArray(response);

                                Log.e("rootJsonArrayLength",rootJsonArray.length()+"");

                                for (int i = 0; i < rootJsonArray.length(); i++) {
                                    JSONObject obj = rootJsonArray.getJSONObject(i);

                                    String CPhone=obj.getString("phone");
                                    String CEmail=obj.getString("email");
                                    String CAddress=obj.getString("address");
                                    // Log.e("dls", delas);

                                    Cphone.setText(CPhone);
                                    Cemail.setText(CEmail);
                                    Caddress.setText(CAddress);

                                }

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

            RequestQueue queue2 = Volley.newRequestQueue(ContactUsActivity.this);
            queue2.add(stringRequest);
        }

    private boolean isValidate()
    {
        final String email = Uemail.getText().toString().trim();

        if (Uname.getText().toString().length() == 0) {
            Uname.setError("Name not entered");
            Uname.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Uemail.setError("Please enter your email");
            Uemail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Uemail.setError("Enter a valid email");
            Uemail.requestFocus();
            return false;
        }
//        if (Uphone.getText().toString().length() == 0) {
//            Uphone.setError("Phone number not entered");
//            Uphone.requestFocus();
//            return false;
//        }
//
//        if (Umsg.getText().toString().length() == 0) {
//            Umsg.setError("Please enter message");
//            Umsg.requestFocus();
//            return false;
//        }

        return true;
    }


    private void ContctUs(){

        progressDialog = new ProgressDialog(ContactUsActivity.this);
        progressDialog.setMessage("Information Sending ...");
        progressDialog.show();

        final String name = Uname.getText().toString().trim();
        final String email = Uemail.getText().toString().trim();
        final String phone = Uphone.getText().toString().trim();
        final String message = Umsg.getText().toString().trim();

        String url="http://demotbs.com/dev/grocery/webservices/contactForm?email="+email+"&name="+name+"&phone="+phone+"&message="+message;
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
                                Toast.makeText(ContactUsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Uname.setText("");
                                Uemail.setText("");
                                Uphone.setText("");
                                Umsg.setText("");

                            }
                            else {
                                Toast.makeText(ContactUsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ContactUsActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactUsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
