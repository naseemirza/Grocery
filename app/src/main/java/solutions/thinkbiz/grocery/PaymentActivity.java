package solutions.thinkbiz.grocery;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PaymentActivity extends AppCompatActivity {

    ImageView paypalbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paypalbtn=(ImageView)findViewById(R.id.imageView);
        paypalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentActivity.this, PaypalActivity.class);
                startActivity(intent);

            }
        });
    }
}
