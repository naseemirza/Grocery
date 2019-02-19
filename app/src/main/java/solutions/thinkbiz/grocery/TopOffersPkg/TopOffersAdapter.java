package solutions.thinkbiz.grocery.TopOffersPkg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import solutions.thinkbiz.grocery.R;

/**
 * Created by User on 12-Feb-19.
 */

public class TopOffersAdapter extends RecyclerView.Adapter<TopOffersAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<TopOffersModel> productList;

    public TopOffersAdapter(Context mCtx, List<TopOffersModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.topoffers, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        TopOffersModel product = productList.get(position);

        holder.textViewTitle.setText(product.getmName());
        holder.textViewoff.setText(product.getOfftext());
        holder.textViewpricetype.setText(product.getmCurrency());
        holder.textViewPrice.setText(product.getmPrice());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getmImageUrl()));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewoff, textViewpricetype, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.nameTextview);
            textViewoff = itemView.findViewById(R.id.textpercent);
            textViewpricetype = itemView.findViewById(R.id.crncytype);
            textViewPrice = itemView.findViewById(R.id.pricetext1);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }

}
