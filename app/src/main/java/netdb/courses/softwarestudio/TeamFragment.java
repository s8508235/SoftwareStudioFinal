package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bill on 2016/6/19.
 */
public class TeamFragment extends Fragment {
    private ArrayAdapter<String> mTeamAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] datas = {
                "建立隊伍",
                "編輯隊伍",
                "建立比賽",
                "編輯比賽",
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(datas));
        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mTeamAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_team, // The name of the layout ID.
                        R.id.list_item_team_textview, // The ID of the textview to populate.
                        weekForecast);//data

        View rootView = inflater.inflate(R.layout.team_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_team);
        listView.setAdapter(mTeamAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String s = Integer.toString(position);
                Intent intent = new Intent(getActivity(), TeamDetailActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, s);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
