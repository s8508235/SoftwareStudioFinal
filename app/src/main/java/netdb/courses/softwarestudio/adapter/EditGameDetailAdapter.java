package netdb.courses.softwarestudio.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.UpdateGameActivity;
import netdb.courses.softwarestudio.data.TeamContract;

/**
 * Created by SeanKang on 2016/6/20.
 */
public class EditGameDetailAdapter extends CursorAdapter {

    private Context mcontext;

    public EditGameDetailAdapter(Context context, Cursor c, int flags) {
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

        // Read date from cursor
        String game = cursor.getString(cursor.getColumnIndex(TeamContract.GameEntry.COLUMN_NAME));
        // Find TextView and set formatted date on it
        viewHolder.dateView.setText(game);

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, UpdateGameActivity.class);
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
                                String selection = TeamContract.GameEntry.TABLE_NAME+
                                        "." + TeamContract.GameEntry.COLUMN_NAME + " = ? ";
                                mcontext.getContentResolver().delete(TeamContract.GameEntry.CONTENT_URI, selection,
                                        new String[]{viewHolder.dateView.getText().toString()});
                                Toast.makeText(mcontext, "Delete successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
}
