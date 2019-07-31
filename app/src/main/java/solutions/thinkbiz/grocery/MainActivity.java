package solutions.thinkbiz.grocery;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import solutions.thinkbiz.grocery.Checkout.CheckOutActivity;
import solutions.thinkbiz.grocery.History.HistoryListActivity;
import solutions.thinkbiz.grocery.ShopByPkg.AllCategActivity;
import solutions.thinkbiz.grocery.Tabs.BstsellerPkg.BestSlrAdapter;
import solutions.thinkbiz.grocery.Tabs.BstsellerPkg.BestSlrModel;
import solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg.DealsAdapter;
import solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg.DealsModel;
import solutions.thinkbiz.grocery.ShopByPkg.ShopbyAdapter;
import solutions.thinkbiz.grocery.ShopByPkg.ShopbyModel;
import solutions.thinkbiz.grocery.Tabs.UnderEuroPkg.UndrAdapter;
import solutions.thinkbiz.grocery.Tabs.UnderEuroPkg.UndrModel;
import solutions.thinkbiz.grocery.Tabs.UptoOffPkg.UptoAdapter;
import solutions.thinkbiz.grocery.Tabs.UptoOffPkg.UptoModel;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOffersAdapter;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOffersModel;
import solutions.thinkbiz.grocery.TopOffersPkg.ViewAllTopOfferseActivity;

import static solutions.thinkbiz.grocery.LoginActivity.booltype;
import static solutions.thinkbiz.grocery.LoginActivity.ads;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private EditText Etext;
    ImageButton profilebtn, homebtn;
    Button undereuro,uptooff,bestseller;
    private static ProgressDialog mProgressDialog;

    private TopOffersAdapter mExampleAdapter1;
    private ArrayList<TopOffersModel> productList;
    private RequestQueue mRequestQueue1;
    private RecyclerView recyclerView;


    private ShopbyAdapter mExampleAdapterSB;
    private ArrayList<ShopbyModel> productListSB;
    private RequestQueue mRequestQueueSB;
    private RecyclerView recyclerViewSB;

    //Dealas of the day
    private DealsAdapter mExampleAdapterDls;
    private ArrayList<DealsModel> productListDls;
    private RequestQueue mRequestQueueDls;
    private RecyclerView recyclerViewDeals;


//    // Under Euro 1
      private UndrAdapter mExampleAdapterUndr;
     private ArrayList<UndrModel> productListUndr;
    private RequestQueue mRequestQueueUndr;
    private RecyclerView recyclerViewUndr;
//
//    // Upto
     private UptoAdapter mExampleAdapterUpto;
     private ArrayList<UptoModel> productListUpto;
    private RequestQueue mRequestQueueUpto;
    private RecyclerView recyclerViewupto;
//
//    // Upto
      private BestSlrAdapter mExampleAdapterBst;
      private ArrayList<BestSlrModel> productListBst;
    private RequestQueue mRequestQueueBst;
    private RecyclerView recyclerViewbstslr;


    Button textdls,textundr,textupto,textbstslr;
    String uemail;
    TextView textViewname;
    RelativeLayout CartBtn;

    TextView textvall;

    Dialog dialog;
    String userId;
    TextView CartItem;
    int contr=0;
    int countitem;
    ImageButton allcate;
    ImageView banner;
    ImageView advrtise;

    public static String token;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        booltype=pref.getBoolean("Booltype", Boolean.parseBoolean(""));
        ads=pref.getBoolean("Booltype1", Boolean.parseBoolean(""));
        uemail = pref.getString("Myemail", "");
        userId = pref.getString("user_id", "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "channel_id";
            String channelName = "notificationName";
            String channelDescription = "notificationDescription";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(id, channelName, importance);
//            notificationChannel.setDescription(channelDescription);
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
           // builder.setChannelId(id);
        }


        token =  FirebaseInstanceId.getInstance().getToken();
           Log.d("token", token);

        //Advertisement
        //ads=false;

           if (ads) {
               ImageView advrtise = (ImageView) findViewById(R.id.adsimg);
               openDailyInventoryBottomSheet();
           }

       //   Log.e("ads", String.valueOf(ads));

       // DealsTabName();

         banner=(ImageView)findViewById(R.id.imageView21);
        mainImage();

        allcate=(ImageButton)findViewById(R.id.allcateg);
        allcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllCategActivity.class);
                    startActivity(intent);
            }
        });

        CartItem=(TextView) findViewById(R.id.cartcounter);
        getCount();

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
                    Intent intent = new Intent(MainActivity.this, CheckOutActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"No Item Added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Etext=(EditText)findViewById(R.id.etext);
        Etext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditTextActivity.class);
                startActivity(intent);
            }
        });

        textvall=(TextView)findViewById(R.id.textVall);
        textvall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllTopOfferseActivity.class);
                startActivity(intent);
            }
        });

        //Top Offers

        productList = new ArrayList<>();
        mRequestQueue1 = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        parseJSON1();

        //Shop by categories

        productListSB = new ArrayList<>();
        mRequestQueueSB = Volley.newRequestQueue(this);
        recyclerViewSB = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerViewSB.setNestedScrollingEnabled(false);
        recyclerViewSB.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewSB.setHasFixedSize(true);

        parseJSON2();


        //Deals of the day Tab

        textdls=(Button)findViewById(R.id.deals);
        textundr=(Button)findViewById(R.id.undereuro);
      // textupto=(Button)findViewById(R.id.upto);
        textbstslr=(Button)findViewById(R.id.bestslr);

        //Deals of the day

        productListDls = new ArrayList<>();
        mRequestQueueDls = Volley.newRequestQueue(this);
        recyclerViewDeals = (RecyclerView) findViewById(R.id.recyclerviewdealsa);
        recyclerViewDeals.setNestedScrollingEnabled(false);
        recyclerViewDeals.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerViewDeals.setHasFixedSize(true);

        parseJSONDeals();

        textdls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSONDeals();
                textundr.setTextColor(getResources().getColor(R.color.colorBlack));
               // textupto.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.colorBlack));
                textdls.setTextColor(getResources().getColor(R.color.maincolor));
            }
        });

        //Under Euro 1

        textundr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSONUndr();
                textdls.setTextColor(getResources().getColor(R.color.colorBlack));
                //textupto.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.colorBlack));
                textundr.setTextColor(getResources().getColor(R.color.maincolor));

            }
        });


        //Up to Off

//        textupto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                parseJSONUpto();
//                textdls.setTextColor(getResources().getColor(R.color.colorBlack));
//                textundr.setTextColor(getResources().getColor(R.color.colorBlack));
//                textbstslr.setTextColor(getResources().getColor(R.color.colorBlack));
//                textupto.setTextColor(getResources().getColor(R.color.maincolor));
//
//            }
//        });


        //Best Seller

        textbstslr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSONBstslr();
                textdls.setTextColor(getResources().getColor(R.color.colorBlack));
                textundr.setTextColor(getResources().getColor(R.color.colorBlack));
               // textupto.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.maincolor));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        textViewname= (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewmail);
        textViewname.setText(uemail);

    }

//    private void DealsTabName() {
//
//            String url="http://demotbs.com/dev/grocery/webservices/deals_tab";
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONArray rootJsonArray = new JSONArray(response);
//
//                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");
//
//                            for (int i = 0; i < rootJsonArray.length(); i++) {
//                                JSONObject obj = rootJsonArray.getJSONObject(i);
//
//                                String delas=obj.getString("first_field");
//                                String underEuro=obj.getString("second_field");
//                                String upto=obj.getString("third_field");
//                                String bestsller=obj.getString("fourth_field");
//                                // Log.e("dls", delas);
//
//                                textdls.setText(delas);
//                                textundr.setText(underEuro);
//                                //textupto.setText(upto);
//                                textbstslr.setText(bestsller);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        Log.e("TAg",error.getMessage());
//                    }
//                });
//
//              RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
//              queue2.add(stringRequest);
//        }

    private void openDailyInventoryBottomSheet() {

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.popup, null);
        ImageView cancel = view.findViewById(R.id.cancel);
                ImageView image = view.findViewById(R.id.adsimg);
                final Dialog mBottomSheetDialog = new Dialog(MainActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      //  mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        AdvertiseImage(image);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads=false;
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putBoolean("Booltype1",ads);
                edit.apply();
               // startActivity(new Intent(MainActivity.this,MainActivity.class));
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.show();

        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mBottomSheetDialog.isShowing()) {
                    mBottomSheetDialog.dismiss();
                }
            }
        };

        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 6000);

    }


    private void AdvertiseImage(final ImageView image) {

        //showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        String url="https://demotbs.com/dev/grocery/webservices/advertise_image";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          //Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg=obj.getString("advertise_image");
                            String path="https://demotbs.com/dev/grocery/assets/uploads/advertise_image/"+msg;
                            Log.e("Response", path);

                            Glide.with(MainActivity.this)
                                    .load(path)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .fitCenter()
                                    .into(image);

                            //removeSimpleProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(CheckOutActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
        queue2.add(stringRequest);
    }

    private void mainImage() {

        String url="https://demotbs.com/dev/grocery/webservices/main_image";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                   //      Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg=obj.getString("main_image");
                            String path="https://demotbs.com/dev/grocery/assets/uploads/slider/"+msg;
                        //    Log.e("Response", path);

                            Glide.with(MainActivity.this)
                                    .load(path)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .fitCenter()
                                    .into(banner);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(CheckOutActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
        queue2.add(stringRequest);
    }

    private void getCount() {

        String url="https://demotbs.com/dev/grocery/webservices/count_cart?user_id="+userId;

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

    private void parseJSON1() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,AllURLs.TOP_OFFERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            Log.e("rootJsonArray",response);
                            JSONArray rootJsonArray = new JSONArray(response);

                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");

                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);
//                                int count = rootJsonArray.length();
//                                Log.e("count", String.valueOf(count));

                                productList.add(new TopOffersModel(object.optString("id"),
                                        object.optString("main_image"),
                                        object.optString("offer_percent"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price"),
                                        object.optString("product_description")));

                            }

                            Log.e("rootJsonArray",productList.size()+"");

                            mExampleAdapter1 = new TopOffersAdapter(MainActivity.this, productList);
                            recyclerView.setAdapter(mExampleAdapter1);
                            mExampleAdapter1.notifyDataSetChanged();
                            recyclerView.setHasFixedSize(true);

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

    private void parseJSON2() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,AllURLs.SHOP_BY_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // progressBar.setVisibility(View.INVISIBLE);

                        try {
                            Log.e("rootJsonArray",response);
                            JSONArray rootJsonArray = new JSONArray(response);

                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");

                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                productListSB.add(new ShopbyModel(object.optString("id"),
                                        object.optString("category_image"),
                                        object.optString("category_name")));
                            }
                            Log.e("rootJsonArray",productListSB.size()+"");

                            mExampleAdapterSB = new ShopbyAdapter(MainActivity.this, productListSB);
                            recyclerViewSB.setAdapter(mExampleAdapterSB);
                            mExampleAdapterSB.notifyDataSetChanged();
                            recyclerViewSB.setHasFixedSize(true);

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

        mRequestQueueSB = Volley.newRequestQueue(this);
        mRequestQueueSB.add(stringRequest);
    }

    private void parseJSONDeals() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,AllURLs.DEALS_OF_DAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // progressBar.setVisibility(View.INVISIBLE);

                        try {
                            Log.e("rootJsonArray",response);
                            JSONArray rootJsonArray = new JSONArray(response);

                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");

                            productListDls.clear();
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                productListDls.add(new DealsModel(object.optString("id"),
                                        object.optString("main_image"),
                                        object.optString("offer_percent"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price"),
                                        object.optString("product_description")));
                            }

                            Log.e("rootJsonArray",productListDls.size()+"");

                            mExampleAdapterDls = new DealsAdapter(MainActivity.this, productListDls);
                            recyclerViewDeals.setAdapter(mExampleAdapterDls);
                            mExampleAdapterDls.notifyDataSetChanged();
                            recyclerViewDeals.setHasFixedSize(true);

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

        mRequestQueueDls = Volley.newRequestQueue(this);
        mRequestQueueDls.add(stringRequest);
    }

    private void parseJSONUndr() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,AllURLs.UNDER_EURO1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // progressBar.setVisibility(View.INVISIBLE);

                        try {
                            Log.e("rootJsonArray",response);
                            JSONArray rootJsonArray = new JSONArray(response);

                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");
                            productListDls.clear();
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                productListDls.add(new DealsModel(object.optString("id"),
                                        object.optString("main_image"),
                                        object.optString("offer_percent"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price"),
                                        object.optString("product_description")));
                            }

                            Log.e("rootJsonArray",productListDls.size()+"");

                            mExampleAdapterDls = new DealsAdapter(MainActivity.this, productListDls);
                            recyclerViewDeals.setAdapter(mExampleAdapterDls);
                            mExampleAdapterDls.notifyDataSetChanged();
                            recyclerViewDeals.setHasFixedSize(true);

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

        mRequestQueueDls = Volley.newRequestQueue(this);
        mRequestQueueDls.add(stringRequest);

    }

//    private void parseJSONUpto() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,AllURLs.UPTO_OFF,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        // progressBar.setVisibility(View.INVISIBLE);
//
//                        try {
//                            Log.e("rootJsonArray",response);
//                            JSONArray rootJsonArray = new JSONArray(response);
//
//                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");
//                            productListDls.clear();
//                            for (int i = 0; i < rootJsonArray.length(); i++) {
//                                JSONObject object = rootJsonArray.getJSONObject(i);
//
//                                productListDls.add(new DealsModel(object.optString("id"),
//                                        object.optString("main_image"),
//                                        object.optString("offer_percent"),
//                                        object.optString("product_name"),
//                                        object.optString("currency"),
//                                        object.optString("product_special_price"),
//                                        object.optString("product_description")));
//                            }
//
//                            Log.e("rootJsonArray",productListDls.size()+"");
//
//                            mExampleAdapterDls = new DealsAdapter(MainActivity.this, productListDls);
//                            recyclerViewDeals.setAdapter(mExampleAdapterDls);
//                            mExampleAdapterDls.notifyDataSetChanged();
//                            recyclerViewDeals.setHasFixedSize(true);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("TAg",error.getMessage());
//                    }
//                });
//
//        mRequestQueueDls = Volley.newRequestQueue(this);
//        mRequestQueueDls.add(stringRequest);
//
//    }


    private void parseJSONBstslr() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,AllURLs.BEST_SELLER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // progressBar.setVisibility(View.INVISIBLE);

                        try {
                            Log.e("rootJsonArray",response);
                            JSONArray rootJsonArray = new JSONArray(response);

                            Log.e("rootJsonArrayLength",rootJsonArray.length()+"");
                            productListDls.clear();
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                productListDls.add(new DealsModel(object.optString("id"),
                                        object.optString("main_image"),
                                        object.optString("offer_percent"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price"),
                                        object.optString("product_description")));
                            }

                            Log.e("rootJsonArray",productListDls.size()+"");

                            mExampleAdapterDls = new DealsAdapter(MainActivity.this, productListDls);
                            recyclerViewDeals.setAdapter(mExampleAdapterDls);
                            mExampleAdapterDls.notifyDataSetChanged();
                            recyclerViewDeals.setHasFixedSize(true);

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

        mRequestQueueDls = Volley.newRequestQueue(this);
        mRequestQueueDls.add(stringRequest);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {

            booltype=false;
            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("Booltype",booltype);
            edit.apply();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        else if (id == R.id.nav_aboutus) {

            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_history) {

            Intent intent = new Intent(MainActivity.this, HistoryListActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_contact) {

            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_cart) {
            Intent intent = new Intent(MainActivity.this, CheckOutActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
