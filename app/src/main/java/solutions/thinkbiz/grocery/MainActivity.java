package solutions.thinkbiz.grocery;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import solutions.thinkbiz.grocery.BestsellerPkg.BestsellerActivity;
import solutions.thinkbiz.grocery.DealsoftheDayPkg.DealsAdapter;
import solutions.thinkbiz.grocery.DealsoftheDayPkg.DealsModel;
import solutions.thinkbiz.grocery.ShopByPkg.ShopbyAdapter;
import solutions.thinkbiz.grocery.ShopByPkg.ShopbyModel;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOffersAdapter;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOffersModel;
import solutions.thinkbiz.grocery.UnderEuroPkg.UnderEuro1Activity;
import solutions.thinkbiz.grocery.UptoPkg.UptoOffActivity;

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
    List<TopOffersModel> productList;
    RecyclerView recyclerView;
    //Shop by categories
    List<ShopbyModel> productList1;
    RecyclerView recyclerViewshp;
    //Dealas of the day
    List<DealsModel> productListDeals;
    RecyclerView recyclerViewDeals;


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
        profilebtn=(ImageButton)findViewById(R.id.profile);
        homebtn=(ImageButton)findViewById(R.id.home);
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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


        undereuro=(Button)findViewById(R.id.undereuro);
        uptooff=(Button)findViewById(R.id.upto);
        bestseller=(Button)findViewById(R.id.bestslr);

        undereuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnderEuro1Activity.class);
                startActivity(intent);
            }
        });

        uptooff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UptoOffActivity.class);
                startActivity(intent);
            }
        });

        bestseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BestsellerActivity.class);
                startActivity(intent);
            }
        });



        //Top Offers
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productList.add(new TopOffersModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));


        TopOffersAdapter adapter = new TopOffersAdapter(this, productList);
        recyclerView.setAdapter(adapter);

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

        //Deals of the day

        recyclerViewDeals = (RecyclerView) findViewById(R.id.recyclerviewdealsa);
        recyclerViewDeals.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        recyclerViewDeals.setHasFixedSize(true);

        productListDeals = new ArrayList<>();
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));
        productListDeals.add(new DealsModel(R.drawable.img11, "10", "Bread & Cackes","€","90.00"));


        DealsAdapter adapterD = new DealsAdapter(this, productListDeals);
        recyclerViewDeals.setAdapter(adapterD);


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

//    private void setupViewPager1(ViewPager viewPager1) {
//        ViewPagerAdapter adapter1 = new ViewPagerAdapter(getSupportFragmentManager());
//
//        adapter1.addFragment(new DealsFrag(), "Deals of the day");
//        adapter1.addFragment(new UnderEuro1Frag(), "Under Euro 1");
//        adapter1.addFragment(new UpToFrag(), "Up to 50% Off");
//        adapter1.addFragment(new BestSellerFrag(), "Bestseller");
//
//        viewPager1.setAdapter(adapter1);
//    }
//
//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }



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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
