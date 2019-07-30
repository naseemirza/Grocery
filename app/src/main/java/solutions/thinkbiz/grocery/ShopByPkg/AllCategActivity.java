package solutions.thinkbiz.grocery.ShopByPkg;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import solutions.thinkbiz.grocery.R;

public class AllCategActivity extends AppCompatActivity {

    private ShopbyAdapter mExampleAdapterSB;
    private ArrayList<ShopbyModel> productListSB;
    private RequestQueue mRequestQueueSB;
    private RecyclerView recyclerViewSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categ);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Shop by categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productListSB = new ArrayList<>();
        mRequestQueueSB = Volley.newRequestQueue(this);
        recyclerViewSB = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerViewSB.setNestedScrollingEnabled(false);
        recyclerViewSB.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerViewSB.setHasFixedSize(true);

        parseJSON2();

    }

    private void parseJSON2() {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, AllURLs.SHOP_BY_ALL_CATEGORY,
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

                                mExampleAdapterSB = new ShopbyAdapter(AllCategActivity.this, productListSB);
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

}
