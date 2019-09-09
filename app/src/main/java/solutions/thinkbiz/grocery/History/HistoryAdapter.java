package solutions.thinkbiz.grocery.History;

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
 * Created by User on 16-Apr-19.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<HistoryModel> productList;

    public HistoryAdapter(Context mCtx, List<HistoryModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public HistoryAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.historylayout, null);
        return new HistoryAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ProductViewHolder holder, final int position) {

        final HistoryModel product = productList.get(position);

        Currency currency = Currency.getInstance(product.getmCurrency());
        final String symbol = currency.getSymbol();

        holder.textViewname.setText(product.getmName());
        holder.textViewcurrncy1.setText(symbol);
        holder.textViewprice1.setText(product.getmPrice());
        holder.textViewQty1.setText(product.getQty());

        Glide.with(mCtx)
                .load(product.getmImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder  {
        TextView textViewname;
        TextView textViewcurrncy1,textViewprice1, textViewQty1;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.prodname);
            textViewcurrncy1 = itemView.findViewById(R.id.prdcrncy);
            textViewprice1 = itemView.findViewById(R.id.prdprice);
            textViewQty1 = itemView.findViewById(R.id.editqnty);
            imageView = itemView.findViewById(R.id.compid);
        }
    }
}
