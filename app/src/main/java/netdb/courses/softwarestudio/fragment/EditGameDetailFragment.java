package netdb.courses.softwarestudio.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.data.TeamContract;
import netdb.courses.softwarestudio.adapter.EditGameDetailAdapter;

/**
 * Created by Bill on 2016/6/19.
 */
public class EditGameDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDITGAME_LOADER = 0;
    private ListView listView;
    private EditGameDetailAdapter mEditGameDetailAdapter;
    private static final String[] EDITGAME_COLUMS = {
            TeamContract.GameEntry.TABLE_NAME + "." + TeamContract.GameEntry._ID,
            TeamContract.GameEntry.COLUMN_NAME,
            TeamContract.GameEntry.COLUMN_TEAMA,
            TeamContract.GameEntry.COLUMN_TEAMA_SCORE,
            TeamContract.GameEntry.COLUMN_TEAMB_SCORE,
            TeamContract.GameEntry.COLUMN_TEAMB
    };

    static final int COL_GAME_ID = 0;
    public static final int COL_GAME_NAME = 1;
    static final int COL_GAME_TEAMA = 2;
    static final int COL_GAME_TEAMA_SCORE = 3;
    static final int COL_GAME_TEAMB_SCORE = 4;
    static final int COL_GAME_TEAMB = 5;

    public EditGameDetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("編輯比賽");

        mEditGameDetailAdapter = new EditGameDetailAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_edit_team_detail, container, false);

        listView = (ListView) rootView.findViewById(R.id.edit_team_listView);
        listView.setAdapter(mEditGameDetailAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(EDITGAME_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // To only show current and future dates, filter the query to return weather only for
        // dates after or including today.

        // Sort order:  Ascending, by date.
        String sortOrder = TeamContract.GameEntry._ID + " ASC";

        Uri GameUri = TeamContract.GameEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),
                GameUri,
                EDITGAME_COLUMS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mEditGameDetailAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mEditGameDetailAdapter.swapCursor(null);
    }


}
