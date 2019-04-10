package solutions.thinkbiz.grocery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import solutions.thinkbiz.grocery.TopOffersPkg.TopOfferDetaillsActivity;

/**
 * Created by User on 03-Apr-19.
 */

public class SearchPrdAdapter extends RecyclerView.Adapter<SearchPrdAdapter.ViewHolder> {


private List<SearchPrdModel> mApps;
private Context mContext;

public SearchPrdAdapter(Context context,List<SearchPrdModel> apps){
        mContext=context;
        mApps=apps;
        }


@Override
public SearchPrdAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.autocompletetext, parent, false);
        return new SearchPrdAdapter.ViewHolder(v);
        }

@Override
public void onBindViewHolder(final SearchPrdAdapter.ViewHolder holder, int position) {
final SearchPrdModel app=mApps.get(position);

        String productName = app.getmName();

        holder.mTextViewName.setText(productName);

        holder.setItemClickListener(new RecyclerViewItemClickListener() {
@Override
public void onClick(View view, int position) {

    Currency currency = Currency.getInstance(app.getmCurrency());
    final String symbol = currency.getSymbol();

    String prodID=app.getId();
    String prodname=app.getmName();
    String prodprice=app.getmPrice();
    String descript = app.getDescr();
    String imageurl=app.getmImageUrl();

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

public void filterList(ArrayList<SearchPrdModel> filterdNames) {
        this.mApps = filterdNames;
        notifyDataSetChanged();
        }


@Override
public int getItemViewType(int position){

        return super.getItemViewType(position);
        }

@Override
public int getItemCount() {
        return mApps.size();
        }



public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView mTextViewName;

    private RecyclerViewItemClickListener itemClickListener;


    public ViewHolder(View itemView ) {

        super(itemView);
        mTextViewName=(TextView) itemView.findViewById(R.id.textView);
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
