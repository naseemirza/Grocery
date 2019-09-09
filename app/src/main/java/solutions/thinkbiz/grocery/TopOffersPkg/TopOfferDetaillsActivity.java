package solutions.thinkbiz.grocery.TopOffersPkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import solutions.thinkbiz.grocery.Checkout.CheckOutActivity;
import solutions.thinkbiz.grocery.MainActivity;
import solutions.thinkbiz.grocery.R;


public class TopOfferDetaillsActivity extends AppCompatActivity {

    ImageButton IncreseBtn,DecreseBtn;
    int minteger=1;

    Button backbtn;
    TextView Prdnametxt;

    ImageView imageView;
    TextView textViewcrncy, textViewprice, textViewDescrp;
    Button addtocart;
    Vibrator vibrator;

    String prdname,prdid,prdcrncy,prdprice,prdDescrp, prdimg, userId;

    TextView CartItem;
    int contr=0;
    int countitem;
    RelativeLayout CartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_offer_detaills);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.detailspagewithcart);
        View view =getSupportActionBar().getCustomView();

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = pref.getString("user_id", "");
        prdname = pref.getString("name", "");
        prdid = pref.getString("pid", "");
        prdcrncy = pref.getString("crncy", "");
        prdprice = pref.getString("price", "");
        prdDescrp = pref.getString("Descr", "");
        prdimg = pref.getString("image", "");

        //Log.e("id",prdid);

        imageView=(ImageView)findViewById(R.id.image);
        textViewcrncy=(TextView)findViewById(R.id.crncytype);
        textViewprice=(TextView)findViewById(R.id.pricetext1);
        textViewDescrp=(TextView)findViewById(R.id.prdesc);
        IncreseBtn=(ImageButton)findViewById(R.id.add);
        DecreseBtn=(ImageButton)findViewById(R.id.remov);

        CartItem=(TextView)findViewById(R.id.cartcounter);

        Prdnametxt=(TextView)findViewById(R.id.textname);
        Prdnametxt.setText(prdname);

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CartBtn=(RelativeLayout)findViewById(R.id.CartRltv);
        CartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((contr+countitem)>0){
                    String actname="My Cart";
                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("Actvname",actname);
                    edit.apply();
                    Intent intent = new Intent(TopOfferDetaillsActivity.this, CheckOutActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TopOfferDetaillsActivity.this,"No Item Added",Toast.LENGTH_SHORT).show();
                }
            }
        });


        addtocart=(Button)findViewById(R.id.Addtocart);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        textViewcrncy.setText(prdcrncy);
        textViewprice.setText(prdprice);
        textViewDescrp.setText(prdDescrp);

        Glide.with(TopOfferDetaillsActivity.this)
                .load(prdimg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageView);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AddTocart();
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
        });

        backbtn=(Button)findViewById(R.id.backtbn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TopOfferDetaillsActivity.this, MainActivity.class));
            }
        });

        getCount();
    }

    private void getCount() {

        String url="http://memorstoreonline.com/webservices/count_cart?user_id="+userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        countitem= Integer.parseInt(response);
                         CartItem.setText(String.valueOf(countitem));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAg",error.getMessage());
                    }
                });

        RequestQueue mRequestQueue2 = Volley.newRequestQueue(this);
        mRequestQueue2.add(stringRequest);
    }


    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);

    }
    public void decreaseInteger(View view) {
            minteger = minteger - 1;
            display(minteger);
    }

    private void display(int number) {
       TextView displayInteger = (TextView) findViewById(R.id.editqnty);
        displayInteger.setText("" + number);
    }

    private void AddTocart() {

        String url="http://memorstoreonline.com/webservices/add_to_cart";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success= obj.getString("s");
                            String error= obj.getString("e");
                            String msg=obj.getString("m");

                         //   Log.e("resp",msg);

                            if (success.equalsIgnoreCase("1"))
                            {
                                Toast.makeText(TopOfferDetaillsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                getCount();
                            }
                            else
                            {
                                Toast.makeText(TopOfferDetaillsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TopOfferDetaillsActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TopOfferDetaillsActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("id", prdid);
                params.put("quantity", String.valueOf(minteger));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(TopOfferDetaillsActivity.this);
        queue.add(stringRequest);
    }
}
