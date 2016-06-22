package netdb.courses.softwarestudio.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import netdb.courses.softwarestudio.GameDataActivity;
import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.adapter.UpdateGameDetailAdapter;
import netdb.courses.softwarestudio.adapter.UpdateTeamDetailAdapter;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateGameDetailFragment extends Fragment  {
    private String teamA, teamB;
    private ListView listViewA, listViewB;
    private UpdateGameDetailAdapter adapterA, adapterB;
    String game;
    Cursor gameCursor, teamACursor, teamBCursor, playerACursor, playerBCursor;

    public UpdateGameDetailFragment() {
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("比賽列表");

        Bundle bundle = getArguments();
        game = bundle.getString("game");

        gameCursor = getContext().getContentResolver().query(
                TeamContract.GameEntry.CONTENT_URI,
                null,
                TeamContract.GameEntry.TABLE_NAME + "." + TeamContract.GameEntry.COLUMN_NAME + " = ?",
                new String[]{game},
                null
        );
        gameCursor.moveToFirst();

        View rootView = inflater.inflate(R.layout.update_game_detail_main, container, false);

        listViewA = (ListView)rootView.findViewById(R.id.tab1_listview);
        listViewB = (ListView)rootView.findViewById(R.id.tab2_listview);

        teamA = gameCursor.getString(gameCursor.getColumnIndex(TeamContract.GameEntry.COLUMN_TEAMA));
        teamB = gameCursor.getString(gameCursor.getColumnIndex(TeamContract.GameEntry.COLUMN_TEAMB));

        teamACursor = getContext().getContentResolver().query(
                TeamContract.TeamEntry.CONTENT_URI,
                null,
                TeamContract.TeamEntry.TABLE_NAME + "." + TeamContract.TeamEntry._ID + " = ?",
                new String[]{teamA},
                null
        );

        teamBCursor = getContext().getContentResolver().query(
                TeamContract.TeamEntry.CONTENT_URI,
                null,
                TeamContract.TeamEntry.TABLE_NAME + "." + TeamContract.TeamEntry._ID + " = ?",
                new String[]{teamB},
                null
        );
        teamACursor.moveToFirst();
        teamBCursor.moveToFirst();

        String a_id = teamACursor.getString(teamACursor.getColumnIndex(TeamContract.TeamEntry._ID));
        String b_id = teamBCursor.getString(teamBCursor.getColumnIndex(TeamContract.TeamEntry._ID));

        playerACursor = getContext().getContentResolver().query(
                TeamContract.PlayerEntry.CONTENT_URI,
                null,
                TeamContract.PlayerEntry.TABLE_NAME + "." + TeamContract.PlayerEntry.COLUMN_TEAM_ID + " = ?",
                new String[]{a_id},
                null
        );

        playerBCursor = getContext().getContentResolver().query(
                TeamContract.PlayerEntry.CONTENT_URI,
                null,
                TeamContract.PlayerEntry.TABLE_NAME + "." + TeamContract.PlayerEntry.COLUMN_TEAM_ID + " = ?",
                new String[]{b_id},
                null
        );

        adapterA = new UpdateGameDetailAdapter(getContext(), playerACursor, a_id);
        adapterB = new UpdateGameDetailAdapter(getContext(), playerBCursor, b_id);

        listViewA.setAdapter(adapterA);
        listViewB.setAdapter(adapterB);

        listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = (String)adapterA.getItem(position) + "," + game;
                Intent intent = new Intent(getActivity(), GameDataActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        listViewB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = (String)adapterB.getItem(position) + "," + game;
                Intent intent = new Intent(getActivity(), GameDataActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        super.onCreate(savedInstanceState);
        TabHost host = (TabHost) rootView.findViewById(R.id.tabhost);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator(teamACursor.getString(teamACursor.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME)));
        host.addTab(spec);

        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator(teamBCursor.getString(teamACursor.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME)));
        host.addTab(spec);
        return rootView;
    }

}
