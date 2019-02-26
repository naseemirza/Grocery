package solutions.thinkbiz.grocery.ShopByPkg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import solutions.thinkbiz.grocery.R;
import solutions.thinkbiz.grocery.RecyclerViewItemClickListener;

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
    public void onBindViewHolder(ShopbyAdapter.ProductViewHolder holder, int position) {

        ShopbyModel product = productList.get(position);

        holder.textViewTitle1.setText(product.getName());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImagepath()));

        if (position==0){
            holder.frameLayout.setBackgroundResource(R.drawable.shpbycard);
            holder.imageView.setImageResource(R.drawable.caticon);
            holder.textViewTitle1.setTextColor(Color.parseColor("#37b5e8"));
            holder.textViewTitle1.setText("All Categories");
            holder.textViewTitle1.setTextSize(13);
            holder.setItemClickListener(new RecyclerViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), AllCategActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
        else {
            holder.setItemClickListener(new RecyclerViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), ShopbyDetailsActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

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
