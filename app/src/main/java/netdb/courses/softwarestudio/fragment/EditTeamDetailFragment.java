package netdb.courses.softwarestudio.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.adapter.EditTeamDetailAdapter;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by Bill on 2016/6/19.
 */
public class EditTeamDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EDITTEAM_LOADER = 0;
    private ListView listView;
    private EditTeamDetailAdapter adapter;
    private Cursor cursor;
    List<String> dataList = new ArrayList<String>();

    private static final String[] EDITTEAM_COLUMS = {
            TeamContract.TeamEntry.TABLE_NAME + "." + TeamContract.TeamEntry._ID,
            TeamContract.TeamEntry.COLUMN_TEAM_NAME,
            TeamContract.TeamEntry.COLUMN_TEAM_NUM
    };

    static final int COL_TEAM_ID = 0;
    public static final int COL_TEAM_NAME = 1;
    static final int COL_TEAM_NUMBER = 2;


    public EditTeamDetailFragment() {
        setHasOptionsMenu(false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.edit_team);

        View rootView = inflater.inflate(R.layout.fragment_edit_team_detail, container, false);
        // The detail Activity called via intent.  Inspect the intent for forecast data.
        listView = (ListView) rootView.findViewById(R.id.edit_team_listView);
        adapter = new EditTeamDetailAdapter(getActivity(), null, 0);
        listView.setAdapter(adapter);

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(EDITTEAM_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String sortOrder = TeamContract.TeamEntry._ID + " ASC";

        Uri TeamUri = TeamContract.TeamEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                TeamUri,
                EDITTEAM_COLUMS,
                null,
                null,
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
