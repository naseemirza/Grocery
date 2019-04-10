package solutions.thinkbiz.grocery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText=(EditText)findViewById(R.id.editTextU);
        button=(Button)findViewById(R.id.buttonL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate())
                {
                    FogotPass();
                }
            }
        });
    }

    private boolean isValidate()
    {
        final String email = editText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editText.setError("Please enter your email");
            editText.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Enter a valid email");
            editText.requestFocus();
            return false;
        }

        return true;
    }

    private void FogotPass() {

        progressDialog = new ProgressDialog(ForgetPassActivity.this);
        progressDialog.setMessage("Signing In...");
        progressDialog.show();

        final String email = editText.getText().toString().trim();

        String url="http://demotbs.com/dev/grocery/webservices/forget_password";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success= obj.getString("s");
                            String error= obj.getString("e");
                            String msg=obj.getString("m");
                            String user_id=obj.getString("user_id");
                            Log.e("uid",user_id);

                            if (success.equalsIgnoreCase("1"))
                            {
                                Toast.makeText(ForgetPassActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                               // Intent intent=new Intent(ForgetPassActivity.this, MainActivity.class);
                               // startActivity(intent);
                                editText.setText("");

                            }
                            else
                            {
                                Toast.makeText(ForgetPassActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ForgetPassActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgetPassActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ForgetPassActivity.this);
        queue.add(stringRequest);
    }
}
