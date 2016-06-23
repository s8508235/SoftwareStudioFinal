package netdb.courses.softwarestudio.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.UpdateGameActivity;
import netdb.courses.softwarestudio.UpdateTeamDetailActivity;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by SeanKang on 2016/6/20.
 */
public class EditTeamDetailAdapter extends CursorAdapter {
    private Context mcontext;

    public EditTeamDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mcontext = context;

    }

    public static class ViewHolder {

        public final TextView dateView;
        public final Button editButton;
        public final Button deletButton;

        public ViewHolder(View view) {
            dateView = (TextView) view.findViewById(R.id.list_item_edit_team_textView);
            editButton = (Button) view.findViewById(R.id.list_item_edit_team_button1);
            deletButton = (Button) view.findViewById(R.id.list_item_edit_team_button2);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_edit_team, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        String team = cursor.getString(cursor.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME));
        // Find TextView and set formatted date on it
        viewHolder.dateView.setText(team);

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selection = TeamContract.TeamEntry.TABLE_NAME +
                        "." + TeamContract.TeamEntry.COLUMN_TEAM_NAME + " = ? ";

                Cursor teamCursor = mcontext.getContentResolver().query(TeamContract.TeamEntry.CONTENT_URI,
                        null,
                        selection,
                        new String[]{viewHolder.dateView.getText().toString()},
                        null
                );
                teamCursor.moveToFirst();

                Intent intent = new Intent(mcontext, UpdateTeamDetailActivity.class);
                intent.putExtra("team",teamCursor.getString(teamCursor.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NAME)));
                intent.putExtra("number", teamCursor.getString(teamCursor.getColumnIndex(TeamContract.TeamEntry.COLUMN_TEAM_NUM)));
                intent.putExtra("id", teamCursor.getString(teamCursor.getColumnIndex(TeamContract.TeamEntry._ID)));
                mcontext.startActivity(intent);
            }
        });
        viewHolder.deletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
                final View v = layoutInflater.inflate(R.layout.pop_text_text,null);


                new AlertDialog.Builder(mContext)
                        .setTitle("確定是否刪除")
                        .setView(v)
                        .setPositiveButton("取消", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("確定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notifyDataSetChanged();
                                String selection = TeamContract.TeamEntry.TABLE_NAME +
                                        "." + TeamContract.TeamEntry.COLUMN_TEAM_NAME + " = ? ";

                                Cursor teamCursor = mcontext.getContentResolver().query(TeamContract.TeamEntry.CONTENT_URI,
                                        null,
                                        selection,
                                        new String[]{viewHolder.dateView.getText().toString()},
                                        null
                                );
                                teamCursor.moveToFirst();

                                mcontext.getContentResolver().delete(TeamContract.TeamEntry.CONTENT_URI, selection,
                                        new String[]{viewHolder.dateView.getText().toString()});

                                selection = TeamContract.PlayerEntry.TABLE_NAME +
                                        "." + TeamContract.PlayerEntry.COLUMN_TEAM_ID + " = ?";

                                Cursor playerCursor = mcontext.getContentResolver().query(TeamContract.PlayerEntry.CONTENT_URI,
                                        null,
                                        selection,
                                        new String[]{teamCursor.getString(teamCursor.getColumnIndex(TeamContract.TeamEntry._ID))},
                                        null
                                );
                                playerCursor.moveToFirst();

                                mcontext.getContentResolver().delete(TeamContract.PlayerEntry.CONTENT_URI, selection,
                                        new String[]{teamCursor.getString(teamCursor.getColumnIndex(TeamContract.TeamEntry._ID))});

                                selection = TeamContract.GameDataEntry.TABLE_NAME +
                                        "." + TeamContract.GameDataEntry._ID + " = ?";
                                do {
                                    int id = playerCursor.getColumnIndex(TeamContract.PlayerEntry.COLUMN_PLAYER_DATA_ID);
                                    if(id < 0) break;
                                    mcontext.getContentResolver().delete(TeamContract.GameDataEntry.CONTENT_URI, selection,
                                            new String[]{playerCursor.getString(id)});
                                }while (playerCursor.moveToNext());
                                Toast.makeText(mcontext, "Delete successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
}
