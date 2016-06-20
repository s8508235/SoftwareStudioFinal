package netdb.courses.softwarestudio.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by SeanKang on 2016/6/20.
 */
public class SetGameDetailAdapter extends PagerAdapter {

    Context mContext;
    private final Random random = new Random();
    private int mSize;
    private Cursor cursor;
    private int fposition;

    public SetGameDetailAdapter(Context context ,int count) {
        mSize = count;
        mContext = context;
        cursor = mContext.getContentResolver().query(
                TeamContract.TeamEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    public int getPosition(){
        return fposition;
    }

    public Cursor getCursor(){
        return cursor;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        String teamName = getTeamName(position);

        TextView textView = new TextView(view.getContext());
        textView.setText(teamName);
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(48);

        view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return textView;
    }

    private String getTeamName(int position){
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME));
    }
}
