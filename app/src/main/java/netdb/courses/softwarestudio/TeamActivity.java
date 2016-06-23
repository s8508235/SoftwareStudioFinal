package netdb.courses.softwarestudio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

/**
 * Created by Bill on 2016/6/18.
 */
public class TeamActivity extends ActionBarActivity {
    private ArrayAdapter<String> mTeamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TeamFragment())
                    .commit();
        }
    }
}
