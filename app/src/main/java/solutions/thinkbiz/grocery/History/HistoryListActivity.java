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
import android.widget.Toast;

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
        String url="https://demotbs.com/dev/grocery/webservices/getAllorder?user_id="+userId;

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

    public void AddToMethod(String id) {

        String url="https://demotbs.com/dev/grocery/webservices/reorder?id="+id+"&user_id="+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.e("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success= obj.getString("s");
                            String error= obj.getString("e");
                            String msg=obj.getString("m");

                            Log.e("cid",msg);

                            if (success.equalsIgnoreCase("1"))
                            {
                                Toast.makeText(HistoryListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(HistoryListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                            mExampleList1.clear();
                            parseJSON1();
                            mExampleAdapter1.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HistoryListActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(CheckOutActivity.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue2 = Volley.newRequestQueue(HistoryListActivity.this);
        queue2.add(stringRequest);
    }
}
