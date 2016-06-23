package netdb.courses.softwarestudio.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by SeanKang on 2016/6/21.
 */
public class UpdateTeamDetailAdapter extends CursorAdapter {

    private int teamNum, index, nindex;
    private String teamName;
    private Context mContext;
    ViewHolder viewholder;
    private List<String> title = new ArrayList<String>();
    public int gamedata_id[];
    public String originName[];
    public HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    public HashMap<Integer, String> numhashMap = new HashMap<Integer, String>();

    public UpdateTeamDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        hashMap.clear();
        numhashMap.clear();
    }
    public static class ViewHolder {

        public final TextView textView;
        public final EditText playerText;
        public final EditText numText;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.set_team_textView);
            playerText = (EditText) view.findViewById(R.id.set_team_editText);
            numText = (EditText) view.findViewById(R.id.set_team_numberText);
        }
    }

    public void setTeam(int num, String name){
        teamNum = num;
        teamName = name;
        gamedata_id = new int[teamNum];
        originName = new String[teamNum];
        for(int i = 1; i <= teamNum; i++){
            title.add("Player" + i + ":");
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_set_team, viewGroup, false);
        viewholder = new ViewHolder(view);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        viewholder.textView.setText(title.get(cursor.getPosition()));
        // Find TextView and set formatted date on it
        String player = cursor.getString(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_NAME));
        String number = cursor.getString(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_ID));

        viewholder.playerText.setText(player);
        viewholder.numText.setText(number);

        gamedata_id[cursor.getPosition()] = cursor.getInt(cursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID));
        originName[cursor.getPosition()] = player;

        if(hashMap.size() <= cursor.getPosition()) {
            hashMap.put(cursor.getPosition(), player);
            numhashMap.put(cursor.getPosition(), number);
        }

        viewholder.playerText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    index = cursor.getPosition();
                return false;
            }
        });

        viewholder.numText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    nindex = cursor.getPosition();
                return false;
            }
        });

        viewholder.playerText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                hashMap.put(cursor.getPosition(), editable.toString());
            }
        });

        viewholder.numText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                numhashMap.put(cursor.getPosition(), editable.toString());
            }
        });
        if(numhashMap.get(cursor.getPosition())!=null){
            viewholder.numText.setText(numhashMap.get(cursor.getPosition()));
        }

        if(hashMap.get(cursor.getPosition())!=null){
            viewholder.playerText.setText(hashMap.get(cursor.getPosition()));
        }

        viewholder.playerText.clearFocus();
        viewholder.numText.clearFocus();

        if(nindex != -1 && nindex == cursor.getPosition()){
            viewholder.numText.requestFocus();
        }

        if(index != -1 && index == cursor.getPosition()){
            viewholder.playerText.requestFocus();
        }
    }
}
