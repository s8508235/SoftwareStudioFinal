package netdb.courses.softwarestudio.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.TeamActivity;
import netdb.courses.softwarestudio.adapter.SetTeamDetailAdapter;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by Bill on 2016/6/19.
 */
public class SetTeamDetailFragment extends Fragment {

    private ListView listView;
    private SetTeamDetailAdapter adapter;

    public SetTeamDetailFragment() {
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.set_team);
        adapter = new SetTeamDetailAdapter(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_set_team_detail, container, false);

        Button btn1 = (Button) rootView.findViewById(R.id.set_team_button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int total = adapter.getCount();

                if(total >= 6) {

                    ContentValues teamName = new ContentValues();
                    ContentValues players[] = new ContentValues[total-1];
                    ContentValues player_data = new ContentValues();

                    teamName.put(TeamContract.TeamEntry.COLUMN_TEAM_NAME, adapter.dataList.get(0));
                    teamName.put(TeamContract.TeamEntry.COLUMN_TEAM_NUM, total - 1);

                    Cursor cursor = getContext().getContentResolver().query(
                            TeamContract.TeamEntry.CONTENT_URI,
                            new String[]{TeamContract.TeamEntry._ID},
                            TeamContract.TeamEntry.COLUMN_TEAM_NAME + " = ?",
                            new String[]{adapter.dataList.get(0)},
                            null);

                    if (cursor.moveToFirst()) {
                        Toast.makeText(getContext(), R.string.already_had_the_same_name, Toast.LENGTH_SHORT).show();
                    } else {
                        Uri insertedUri = getContext().getContentResolver().insert(TeamContract.TeamEntry.CONTENT_URI, teamName);
                        long team_id = ContentUris.parseId(insertedUri);

                        player_data.put(TeamContract.GameDataEntry.COLUMN_AST, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_BLOCK, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_DEF, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_OFF, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_FREE_THROW, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_SHOT, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_STEAL, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_THREE, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_TOTAL_FREE_THROW, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_TOTAL_SHOT, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_TOTAL_THREE, 0);
                        player_data.put(TeamContract.GameDataEntry.COLUMN_TURNOVER, 0);

                        for (int i = 1; i < total; i++) {
                            insertedUri = getContext().getContentResolver().insert(TeamContract.GameDataEntry.CONTENT_URI, player_data);
                            long gameData_id = ContentUris.parseId(insertedUri);

                            ContentValues player = new ContentValues();
                            player.put(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME, adapter.dataList.get(i));
                            player.put(TeamContract.PlayerEntry.COLUMN_TEAM_ID, team_id);
                            player.put(TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID, gameData_id);
                            player.put(TeamContract.PlayerEntry.COLUMN_PLAYER_ID, Integer.parseInt(adapter.numberList.get(i)));
                            Log.d("AAAAAAA", adapter.dataList.get(i) + " " + Integer.parseInt(adapter.numberList.get(i)));
                            players[i-1] = player;
                        }
                        int k = getContext().getContentResolver().bulkInsert(TeamContract.PlayerEntry.CONTENT_URI, players);
                        Log.d("AAAAAAA", ""+k);
                        Toast.makeText(getContext(),R.string.success,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), TeamActivity.class);
                        startActivity(intent);
                    }
                    cursor.close();
                }else{
                    Toast.makeText(getContext(),R.string.not_enough_people_for_a_team,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn2 = (Button) rootView.findViewById(R.id.set_team_button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.dataList.add("mem"+ adapter.getCount());
                adapter.playerList.add("player" + (adapter.getCount()-1) + ":");
                adapter.numberList.add(""+(adapter.getCount()-1));
                adapter.notifyDataSetChanged();
            }
        });

        listView = (ListView) rootView.findViewById(R.id.set_team_listView);

        listView.setAdapter(adapter);

        return rootView;
    }
}
