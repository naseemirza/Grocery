package solutions.thinkbiz.grocery.Checkout;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import solutions.thinkbiz.grocery.R;


/**
 * Created by User on 29-Mar-19.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<OrderModel> productList;
    private TextView pricetxt;

    public OrderAdapter(Context mCtx, List<OrderModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;

    }

    @Override
    public OrderAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cart_item, null);
        return new OrderAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ProductViewHolder holder, final int position) {

        final OrderModel product = productList.get(position);
        final String cartID = product.getCartId();

        Log.e("cartid", cartID);

        Currency currency = Currency.getInstance(product.getCurrency());
        final String symbol = currency.getSymbol();

        holder.textViewTitle.setText(product.getName());
        holder.textViewcurrncy.setText(symbol);
      //  holder.textViewprice.setText(product.getPrice());
        final String textprice= String.valueOf(productList.get(position).getTotalprice());
        holder.textViewprice.setText(textprice);
        holder.textViewQty.setText(product.getQuantity());

        Glide.with(mCtx)
                .load(product.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.imageView);


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mCtx);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.quantitytxt);

                final EditText qunty = dialog.findViewById(R.id.et);
                holder.textViewQty.setText(qunty.getText().toString());


                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                long result =  ((CheckOutActivity)mCtx).AddMethod(cartID, Integer.parseInt(qunty.getText().toString()));
                if (result == 1) {
                    holder.textViewQty.setText(qunty.getText().toString());
                    dialog.dismiss();

                }
                    }
                });

                Button btncn = (Button) dialog.findViewById(R.id.btncn);
                btncn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               int qtyadd= Integer.parseInt(product.getQuantity());
//                qtyadd= qtyadd+1;
//                holder.textViewQty.setText(String.valueOf(""+ qtyadd));
//                product.setQuantity(String.valueOf(qtyadd));
////                SharedPreferences pref = mCtx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
////                SharedPreferences.Editor edit = pref.edit();
////                edit.putString("qty", String.valueOf(qty));
////                edit.apply();
////
////                long result =((CheckOutActivity)mCtx).AddMethod(qtyadd);
////                if (result == 1) {
////                    SharedPreferences pref = mCtx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
////                    qtyadd= Integer.parseInt(pref.getString("qty", ""));
////                    //Log.e("qty", String.valueOf(qtyadd));
////
////                    holder.textViewQty.setText(String.valueOf(""+ qtyadd));
////                    product.setQuantity(String.valueOf(qtyadd));
////                }
//
//
//               // ((CheckOutActivity)mCtx).AddMethod(cartID,qty);
////                long result =  ((CheckOutActivity)mCtx).AddMethod(cartID,qty);
////                if (result == 1) {
////                    holder.textViewQty.setText(String.valueOf(""+ qty));
////                    product.setQuantity(String.valueOf(qty));
////                   // Log.e("qty", String.valueOf(qty));
////                    //Toast.makeText(mCtx, "Updated successfully!", Toast.LENGTH_SHORT).show();
////                }
////                else {
////                    Toast.makeText(mCtx, "Not Updated", Toast.LENGTH_SHORT).show();
////                }
//            }
//        });
//
//        holder.remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int qtymins= Integer.parseInt(product.getQuantity());
//
//                if (qtymins>1) {
//                    qtymins = qtymins - 1;
//                    holder.textViewQty.setText(String.valueOf(""+ qtymins));
//                    product.setQuantity(String.valueOf(qtymins));
//
//                   // long result =  ((CheckOutActivity)mCtx).RemoveMethod(cartID,qty1);
//
////                    if (result == 1) {
////                        holder.textViewQty.setText(String.valueOf(""+ qty1));
////                        product.setQuantity(String.valueOf(qty1));
////                        Toast.makeText(mCtx, "Updated successfully!", Toast.LENGTH_SHORT).show();
////                    } else {
////                        Toast.makeText(mCtx, "Not Updated", Toast.LENGTH_SHORT).show();
////                    }
//
//                }
//            }
//        });
//
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    ((CheckOutActivity)mCtx).DeleteMethod(cartID);
                       productList.remove(position);
                       notifyDataSetChanged();
            }
        });

    }


        @Override
    public int getItemCount() {
        return productList.size();
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder  {
        TextView textViewprice;
        TextView textViewTitle, textViewcurrncy, textViewQty, qtytext;
        ImageView imageView, deletebtn;
        ImageButton add, remove;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.prodname);
            textViewcurrncy = itemView.findViewById(R.id.prdcrncy);
            textViewprice = itemView.findViewById(R.id.prdprice);
            textViewQty = itemView.findViewById(R.id.editqnty);
            deletebtn = itemView.findViewById(R.id.deletebtn);
            imageView = itemView.findViewById(R.id.compid);
            add=itemView.findViewById(R.id.add);
            remove=itemView.findViewById(R.id.remov);
        }
    }
}
