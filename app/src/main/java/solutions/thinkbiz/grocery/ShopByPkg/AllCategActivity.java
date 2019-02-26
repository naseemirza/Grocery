package solutions.thinkbiz.grocery.ShopByPkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import solutions.thinkbiz.grocery.R;

public class AllCategActivity extends AppCompatActivity {

    List<ShopbyModel> productList1;
    RecyclerView recyclerViewshp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categ);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Shop by categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerViewshp = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerViewshp.setNestedScrollingEnabled(false);
        recyclerViewshp.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerViewshp.setHasFixedSize(true);

        productList1 = new ArrayList<>();
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));
        productList1.add(new ShopbyModel(R.drawable.img11,"Bread & Cackes"));

        AllCatAdapter adapter1 = new AllCatAdapter(this, productList1);
        recyclerViewshp.setAdapter(adapter1);

    }
}
