package solutions.thinkbiz.grocery.History;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Currency;
import java.util.List;

import solutions.thinkbiz.grocery.R;
import solutions.thinkbiz.grocery.ShopByPkg.ShopbyDetailsActivity;

/**
 * Created by User on 17-Apr-19.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<HistoryListModel> productList;

    public HistoryListAdapter(Context mCtx, List<HistoryListModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public HistoryListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.histrylayout, null);
        return new HistoryListAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryListAdapter.ProductViewHolder holder, final int position) {

        final HistoryListModel product = productList.get(position);

        final String id=product.getId();
        final String date=product.getDate();
        String symbol, price;

        SharedPreferences pref = mCtx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        symbol = pref.getString("crncy", "");
        price = pref.getString("Tprice", "");

     //   Log.e("crncy",symbol);
      //  Log.e("price",price);


      //  Currency currency = Currency.getInstance(product.getmCurrency());
       // final String symbol = currency.getSymbol();

        holder.itemtxt.setText(product.getItemnumber());
        holder.crncytxt.setText(symbol);
        holder.totalamttxt.setText(price);
        holder.datetxt.setText(product.getDate());
        holder.statustxt.setText(product.getStatus());

        holder.viwmoretxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = mCtx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("id", id);
                edit.putString("date", date);
                edit.apply();
                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder  {
        TextView itemtxt, crncytxt, totalamttxt,datetxt, statustxt, viwmoretxt;

        public ProductViewHolder(View itemView) {
            super(itemView);
            itemtxt = itemView.findViewById(R.id.indrel);
            crncytxt = itemView.findViewById(R.id.crmcy);
            totalamttxt = itemView.findViewById(R.id.fcsar1);
            datetxt = itemView.findViewById(R.id.admin);
            statustxt = itemView.findViewById(R.id.status);
            viwmoretxt = itemView.findViewById(R.id.compsite);
        }
    }

}
