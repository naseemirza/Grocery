package solutions.thinkbiz.grocery;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 08-Apr-19.
 */

public class TabAdapter  extends RecyclerView.Adapter<TabAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<TabModel> productList;

    public TabAdapter(Context mCtx, List<TabModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public TabAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tabname, null);
        return new TabAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabAdapter.ProductViewHolder holder, final int position) {

        final TabModel product = productList.get(position);

        String name=product.getmName();

        holder.textViewTitle1.setText(name);

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (position==0){


                }
                else if (position==1)
                {

                }
                else if (position==2)
                {

                }
                else if (position==3)
                {

                }

                else {
                    Intent intent = new Intent(mCtx, MainActivity.class);
                    mCtx.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

                                                            //implements View.OnClickListener
    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView textViewTitle1;

        private RecyclerViewItemClickListener itemClickListener;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle1 = itemView.findViewById(R.id.textViewname);

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
