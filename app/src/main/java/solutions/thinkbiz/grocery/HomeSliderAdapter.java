package solutions.thinkbiz.grocery;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by User on 11-Feb-19.
 */

public class HomeSliderAdapter extends PagerAdapter {

    private Context context;
    private Integer [] images={R.drawable.slider1, R.drawable.slider1,
            R.drawable.slider1,R.drawable.slider1,R.drawable.slider1,R.drawable.slider1,R.drawable.slider1,R.drawable.slider1};
    private LayoutInflater layoutInflater;
    public HomeSliderAdapter(Context context) {
        this.context = context;

}
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slideritem, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        ViewPager vp=(ViewPager) container;
        vp.addView(view,0);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp=(ViewPager) container;
        View view=(View) object;
        vp.removeView(view);
    }
}

