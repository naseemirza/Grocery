package solutions.thinkbiz.grocery.BestsellerPkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import solutions.thinkbiz.grocery.R;

public class BestsellerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestseller);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Bestseller");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
