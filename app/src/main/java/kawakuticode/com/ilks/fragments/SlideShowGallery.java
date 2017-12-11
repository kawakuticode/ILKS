package kawakuticode.com.ilks.fragments;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kawakuticode.com.ilks.Model.EventAlbum;
import kawakuticode.com.ilks.Model.PictureSource;
import kawakuticode.com.ilks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideShowGallery extends DialogFragment {

    private ArrayList<PictureSource> images;
    private EventAlbum album;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount;
    private int selectedPosition = 0;
    private String TAG = SlideShowGallery.class.getSimpleName();
    private final String ALBUM_BUNDLE_KEY = "album";


    public SlideShowGallery() {

    }

    public static SlideShowGallery newInstance() {
        SlideShowGallery f = new SlideShowGallery();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        album = bundle.getParcelable(ALBUM_BUNDLE_KEY);


        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);

        images = (ArrayList<PictureSource>) album.getSources();
        selectedPosition = getArguments().getInt("position");

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + images.size());

        PictureSource image = images.get(position);

    }


    // adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.album_full_view_image, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

            PictureSource image = images.get(position);

            Glide.with(getActivity()).load(image.getSource())
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}