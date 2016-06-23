package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import netdb.courses.softwarestudio.fragment.UpdateTeamDetailFragment;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateTeamDetailActivity extends ActionBarActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int teanNum = Integer.parseInt(intent.getStringExtra("number"));
        Bundle tmp = new Bundle();
        tmp.putString("team", intent.getStringExtra("team"));
        tmp.putInt("number", teanNum);
        tmp.putString("id", intent.getStringExtra("id"));

        UpdateTeamDetailFragment mFragment = new UpdateTeamDetailFragment();
        mFragment.setArguments(tmp);

        setContentView(R.layout.update_team_detail_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contain, mFragment)
                    .commit();
        }
    }

}
