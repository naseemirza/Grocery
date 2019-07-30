package solutions.thinkbiz.grocery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static solutions.thinkbiz.grocery.MainActivity.token;

public class LoginActivity extends AppCompatActivity {

    TextView reg, forgetpass;
    EditText editTextEmail,editTextPass;
    Button buttonLogn;
    ProgressDialog progressDialog;
    public static Boolean booltype;
    public static Boolean ads;
    ImageView Lognimg;
    String name, email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        booltype=pref.getBoolean("Booltype", Boolean.parseBoolean(""));
        name = pref.getString("Myname", "");
        email = pref.getString("Myemail", "");
        phone = pref.getString("Myphone", "");

        if(booltype){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
        }

        token =  FirebaseInstanceId.getInstance().getToken();
       // Log.d("token", token);

        Lognimg=(ImageView)findViewById(R.id.imageView2);

        LoginImage();

        editTextEmail=(EditText)findViewById(R.id.editTextU);
        editTextEmail.setText(email);
        editTextPass=(EditText)findViewById(R.id.editTextP);

        reg = (TextView) findViewById(R.id.textViewRgs);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetpass = (TextView) findViewById(R.id.textViewfrgt);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });

        buttonLogn=(Button)findViewById(R.id.buttonL);
        buttonLogn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                //startActivity(intent);

                if (isOnline()) {

                } else {
                    try {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this).create();

                        alertDialog.setTitle("Info");
                        alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                        alertDialog.setIcon(R.drawable.ic_warning_black_24dp);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                            }
                        });
                        alertDialog.show();

                    } catch (Exception e) {
                    }
                }

                if(isValidate())
                {
                    Loginbtn();
                }
            }
        });

    }

    private void LoginImage() {

        String url="https://demotbs.com/dev/grocery/webservices/login_image";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //      Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg=obj.getString("login_image");
                            String path="https://demotbs.com/dev/grocery/assets/editor_upload_images/image/"+msg;
                            Log.e("Response", path);
                            Glide.with(getApplicationContext())
                                    .load(path)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .fitCenter()
                                    .into(Lognimg);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(CheckOutActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue2 = Volley.newRequestQueue(LoginActivity.this);
        queue2.add(stringRequest);

    }


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            // Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isValidate()
    {
        final String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return false;
        }

        if (editTextPass.getText().toString().length() == 0) {
            editTextPass.setError("Password not entered");
            editTextPass.requestFocus();
            return false;
        }
        if (editTextPass.getText().toString().length() < 5) {
            editTextPass.setError("Password should be atleast of 5 charactors");
            editTextPass.requestFocus();
            return false;
        }

        return true;
    }

    private void Loginbtn() {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Signing In...");
        progressDialog.show();

       // final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPass.getText().toString().trim();

        String url="https://demotbs.com/dev/grocery/webservices/login?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("resp",response);
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success= obj.getString("s");
                            String error= obj.getString("e");
                            String msg=obj.getString("m");
                            String user_id=obj.getString("user_id");
                            Log.e("uid",user_id);
                           // String name=obj.getString("user_name");
                          //  String email=obj.getString("user_email");
                            String phone=obj.getString("user_phone");

                            if (success.equalsIgnoreCase("1"))
                            {
                                booltype=true;
                                ads=true;
                                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString("Myname",name);
                                edit.putString("user_id",user_id);
                               // edit.putString("name",name);
                                edit.putString("Myemail",email);
                                edit.putBoolean("Booltype",booltype);
                                edit.putBoolean("Booltype1",ads);
                                edit.putString("Myphone",phone);

                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                edit.apply();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                editTextEmail.setText("");
                                editTextPass.setText("");
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("device_token", token);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
    }

}
