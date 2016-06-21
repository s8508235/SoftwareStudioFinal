package netdb.courses.softwarestudio;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Bill on 2016/6/19.
 */
public class SetGameDetailFragment extends Fragment{
    private SamplePagerAdapter mtopAdapter,mbotAdapter;
    public SetGameDetailFragment() {
        setHasOptionsMenu(true);
    }
    private boolean init = false;
    private int team_num = 5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("建立比賽");
        View rootView = inflater.inflate(R.layout.fragment_set_game_detail, container, false);

        ViewPager top_viewpager = (ViewPager) rootView.findViewById(R.id.top_viewpager);
        CircleIndicator top_indicator = (CircleIndicator) rootView.findViewById(R.id.top_indicator);
        mtopAdapter = new SamplePagerAdapter(0) {
            @Override public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        top_viewpager.setAdapter(mtopAdapter);
        top_indicator.setViewPager(top_viewpager);



        ViewPager bot_viewpager = (ViewPager) rootView.findViewById(R.id.bot_viewpager);
        CircleIndicator bot_indicator = (CircleIndicator) rootView.findViewById(R.id.bot_indicator);
        mbotAdapter = new SamplePagerAdapter(team_num) {
            @Override public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        bot_viewpager.setAdapter(mbotAdapter);
        bot_indicator.setViewPager(bot_viewpager);

        return rootView;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.set_team_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.set_team_button_submit) {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class SamplePagerAdapter extends PagerAdapter {

        private final Random random = new Random();
        private int mSize;

        public SamplePagerAdapter() {
            mSize = 5;
        }

        public SamplePagerAdapter(int count) {
            mSize = count;
        }

        @Override public int getCount() {
            return mSize;
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override public Object instantiateItem(ViewGroup view, int position) {
            TextView textView = new TextView(view.getContext());
            textView.setText(String.valueOf(position + 1));
            textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(48);
            view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return textView;
        }
    }
}
