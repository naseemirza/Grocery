package solutions.thinkbiz.grocery.Tabs.BstsellerPkg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import solutions.thinkbiz.grocery.R;
import solutions.thinkbiz.grocery.RecyclerViewItemClickListener;
import solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg.DealsAdapter;
import solutions.thinkbiz.grocery.Tabs.DealsoftheDayPkg.DealsModel;
import solutions.thinkbiz.grocery.Tabs.TabDetailsActivity;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOfferDetaillsActivity;

/**
 * Created by User on 22-Feb-19.
 */

public class BestSlrAdapter extends RecyclerView.Adapter<BestSlrAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<BestSlrModel> productList;

    public BestSlrAdapter(Context mCtx, List<BestSlrModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public BestSlrAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.topoffers, null);
        return new BestSlrAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BestSlrAdapter.ProductViewHolder holder, int position) {

        BestSlrModel product = productList.get(position);

        holder.textViewTitle.setText(product.getmName());
        holder.textViewoff.setText(product.getOfftext());
        holder.textViewpricetype.setText(product.getmCurrency());
        holder.textViewPrice.setText(product.getmPrice());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getmImageUrl()));

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), TabDetailsActivity.class);
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
