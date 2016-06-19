package netdb.courses.softwarestudio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bill on 2016/6/19.
 */
public class EditGameDetailFragment extends Fragment {
    private String mTeamDetailStr;

    public EditGameDetailFragment() {
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("編輯比賽");
        View rootView = inflater.inflate(R.layout.fragment_edit_game_detail, container, false);
        // The detail Activity called via intent.  Inspect the intent for forecast data.

        return rootView;
    }
}
