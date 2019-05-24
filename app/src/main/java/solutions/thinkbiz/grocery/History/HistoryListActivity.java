package solutions.thinkbiz.grocery.History;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import solutions.thinkbiz.grocery.R;

public class HistoryListActivity extends AppCompatActivity {

    private HistoryListAdapter mExampleAdapter1;
    private ArrayList<HistoryListModel> mExampleList1;
    private RequestQueue mRequestQueue1;
    private RecyclerView mRecyclerview1;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        getSupportActionBar().setTitle("My Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = pref.getString("user_id", "");

        mExampleList1 = new ArrayList<>();
        mRequestQueue1 = Volley.newRequestQueue(this);
        mRecyclerview1=(RecyclerView)findViewById(R.id.myRecyclerID);
        mRecyclerview1.setNestedScrollingEnabled(false);
        mRecyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerview1.setHasFixedSize(true);

        parseJSON1();
    }

    //+userId
    private void parseJSON1() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        String url="http://demotbs.com/dev/grocery/webservices/getAllorder?user_id="+userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.e("rootJsonArray",response);
                        progressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONArray rootJsonArray = new JSONArray(response);
                            for (int i = 0; i < rootJsonArray.length(); i++) {
                                JSONObject object = rootJsonArray.getJSONObject(i);

                                mExampleList1.add(new HistoryListModel(object.optString("id"),
                                        object.optString("user_id"),
                                        object.optString("item_number"),
                                        object.optString("total_amount"),
                                        object.optString("created_date"),
                                        object.optString("status")));
                            }

                            Log.e("rootJsonArray",mExampleList1.size()+"");                  //deleteitem,updatemethod
                            mExampleAdapter1 = new HistoryListAdapter(HistoryListActivity.this, mExampleList1);
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
