package netdb.courses.softwarestudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import netdb.courses.softwarestudio.fragment.GameDataFragment;

/**
 * Created by Bill on 2016/6/19.
 */
public class GameDataActivity extends AppCompatActivity {
    private String extra[];
    private String game, data_id, player, team_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_data_main);

        Intent intent = getIntent();
        extra = intent.getStringExtra(Intent.EXTRA_TEXT).split(",");
        player = extra[0]; data_id = extra[1]; team_id = extra[2]; game = extra[3];

        Bundle bundle = new Bundle();
        bundle.putString("player", player);
        bundle.putString("data_id", data_id);
        bundle.putString("game", game);
        bundle.putString("team_id", team_id);
        GameDataFragment mFragment = new GameDataFragment();
        mFragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contain, mFragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed(){
        toBack();
    }
    public void toBack(){
        Intent intent = new Intent(this, UpdateGameActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                toBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
