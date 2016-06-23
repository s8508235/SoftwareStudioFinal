package netdb.courses.softwarestudio.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.TeamActivity;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by SeanKang on 2016/6/22.
 */
public class UpdateGameDetailAdapter extends BaseAdapter {

    Context mContext;
    Cursor cursor;
    String team_id;
    public UpdateGameDetailAdapter(Context context, Cursor id, String team){
        mContext = context;
        cursor = id;
        team_id = team;
    }

    @Override
    public int getCount(){
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position){
        cursor.moveToPosition(position);
       // Log.d("KKKKKKKKK", cursor.getString(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID)));
        return cursor.getString(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME)) + "," +
                cursor.getString(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID)) + "," + team_id;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        cursor.moveToPosition(position);
        String str = cursor.getString(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME));

        convertView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_update_game,null);
        final TextView textview = (TextView) convertView.findViewById(R.id.update_game_textView);

        textview.setText(str);

        return convertView;
    }
}
