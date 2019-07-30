package solutions.thinkbiz.grocery.Tabs.UptoOffPkg;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
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

/**
 * Created by User on 22-Feb-19.
 */

public class UptoAdapter extends RecyclerView.Adapter<UptoAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<UptoModel> productList;

    public UptoAdapter(Context mCtx, List<UptoModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public UptoAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.topoffers, null);
        return new UptoAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UptoAdapter.ProductViewHolder holder, int position) {

        final UptoModel product = productList.get(position);

        Currency currency = Currency.getInstance(product.getmCurrency());
        final String symbol = currency.getSymbol();
        // Log.e("euro",symbol);

        holder.textViewTitle.setText(product.getmName());
        holder.textViewoff.setText(product.getOfftext());
        holder.textViewpricetype.setText(symbol);
        holder.textViewPrice.setText(" "+product.getmPrice());
        //  holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getmImageUrl()));

        Glide.with(mCtx)
                .load(product.getmImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imageView);

//        holder.setItemClickListener(new RecyclerViewItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                String prodname=product.getmName();
//                String prodprice=product.getmPrice();
//                String descript = product.getDescr();
//
//                SharedPreferences pref = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = pref.edit();
//                edit.putString("name",prodname);
//                edit.putString("crncy",symbol);
//                edit.putString("price",prodprice);
//                edit.putString("Descr",descript);
//                edit.apply();
//                Intent intent = new Intent(view.getContext(), TopOfferDetaillsActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewoff, textViewpricetype, textViewPrice;
        ImageView imageView;
       // private RecyclerViewItemClickListener itemClickListener;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.nameTextview);
            textViewoff = itemView.findViewById(R.id.textpercent);
            textViewpricetype = itemView.findViewById(R.id.crncytype);
            textViewPrice = itemView.findViewById(R.id.pricetext1);
            imageView = itemView.findViewById(R.id.imageview);
            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            this.itemClickListener.onClick(v,getLayoutPosition());
//        }
//
//        public void setItemClickListener(RecyclerViewItemClickListener ic)
//        {
//            this.itemClickListener=ic;
//
//        }
    }
}
