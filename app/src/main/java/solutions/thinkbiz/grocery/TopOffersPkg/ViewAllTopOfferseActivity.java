package solutions.thinkbiz.grocery.TopOffersPkg;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

public class ViewAllTopOfferseActivity extends AppCompatActivity {

    private TopOffersAdapter mExampleAdapter1;
    private ArrayList<TopOffersModel> productList;
    private RequestQueue mRequestQueue1;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_top_offerse);

        getSupportActionBar().setTitle("Top offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productList = new ArrayList<>();
        mRequestQueue1 = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewtop);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        parseJSON1();

    }

    private void parseJSON1() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AllURLs.TOP_OFFERS,
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

                            mExampleAdapter1 = new TopOffersAdapter(ViewAllTopOfferseActivity.this, productList);
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
}
