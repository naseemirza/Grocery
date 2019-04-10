package solutions.thinkbiz.grocery.ShopByPkg;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

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

import solutions.thinkbiz.grocery.AllURLs;
import solutions.thinkbiz.grocery.MainActivity;
import solutions.thinkbiz.grocery.R;
import solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg.DealsAdapter;
import solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg.DealsModel;

public class ShopbyDetailsActivity extends AppCompatActivity {

    String prdid;

    private DealsAdapter mExampleAdapterDls;
    private ArrayList<DealsModel> productListDls;
    private RequestQueue mRequestQueueDls;
    private RecyclerView recyclerViewDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopby_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Product detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        prdid = pref.getString("pid", "");

        productListDls = new ArrayList<>();
        mRequestQueueDls = Volley.newRequestQueue(this);
        recyclerViewDeals = (RecyclerView) findViewById(R.id.recyclerviewdealsa);
        recyclerViewDeals.setNestedScrollingEnabled(false);
        recyclerViewDeals.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerViewDeals.setHasFixedSize(true);

        parseJSONDeals();
    }

    private void parseJSONDeals() {

        String url=" http://demotbs.com/dev/grocery/webservices/productBycategory?id="+prdid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                                        object.optString("top_offer"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price"),
                                        object.optString("product_description")));
                            }

                            Log.e("rootJsonArray",productListDls.size()+"");

                            mExampleAdapterDls = new DealsAdapter(ShopbyDetailsActivity.this, productListDls);
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
}
