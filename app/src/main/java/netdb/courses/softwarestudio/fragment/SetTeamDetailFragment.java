package netdb.courses.softwarestudio.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
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
        getActivity().setTitle("建立隊伍");
        adapter = new SetTeamDetailAdapter(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_set_team_detail, container, false);

        Button btn1 = (Button) rootView.findViewById(R.id.set_team_button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int total = adapter.getCount();
                HashMap<Integer, String> hashMap = adapter.getHashMap();

                ContentValues teamName = new ContentValues();
                ContentValues players[] = new ContentValues[total-1];

                teamName.put(TeamContract.TeamEntry.COLUMN_TEAM_NAME, adapter.dataList.get(0));
                teamName.put(TeamContract.TeamEntry.COLUMN_TEAM_NUM, total-1);

                for(int i = 1; i < total; i++){
                    players[i].put(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME, hashMap.get(i));

                }

                Cursor cursor = getContext().getContentResolver().query(
                        TeamContract.TeamEntry.CONTENT_URI,
                        new String[]{TeamContract.TeamEntry._ID},
                        TeamContract.TeamEntry.COLUMN_TEAM_NAME + " = ?",
                        new String[]{adapter.dataList.get(0)},
                        null);

                if(cursor.moveToFirst()){
                    Toast.makeText(getContext(), "重複隊伍", Toast.LENGTH_SHORT).show();
                }else {

                    Uri insertedUri = getContext().getContentResolver().insert(TeamContract.TeamEntry.CONTENT_URI, teamName);
                    Intent intent = new Intent(getActivity(), TeamActivity.class);
                    startActivity(intent);
                }
                cursor.close();
            }
        });

        Button btn2 = (Button) rootView.findViewById(R.id.set_team_button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.dataList.add("mem"+ adapter.numberOfData());
                adapter.playerList.add("player" + (adapter.numberOfData()-1) + ":");

                adapter.notifyDataSetChanged();
            }
        });

        listView = (ListView) rootView.findViewById(R.id.set_team_listView);

        listView.setAdapter(adapter);


        /*
        if(mTeamDetailStr.equals("1"))
        {
            rootView = inflater.inflate(R.layout.fragment_edit_team_detail, container, false);
        }
        else if(mTeamDetailStr.equals("2"))
        {
            rootView = inflater.inflate(R.layout.fragment_set_game_detail, container, false);
        }
        else if(mTeamDetailStr.equals("3"))
        {
            rootView = inflater.inflate(R.layout.fragment_edit_game_detail, container, false);
        }*/
        // The detail Activity called via intent.  Inspect the intent for forecast data.

        return rootView;
    }
}
