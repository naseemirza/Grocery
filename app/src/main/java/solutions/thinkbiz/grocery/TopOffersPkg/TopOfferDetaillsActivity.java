package solutions.thinkbiz.grocery.TopOffersPkg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import solutions.thinkbiz.grocery.Checkout.CheckOutActivity;
import solutions.thinkbiz.grocery.MainActivity;
import solutions.thinkbiz.grocery.R;

public class TopOfferDetaillsActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    ImageButton IncreseBtn,DecreseBtn;
    int minteger=1;

    ImageView imageView;
    TextView textViewcrncy, textViewprice, textViewDescrp;
    Button addtocart;
    Vibrator vibrator;
    //ProgressDialog progressDialog;
    String prdname,prdid,prdcrncy,prdprice,prdDescrp, prdimg, userId;

//    private int[] myImageList = new int[]{R.drawable.img11, R.drawable.img11,
//            R.drawable.img11,R.drawable.img11
//            ,R.drawable.img11,R.drawable.img11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_offer_detaills);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        addtocart=(Button)findViewById(R.id.Addtocart);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        getSupportActionBar().setTitle(prdname);
        textViewcrncy.setText(prdcrncy);
        textViewprice.setText(prdprice);
        textViewDescrp.setText(prdDescrp);

        Glide.with(TopOfferDetaillsActivity.this)
                .load(prdimg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imageView);

       // imageModelArrayList = new ArrayList<>();
      //  imageModelArrayList = populateList();

      //  init();

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(TopOfferDetaillsActivity.this, CheckOutActivity.class));
//                if(v.getId() == prdid) {
//                    addtocart.setText("Applied");
//                    addtocart.setBackgroundResource(R.drawable.aftrapplybtn);
//                }
                    AddTocart();
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
        });
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

        String url="http://demotbs.com/dev/grocery/webservices/add_to_cart";
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

//    private ArrayList<ImageModel> populateList(){
//
//        ArrayList<ImageModel> list = new ArrayList<>();
//
//        for(int i = 0; i < 6; i++){
//            ImageModel imageModel = new ImageModel();
//            imageModel.setImage_drawable(myImageList[i]);
//            list.add(imageModel);
//        }
//
//        return list;
//    }

//    private void init() {
//
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setAdapter(new SlidingImage_Adapter(TopOfferDetaillsActivity.this,imageModelArrayList));
//
//        CirclePageIndicator indicator = (CirclePageIndicator)
//                findViewById(R.id.indicator);
//
//        indicator.setViewPager(mPager);
//
//        final float density = getResources().getDisplayMetrics().density;
//
////Set circle indicator radius
//        indicator.setRadius(5 * density);
//
//        NUM_PAGES =imageModelArrayList.size();
//
//        // Auto start of viewpager
////        final Handler handler = new Handler();
////        final Runnable Update = new Runnable() {
////            public void run() {
////                if (currentPage == NUM_PAGES) {
////                    currentPage = 0;
////                }
////                mPager.setCurrentItem(currentPage++, true);
////            }
////        };
////        Timer swipeTimer = new Timer();
////        swipeTimer.schedule(new TimerTask() {
////            @Override
////            public void run() {
////                handler.post(Update);
////            }
////        }, 3000, 3000);
//
//        // Pager listener over indicator
//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//
//            }
//
//            @Override
//            public void onPageScrolled(int pos, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int pos) {
//
//            }
//        });
//
//    }
}
