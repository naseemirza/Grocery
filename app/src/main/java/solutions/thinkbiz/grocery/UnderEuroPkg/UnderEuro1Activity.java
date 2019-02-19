package solutions.thinkbiz.grocery.UnderEuroPkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import solutions.thinkbiz.grocery.R;

public class UnderEuro1Activity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_euro1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Under Euro € 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
