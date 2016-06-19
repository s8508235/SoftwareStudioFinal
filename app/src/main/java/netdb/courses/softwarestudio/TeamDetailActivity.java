package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Bill on 2016/6/18.
 */
public class TeamDetailActivity extends ActionBarActivity {
    private SetGameDetailFragment setGameDetailFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail_main);
        Intent intent = getIntent();
        String mTeamDetailStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (savedInstanceState == null) {
            if(mTeamDetailStr.equals("0"))
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new SetTeamDetailFragment())
                        .commit();
            }
            else if(mTeamDetailStr.equals("1"))
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new EditTeamDetailFragment())
                        .commit();
            }
            else if(mTeamDetailStr.equals("2"))
            {
                setGameDetailFragment = new SetGameDetailFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, setGameDetailFragment)
                        .commit();
            }
            else if(mTeamDetailStr.equals("3"))
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new EditGameDetailFragment())
                        .commit();
            }

        }
    }

}
