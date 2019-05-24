package solutions.thinkbiz.grocery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class AboutUsActivity extends AppCompatActivity {

    WebView mywebview;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mywebview = (WebView) findViewById(R.id.webView1);
        mywebview.setWebViewClient(new MyWebViewClient());
        String url="http://demotbs.com/dev/grocery/webservices/about_us";
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mywebview.loadUrl(url);

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = new ProgressDialog(AboutUsActivity.this);
            progressDialog.setMessage("Please wait ...");
            progressDialog.setProgressStyle(90);
            progressDialog.show();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

}
