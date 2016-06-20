package netdb.courses.softwarestudio.fragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

import me.relex.circleindicator.CircleIndicator;
import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.adapter.SetGameDetailAdapter;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by Bill on 2016/6/19.
 */
public class SetGameDetailFragment extends Fragment{

    private SetGameDetailAdapter mtopAdapter,mbotAdapter;
    private ViewPager top_viewpager, bot_viewpager;

    public SetGameDetailFragment() {
        setHasOptionsMenu(true);
    }

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


    public int getTeamNumber(){
        Cursor cursor = getContext().getContentResolver().query(
                TeamContract.TeamEntry.CONTENT_URI,
                new String[]{TeamContract.TeamEntry._ID},
                null,
                null,
                null
        );
        int teamNum = cursor.getCount();
        cursor.close();
        return teamNum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("建立比賽");
        int team_num = getTeamNumber();

        View rootView = inflater.inflate(R.layout.fragment_set_game_detail, container, false);

        top_viewpager = (ViewPager) rootView.findViewById(R.id.top_viewpager);
        CircleIndicator top_indicator = (CircleIndicator) rootView.findViewById(R.id.top_indicator);
        mtopAdapter = new SetGameDetailAdapter(getContext() ,team_num) {
            @Override public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        top_viewpager.setAdapter(mtopAdapter);
        top_indicator.setViewPager(top_viewpager);

        bot_viewpager = (ViewPager) rootView.findViewById(R.id.bot_viewpager);
        CircleIndicator bot_indicator = (CircleIndicator) rootView.findViewById(R.id.bot_indicator);
        mbotAdapter = new SetGameDetailAdapter(getContext(),team_num) {
            @Override public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        bot_viewpager.setAdapter(mbotAdapter);
        bot_indicator.setViewPager(bot_viewpager);

        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.set_team_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.set_team_button_submit) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final View v = layoutInflater.inflate(R.layout.pop_edit_text,null);

            new AlertDialog.Builder(getActivity())
                    .setTitle("請輸入比賽名稱")
                    .setView(v)
                    .setPositiveButton("取消", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("確定", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText editText = (EditText) (v.findViewById(R.id.pop_editText));
                            editText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }
                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                }
                            });


                            Cursor cursor = getContext().getContentResolver().query(
                                    TeamContract.GameEntry.CONTENT_URI,
                                    new String[]{TeamContract.GameEntry._ID},
                                    TeamContract.GameEntry.COLUMN_NAME + " = ?",
                                    new String[]{editText.getText().toString()},
                                    null);

                            if(cursor.moveToFirst()){
                                Toast.makeText(getContext(),"Set Game Fail", Toast.LENGTH_SHORT).show();
                            }else{
                                ContentValues values = new ContentValues();

                                Cursor topCursor = mtopAdapter.getCursor(), botCursor =  mbotAdapter.getCursor();
                                topCursor.moveToPosition(top_viewpager.getCurrentItem());
                                botCursor.moveToPosition(bot_viewpager.getCurrentItem());

                                values.put(TeamContract.GameEntry.COLUMN_NAME, editText.getText().toString());
                                values.put(TeamContract.GameEntry.COLUMN_TEAMA, topCursor.getInt(
                                        topCursor.getColumnIndex(TeamContract.TeamEntry._ID)));
                                values.put(TeamContract.GameEntry.COLUMN_TEAMA_SCORE, 0);
                                values.put(TeamContract.GameEntry.COLUMN_TEAMB, botCursor.getInt(
                                        botCursor.getColumnIndex(TeamContract.TeamEntry._ID)));
                                values.put(TeamContract.GameEntry.COLUMN_TEAMB_SCORE, 0);
                                values.put(TeamContract.GameEntry.COLUMN_WINNER, 0);


                                Uri _id = getContext().getContentResolver().insert(TeamContract.GameEntry.CONTENT_URI, values);
                                Toast.makeText(getContext(),"Set Game Success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .show();
            /*
            Toast.makeText(getContext(),"Set Game Success", Toast.LENGTH_SHORT).show();
            ContentValues values = new ContentValues();

            values.put(TeamContract.GameEntry.COLUMN_NAME, gameName);
            values.put(TeamContract.GameEntry.COLUMN_TEAMA, mtopAdapter.getCursor().getString(
                    mtopAdapter.getCursor().getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME)));
            values.put(TeamContract.GameEntry.COLUMN_TEAMA_SCORE, 0);
            values.put(TeamContract.GameEntry.COLUMN_TEAMB, mbotAdapter.getCursor().getString(
                    mbotAdapter.getCursor().getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME)));
            values.put(TeamContract.GameEntry.COLUMN_TEAMB_SCORE, 0);
            values.put(TeamContract.GameEntry.COLUMN_WINNER, 0);

            Uri _id = getContext().getContentResolver().insert(TeamContract.GameEntry.CONTENT_URI, values);

            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
