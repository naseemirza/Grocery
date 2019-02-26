package solutions.thinkbiz.grocery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private EditText Etext;
    ImageButton profilebtn, homebtn;
    Button undereuro,uptooff,bestseller;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    //Top Offers
    //List<TopOffersModel> productList;
    //RecyclerView recyclerView;

    private TopOffersAdapter mExampleAdapter1;
    private ArrayList<TopOffersModel> productList;
    private RequestQueue mRequestQueue1;
    private RecyclerView recyclerView;


    //Shop by categories
    List<ShopbyModel> productList1;
    RecyclerView recyclerViewshp;
    //Dealas of the day
    List<DealsModel> productListDeals = new ArrayList<>();
    List<UndrModel> getProductLisUndr = new ArrayList<>();
    List<UptoModel> productListupto = new ArrayList<>();
    List<BestSlrModel> getProductLisbstslr = new ArrayList<>();
    RecyclerView recyclerViewDeals, recyclerViewUndr, recyclerViewupto, recyclerViewbstslr;

    Button textdls,textundr,textupto,textbstslr;

   // TabLayout tabLayouttab;
 //   ViewPager viewPagertab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       //
//        profilebtn=(ImageButton)findViewById(R.id.profile);
//        homebtn=(ImageButton)findViewById(R.id.home);
//        profilebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//        homebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        Etext=(EditText)findViewById(R.id.etext);
        Etext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditTextActivity.class);
                startActivity(intent);
            }
        });


        //Top Offers

        productList = new ArrayList<>();
        mRequestQueue1 = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);

        parseJSON1();

//        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
//        recyclerView.setHasFixedSize(true);
//        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        productList = new ArrayList<>();
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
//
//
//        TopOffersAdapter adapter = new TopOffersAdapter(this, productList);
//        recyclerView.setAdapter(adapter);

        //Shop by categories

        recyclerViewshp = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerViewshp.setNestedScrollingEnabled(false);
        recyclerViewshp.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewshp.setHasFixedSize(true);

        productList1 = new ArrayList<>();
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));

        ShopbyAdapter adapter1 = new ShopbyAdapter(this, productList1);
        recyclerViewshp.setAdapter(adapter1);

        //Deals of the day Tab

        textdls=(Button)findViewById(R.id.deals);
        textundr=(Button)findViewById(R.id.undereuro);
        textupto=(Button)findViewById(R.id.upto);
        textbstslr=(Button)findViewById(R.id.bestslr);

        //Deals of the day

        recyclerViewDeals = (RecyclerView) findViewById(R.id.recyclerviewdealsa);
        recyclerViewDeals.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        recyclerViewDeals.setHasFixedSize(true);

        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));

       final DealsAdapter adapterD = new DealsAdapter(this, productListDeals);
        recyclerViewDeals.setAdapter(adapterD);

        textdls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewUndr.setVisibility(View.GONE);
                recyclerViewupto.setVisibility(View.GONE);
                recyclerViewbstslr.setVisibility(View.GONE);
                recyclerViewDeals.setVisibility(View.VISIBLE);
                recyclerViewDeals.setAdapter(adapterD);
                textundr.setTextColor(getResources().getColor(R.color.colorBlack));
                textupto.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.colorBlack));
                textdls.setTextColor(getResources().getColor(R.color.maincolor));
            }
        });



        //Under Euro 1

        recyclerViewUndr = (RecyclerView) findViewById(R.id.recyclerviewundr);
        recyclerViewUndr.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        recyclerViewUndr.setHasFixedSize(true);

        getProductLisUndr.add(new UndrModel(R.drawable.img11, "20", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisUndr.add(new UndrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));

        final UndrAdapter adapterUndr = new UndrAdapter(this, getProductLisUndr);
         //recyclerViewUndr.setAdapter(adapterUndr);

        textundr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewDeals.setVisibility(View.GONE);
                recyclerViewupto.setVisibility(View.GONE);
                recyclerViewbstslr.setVisibility(View.GONE);
                recyclerViewUndr.setVisibility(View.VISIBLE);
                recyclerViewUndr.setAdapter(adapterUndr);
                textdls.setTextColor(getResources().getColor(R.color.colorBlack));
                textupto.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.colorBlack));
                textundr.setTextColor(getResources().getColor(R.color.maincolor));

            }
        });


        //Up to Off

        recyclerViewupto = (RecyclerView) findViewById(R.id.recyclerviewupto);
        recyclerViewupto.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        recyclerViewupto.setHasFixedSize(true);

        productListupto.add(new UptoModel(R.drawable.img11, "30", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListupto.add(new UptoModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));

        final UptoAdapter adapterUpto = new UptoAdapter(this, productListupto);
       // recyclerViewupto.setAdapter(adapterUpto);

        textupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewDeals.setVisibility(View.GONE);
                recyclerViewUndr.setVisibility(View.GONE);
                recyclerViewbstslr.setVisibility(View.GONE);
                recyclerViewupto.setVisibility(View.VISIBLE);
                recyclerViewupto.setAdapter(adapterUpto);
                textdls.setTextColor(getResources().getColor(R.color.colorBlack));
                textundr.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.colorBlack));
                textupto.setTextColor(getResources().getColor(R.color.maincolor));

            }
        });


        //Best Seller

        recyclerViewbstslr = (RecyclerView) findViewById(R.id.recyclerviewbstslr);
        recyclerViewbstslr.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        recyclerViewbstslr.setHasFixedSize(true);

        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "40", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        getProductLisbstslr.add(new BestSlrModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));

        final BestSlrAdapter adapterBest = new BestSlrAdapter(this, getProductLisbstslr);

        textbstslr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewDeals.setVisibility(View.GONE);
                recyclerViewUndr.setVisibility(View.GONE);
                recyclerViewupto.setVisibility(View.GONE);
                recyclerViewbstslr.setVisibility(View.VISIBLE);
                recyclerViewbstslr.setAdapter(adapterBest);
                textdls.setTextColor(getResources().getColor(R.color.colorBlack));
                textundr.setTextColor(getResources().getColor(R.color.colorBlack));
                textupto.setTextColor(getResources().getColor(R.color.colorBlack));
                textbstslr.setTextColor(getResources().getColor(R.color.maincolor));

            }
        });



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        HomeSliderAdapter myCustomPagerAdapter = new HomeSliderAdapter(this);
        viewPager.setAdapter(myCustomPagerAdapter);

        dotscount = myCustomPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 3000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()==0){
                        viewPager.setCurrentItem(1);
                    }
                    else if(viewPager.getCurrentItem()==1){
                        viewPager.setCurrentItem(2);
                    }
                    else if(viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    }
                    else if(viewPager.getCurrentItem()==3){
                        viewPager.setCurrentItem(4);
                    }
                    else if(viewPager.getCurrentItem()==4){
                        viewPager.setCurrentItem(5);
                    }
                    else if(viewPager.getCurrentItem()==5){
                        viewPager.setCurrentItem(6);
                    }
                    else if(viewPager.getCurrentItem()==6){
                        viewPager.setCurrentItem(7);
                    }
                    else {
                        viewPager.setCurrentItem(0);
                    }


                }
            });
        }
    }


    private void parseJSON1() {

        //final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
       // progressBar.setVisibility(View.VISIBLE);
         String topoffersURL="http://demotbs.com/dev/grocery/webservices/getAllProduct";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,topoffersURL,
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

                                productList.add(new TopOffersModel(object.optString("id"),
                                        object.optString("main_image"),
                                        object.optString("top_offer"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price")));

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

           // startActivity(new Intent(MainActivity.this,LoginPageActivity.class));
            // Handle the camera action
        }
        else if (id == R.id.nav_aboutus) {

//            String actname="About Us";
//            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor edit = pref.edit();
//            edit.putString("Aboutus",actname);
//            edit.apply();
//            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
//            startActivity(intent);

        } else if (id == R.id.nav_history) {
//            String actname="History";
//            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//            SharedPreferences.Editor edit = pref.edit();
//            edit.putString("Actvname",actname);
//            edit.apply();
//            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
//            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
