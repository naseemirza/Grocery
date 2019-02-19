package solutions.thinkbiz.grocery.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import solutions.thinkbiz.grocery.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BestSellerFrag extends Fragment {


    public BestSellerFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_best_seller, container, false);
    }

}
