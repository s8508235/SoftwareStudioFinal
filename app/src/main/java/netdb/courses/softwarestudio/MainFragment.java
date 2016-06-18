package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
/**
 * Created by Bill on 2016/6/18.
 */
public class MainFragment extends Fragment {

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

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Button btn1 = (Button) rootView.findViewById(R.id.team_button);
        Button btn2 = (Button) rootView.findViewById(R.id.solo_button);
        btn1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SoloActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
/*
    @Override
    protected void onPostExecute(String[] result) {
        if (result != null) {
            mTeamAdapter.clear();
            for(String dayForecastStr : result) {
                mTeamAdapter.add(dayForecastStr);
            }
            // New data is back from the server.  Hooray!
        }
    }*/
}
