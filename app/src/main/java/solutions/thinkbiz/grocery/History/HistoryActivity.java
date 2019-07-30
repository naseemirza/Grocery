package solutions.thinkbiz.grocery.History;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

import solutions.thinkbiz.grocery.R;

public class HistoryActivity extends AppCompatActivity {


    private HistoryAdapter mExampleAdapter1;
    private ArrayList<HistoryModel> mExampleList1;
    private RequestQueue mRequestQueue1;
    private RecyclerView mRecyclerview1;

    String Id,Order_Date;
    TextView ordrtxt,datetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("Order items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Id = pref.getString("id", "");
        Order_Date = pref.getString("date", "");

        ordrtxt=(TextView)findViewById(R.id.orderid);
        datetxt=(TextView)findViewById(R.id.datetxt);

        ordrtxt.setText(Id);
        datetxt.setText(Order_Date);

        mExampleList1 = new ArrayList<>();
        mRequestQueue1 = Volley.newRequestQueue(this);
        mRecyclerview1=(RecyclerView)findViewById(R.id.myRecyclerID);
        mRecyclerview1.setNestedScrollingEnabled(false);
        mRecyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerview1.setHasFixedSize(true);

        parseJSON1();
    }

    //+Id

    private void parseJSON1() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        String url="https://demotbs.com/dev/grocery/webservices/getAllorder_item?order_id="+Id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         //Log.e("rootJsonArray",response);
                        progressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONObject rootJsonObject = new JSONObject(response);
                            JSONArray rootJsonArray = rootJsonObject.getJSONArray("order_item");
                            //JSONArray rootJsonArray = new JSONArray(response);
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                mExampleList1.add(new HistoryModel(object.optString("id"),
                                        object.optString("main_image"),
                                        object.optString("product_name"),
                                        object.optString("currency"),
                                        object.optString("product_special_price"),
                                        object.optString("qty")));
                            }

                            Log.e("rootJsonArray",mExampleList1.size()+"");                  //deleteitem,updatemethod
                            mExampleAdapter1 = new HistoryAdapter(HistoryActivity.this, mExampleList1);
                            mRecyclerview1.setAdapter(mExampleAdapter1);
                            mExampleAdapter1.notifyDataSetChanged();
                            mRecyclerview1.setHasFixedSize(true);

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
