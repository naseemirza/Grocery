package solutions.thinkbiz.grocery.Checkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import solutions.thinkbiz.grocery.MainActivity;
import solutions.thinkbiz.grocery.R;

public class StoreRespActivity extends AppCompatActivity {

    Button button;
    String OrderoId;
    TextView txtid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_resp);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        OrderoId = pref.getString("OrderId", "");

        txtid=(TextView)findViewById(R.id.txtid);
        txtid.setText(OrderoId);
        button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreRespActivity.this,MainActivity.class));
            }
        });
    }
}
