package netdb.courses.softwarestudio.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.TeamActivity;
import netdb.courses.softwarestudio.adapter.UpdateTeamDetailAdapter;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateTeamDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int UPDATETEAM_LOADER = 0;
    private ListView listView;
    private String teamName, id;
    private int teamNum;
    private UpdateTeamDetailAdapter adapter;

    private static final String[] UPDATETEAM_COLUMS = {
            TeamContract.PlayerEntry.TABLE_NAME + "." + TeamContract.PlayerEntry._ID,
            TeamContract.PlayerEntry.COLUMN_PLAYER_NAME,
            TeamContract.PlayerEntry.COLUMN_PLAYER_ID,
            TeamContract.PlayerEntry.COLUMN_TEAM_ID,
            TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID
    };

    static final int COL_TEAM_ID = 0;
    public static final int COL_TEAM_NAME = 1;
    static final int COL_TEAM_NUMBER = 2;

    public UpdateTeamDetailFragment() {
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        teamName = bundle.getString("team");
        teamNum = bundle.getInt("number");
        id = bundle.getString("id");

        adapter = new UpdateTeamDetailAdapter(getContext(), null, 0);
        adapter.setTeam(teamNum, teamName);
        getActivity().setTitle(teamName);
        View rootView = inflater.inflate(R.layout.fragment_update_team_detail, container, false);

        Button btn = (Button) rootView.findViewById(R.id.update_team_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i < teamNum; i++){

                    ContentValues player = new ContentValues();

                    player.put(TeamContract.PlayerEntry.COLUMN_PLAYER_ID, Integer.parseInt(adapter.numhashMap.get(i)));
                    player.put(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME, adapter.hashMap.get(i));
                    //player.put(TeamContract.PlayerEntry.COLUMN_TEAM_ID, Integer.parseInt(id));
                    //player.put(TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID, adapter.gamedata_id[i]);

                    Log.d("AAAAAAAAAAAAAAAAAAAAA", player.getAsString(TeamContract.PlayerEntry.COLUMN_PLAYER_ID)+
                    player.getAsString(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME) + " " );

                    String selection = TeamContract.PlayerEntry.TABLE_NAME + "." +
                            TeamContract.PlayerEntry.COLUMN_TEAM_ID + " = ? AND " +
                            TeamContract.PlayerEntry.COLUMN_PLAYER_NAME + " = ?";

                    getContext().getContentResolver().update(
                            TeamContract.PlayerEntry.CONTENT_URI,
                            player,
                            selection,
                            new String[]{id, adapter.originName[i]});

                }
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) rootView.findViewById(R.id.update_team_listView);

        listView.setAdapter(adapter);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(UPDATETEAM_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = TeamContract.PlayerEntry._ID + " ASC";
        String selection = TeamContract.PlayerEntry.TABLE_NAME + "."
                + TeamContract.PlayerEntry.COLUMN_TEAM_ID + " = ?";

        Uri PlayerUri = TeamContract.PlayerEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                PlayerUri,
                null,
                selection,
                new String[]{id},
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
