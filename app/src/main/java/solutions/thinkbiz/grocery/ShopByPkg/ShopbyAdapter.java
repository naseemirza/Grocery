package solutions.thinkbiz.grocery.ShopByPkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import solutions.thinkbiz.grocery.R;
import solutions.thinkbiz.grocery.RecyclerViewItemClickListener;
import solutions.thinkbiz.grocery.TopOffersPkg.TopOfferDetaillsActivity;

/**
 * Created by User on 12-Feb-19.
 */

public class ShopbyAdapter extends RecyclerView.Adapter<ShopbyAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<ShopbyModel> productList;

    public ShopbyAdapter(Context mCtx, List<ShopbyModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ShopbyAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.shopbycat, null);
        return new ShopbyAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopbyAdapter.ProductViewHolder holder, final int position) {

         final ShopbyModel product = productList.get(position);

         String name=product.getmName();

        holder.textViewTitle1.setText(name);


        Glide.with(mCtx)
                    .load(product.getmImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(holder.imageView);

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                String prodID=product.getId();
                String prdname=product.getmName();

                SharedPreferences pref = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("pid",prodID);
                edit.putString("pname",prdname);
                edit.apply();
                Intent intent = new Intent(view.getContext(), ShopbyDetailsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle1;
       ImageView imageView;
       FrameLayout frameLayout;

        private RecyclerViewItemClickListener itemClickListener;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle1 = itemView.findViewById(R.id.nameText);
            imageView = itemView.findViewById(R.id.shpbyimg);
            frameLayout =itemView.findViewById(R.id.imgcard);

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
