package solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
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
import solutions.thinkbiz.grocery.RecyclerViewItemClickListener;
import solutions.thinkbiz.grocery.Tabs.TabDetailsActivity;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOfferDetaillsActivity;

/**
 * Created by User on 14-Feb-19.
 */

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<DealsModel> productList;

    public DealsAdapter(Context mCtx, List<DealsModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public DealsAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.topoffers, null);
        return new DealsAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DealsAdapter.ProductViewHolder holder, int position) {

        final DealsModel product = productList.get(position);

        String price=product.getmPrice();
        String price1 = price.substring(0, price.indexOf("."));
        Currency currency = Currency.getInstance(product.getmCurrency());
        final String symbol = currency.getSymbol();
        // Log.e("euro",symbol);

        holder.textViewTitle.setText(product.getmName());
        holder.textViewoff.setText(product.getOfftext());
        holder.textViewpricetype.setText(symbol);
        holder.textViewPrice.setText(" "+price1);
        //  holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getmImageUrl()));

        Glide.with(mCtx)
                .load(product.getmImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imageView);

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                String prodID=product.getId();
                String prodname=product.getmName();
                String prodprice=product.getmPrice();
                String descript = product.getDescr();
                String imageurl=product.getmImageUrl();

                SharedPreferences pref = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("image",imageurl);
                edit.putString("pid",prodID);
                edit.putString("name",prodname);
                edit.putString("crncy",symbol);
                edit.putString("price",prodprice);
                edit.putString("Descr",descript);
                edit.apply();
                Intent intent = new Intent(view.getContext(), TopOfferDetaillsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView textViewTitle, textViewoff, textViewpricetype, textViewPrice;
        ImageView imageView;

        private RecyclerViewItemClickListener itemClickListener;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.nameTextview);
            textViewoff = itemView.findViewById(R.id.textpercent);
            textViewpricetype = itemView.findViewById(R.id.crncytype);
            textViewPrice = itemView.findViewById(R.id.pricetext1);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onClick(v,getLayoutPosition());
        }

        public void setItemClickListener(RecyclerViewItemClickListener ic)
        {
            this.itemClickListener=ic;

        }
    }
}