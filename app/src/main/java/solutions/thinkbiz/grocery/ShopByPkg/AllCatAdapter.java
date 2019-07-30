package solutions.thinkbiz.grocery.ShopByPkg;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

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

/**
 * Created by User on 25-Feb-19.
 */

public class AllCatAdapter  extends RecyclerView.Adapter<AllCatAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<ShopbyModel> productList;

    public AllCatAdapter(Context mCtx, List<ShopbyModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public AllCatAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.shopbycatall, null);
        return new AllCatAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllCatAdapter.ProductViewHolder holder, int position) {

        ShopbyModel product = productList.get(position);

       // holder.textViewTitle1.setText(product.getName());
      //  holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImagepath()));
        holder.textViewTitle1.setText(product.getmName());
        Glide.with(mCtx)
                .load(product.getmImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imageView);

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {


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
            frameLayout = itemView.findViewById(R.id.imgcard);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onClick(v, getLayoutPosition());
        }

        public void setItemClickListener(RecyclerViewItemClickListener ic) {
            this.itemClickListener = ic;

        }
    }
}