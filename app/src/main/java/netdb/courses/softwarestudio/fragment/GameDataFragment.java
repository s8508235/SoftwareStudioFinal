package netdb.courses.softwarestudio.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.UpdateGameActivity;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by Bill on 2016/6/19.
 */
public class GameDataFragment extends Fragment {
    private ListView listView;
    private String mTeamDetailStr;
    mAdapter adapter;
    private String player, data_id, game, team_id;
    Cursor data;
    int[] game_data = new int[14];

    List<String> dataList = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        player = getArguments().getString("player");
        data_id = getArguments().getString("data_id");
        game = getArguments().getString("game");
        team_id = getArguments().getString("team_id");
        data = getContext().getContentResolver().query(
                TeamContract.GameDataEntry.CONTENT_URI,
                null,
                TeamContract.GameDataEntry.TABLE_NAME + "." + TeamContract.GameDataEntry._ID + " = ?",
                new String[]{data_id},
                null
        );
        Log.d("AAAAAAAAAAAAAA", ""+data_id);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.set_team_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.set_team_button_submit){
            ContentValues contentValues = new ContentValues();

            contentValues.put(TeamContract.GameDataEntry.COLUMN_AST, Integer.toString(game_data[0]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_BLOCK, Integer.toString(game_data[1]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_OFF, Integer.toString(game_data[2]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_DEF, Integer.toString(game_data[3]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_SHOT, Integer.toString(game_data[6]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_THREE, Integer.toString(game_data[7]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_FREE_THROW, Integer.toString(game_data[8]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_TOTAL_SHOT, Integer.toString(game_data[9]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_TOTAL_THREE, Integer.toString(game_data[10]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_TOTAL_FREE_THROW, Integer.toString(game_data[11]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_STEAL, Integer.toString(game_data[12]));
            contentValues.put(TeamContract.GameDataEntry.COLUMN_TURNOVER, Integer.toString(game_data[13]));

            int k = getContext().getContentResolver().update(
                    TeamContract.GameDataEntry.CONTENT_URI,
                    contentValues,
                    TeamContract.GameDataEntry.TABLE_NAME + "." + TeamContract.GameDataEntry._ID + " = ?",
                    new String[]{data_id}
            );

            Cursor teamS = getContext().getContentResolver().query(
                    TeamContract.GameEntry.CONTENT_URI,
                    null,
                    TeamContract.GameEntry.TABLE_NAME + "." + TeamContract.GameEntry.COLUMN_NAME + " = ?",
                    new String[]{game},
                    null
            );
            teamS.moveToFirst();
            ContentValues Nscore = new ContentValues();
            int score;
            if(teamS.getString(teamS.getColumnIndex(TeamContract.GameEntry.COLUMN_TEAMA)).equals(team_id)){
                score = teamS.getInt(teamS.getColumnIndex(TeamContract.GameEntry.COLUMN_TEAMA_SCORE)) + game_data[5];
                Nscore.put(TeamContract.GameEntry.COLUMN_TEAMA_SCORE, score);
            }else{
                score = teamS.getInt(teamS.getColumnIndex(TeamContract.GameEntry.COLUMN_TEAMB_SCORE)) + game_data[5];
                Nscore.put(TeamContract.GameEntry.COLUMN_TEAMB_SCORE, score);
            }


            getContext().getContentResolver().update(
                    TeamContract.GameEntry.CONTENT_URI,
                    Nscore,
                    TeamContract.GameEntry.TABLE_NAME + "." + TeamContract.GameEntry.COLUMN_NAME + " = ?",
                    new String[]{game}
            );

            if(k > 0) {
                Toast.makeText(getContext(), "Save Success", Toast.LENGTH_SHORT);
            }
            Intent intent = new Intent(getContext(), UpdateGameActivity.class);
            intent.putExtra("game", game);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(player);
        View rootView = inflater.inflate(R.layout.fragment_game_data, container, false);
        addData();
        listView = (ListView)rootView.findViewById(R.id.game_data_listView);

        adapter = new mAdapter();
        listView.setAdapter(adapter);

        return rootView;
    }
    private void addData(){
        data.moveToFirst();
        game_data[0] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_AST));
        game_data[1] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_BLOCK));
        game_data[2] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_OFF));
        game_data[3] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_DEF));
        game_data[4] = game_data[3] + game_data[2];
        game_data[6] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_SHOT));
        game_data[7] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_THREE));
        game_data[8] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_FREE_THROW));
        game_data[9] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_TOTAL_SHOT));
        game_data[10] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_TOTAL_THREE));
        game_data[11] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_TOTAL_FREE_THROW));
        game_data[12] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_STEAL));
        game_data[13] = data.getInt(data.getColumnIndex(TeamContract.GameDataEntry.COLUMN_TURNOVER));
        game_data[5] = game_data[7] * 3 + game_data[8] + (game_data[6] - game_data[7]) * 2;
        dataList.add("AST:");
        dataList.add("BS:");
        dataList.add("OFF:");
        dataList.add("DEF:");
        dataList.add("TOT:");
        dataList.add("PTS:");
        dataList.add("FGM:");
        dataList.add("3PM:");
        dataList.add("FTM:");
        dataList.add("TOTFGM:");
        dataList.add("TOT3PM:");
        dataList.add("TOTFTM:");
        dataList.add("ST:");
        dataList.add("TO:");
    }


    class mAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            return dataList.size();
        }

        @Override
        public Object getItem(int position){
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            final String str = dataList.get(position);

            convertView = LayoutInflater.from(getActivity().getApplication())
                    .inflate(R.layout.list_item_game_data,null);
            final TextView textview = (TextView)convertView.findViewById(R.id.game_data_textView);
            final TextView numview = (TextView) convertView.findViewById(R.id.game_data_num_textView);

            textview.setText(str);
            numview.setText(Integer.toString(game_data[position]));

            final NumberPicker numPicker = (NumberPicker) convertView.findViewById(R.id.game_data_numberPicker);

            if(position != 4 && position != 5) {
                numPicker.setEnabled(true);
                numPicker.setMaxValue(100);
                numPicker.setMinValue(0);
                numPicker.setValue(game_data[position]);
                numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    public void onValueChange(NumberPicker view, int oldValue, int newValue) {
                        game_data[position] = newValue;
                        game_data[4] = game_data[2] + game_data[3];
                        game_data[5] = game_data[7] * 3 + game_data[8] + (game_data[6] - game_data[7]) * 2;
                        numview.setText(String.valueOf(newValue));
                        if(position == 2 || position == 3 || position == 6 || position == 7 || position == 8)
                            adapter.notifyDataSetChanged();
                    }
                });
            }else{

                numPicker.setValue(game_data[position]);
                numPicker.setEnabled(false);

            }

            return convertView;
        }
    }
}
