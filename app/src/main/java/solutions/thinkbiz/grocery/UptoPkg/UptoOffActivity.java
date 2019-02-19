package solutions.thinkbiz.grocery.UptoPkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import solutions.thinkbiz.grocery.R;

public class UptoOffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upto_off);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Up to 50% Off");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
