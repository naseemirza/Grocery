package solutions.thinkbiz.grocery;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsActivity extends AppCompatActivity {

    TextView Cphone,Cemail,Caddress;
    EditText Uname,Uemail,Uphone,Umsg;

    Button Submitbtn;
    ProgressDialog progressDialog;

    String CPhone, CEmail, CAddress;

    LinearLayout linearLayoutcall,linearLayoutmail;
    String mname,memail,mcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        mname = pref.getString("Myname", "");
        memail = pref.getString("Myemail", "");
        mcontact = pref.getString("Myphone", "");

        Cphone=(TextView)findViewById(R.id.textViewname);
        Cemail=(TextView)findViewById(R.id.textViewname1);
        Caddress=(TextView)findViewById(R.id.textViewname2);

        Uname=(EditText)findViewById(R.id.fname);
        Uemail=(EditText)findViewById(R.id.editTextemail);
        Uphone=(EditText)findViewById(R.id.phone);
        Umsg=(EditText)findViewById(R.id.editText2);

        //Log.e("name",mname);

        Uname.setText(mname);
        Uemail.setText(memail);
        Uphone.setText(mcontact);

        //Call

        linearLayoutcall = (LinearLayout) findViewById(R.id.li1);
        linearLayoutcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = String.valueOf(CPhone);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                intent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                startActivity(intent);
            }
        });


        //Mail

        linearLayoutmail = (LinearLayout) findViewById(R.id.li2);
        linearLayoutmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.e("name", CEmail);

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + CEmail)); // only email apps should handle this
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

//                Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
//                PackageManager pm = getPackageManager();
//
//                List<ResolveInfo> resInfo = pm.queryIntentActivities(emailIntent, 0);
//                if (resInfo.size() > 0) {
//                    ResolveInfo ri = resInfo.get(0);
//                    // First create an intent with only the package name of the first registered email app
//                    // and build a picked based on it
//                    Intent intentChooser = pm.getLaunchIntentForPackage(ri.activityInfo.packageName);
//                    Intent openInChooser =
//                            Intent.createChooser(intentChooser,
//                                    getString(R.string.mail));
//
//                    // Then create a list of LabeledIntent for the rest of the registered email apps
//                    List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
//                    for (int i = 1; i < resInfo.size(); i++) {
//                        // Extract the label and repackage it in a LabeledIntent
//                        ri = resInfo.get(i);
//                        String packageName = ri.activityInfo.packageName;
//                        Intent intent = pm.getLaunchIntentForPackage(packageName);
//                        intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
//                    }
//
//                    LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
//                    // Add the rest of the email apps to the picker selection
//                    openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
//                    startActivity(openInChooser);
//                }

            }
        });

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

            String url="https://demotbs.com/dev/grocery/webservices/contact_address";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray rootJsonArray = new JSONArray(response);

                                Log.e("rootJsonArrayLength",rootJsonArray.length()+"");

                                for (int i = 0; i < rootJsonArray.length(); i++) {
                                    JSONObject obj = rootJsonArray.getJSONObject(i);

                                     CPhone=obj.getString("phone");
                                     CEmail=obj.getString("email");
                                     CAddress=obj.getString("address");
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

        String url="https://demotbs.com/dev/grocery/webservices/contactForm?email="+email+"&name="+name+"&phone="+phone+"&message="+message;
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
