package netdb.courses.softwarestudio;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill on 2016/6/19.
 */
public class EditTeamDetailFragment extends Fragment {
    private ListView listView;
    private mAdapter adapter;
    List<String> dataList = new ArrayList<String>();
    public EditTeamDetailFragment() {
        setHasOptionsMenu(false);
    }
    private void addData(){
        dataList.add("team1");
        dataList.add("team2");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("編輯隊伍");
        addData();
        View rootView = inflater.inflate(R.layout.fragment_edit_team_detail, container, false);
        // The detail Activity called via intent.  Inspect the intent for forecast data.

        listView = (ListView) rootView.findViewById(R.id.edit_team_listView);
        adapter = new mAdapter();
        listView.setAdapter(adapter);
        return rootView;
    }
    class mAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            return dataList.size();
        }

        @Override
        public Object getItem(int position){
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            String str = dataList.get(position);
            convertView = LayoutInflater.from(getActivity().getApplication())
                    .inflate(R.layout.list_item_edit_team,null);
            final TextView textview = (TextView)convertView.findViewById(R.id.list_item_edit_team_textView);
            textview.setText(str);

            Button btn = (Button) convertView.findViewById(R.id.list_item_edit_team_button1);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Go to edit",Toast.LENGTH_LONG);
                    Intent intent = new Intent(getActivity(), UpdateTeamDetailActivity.class);
                    startActivity(intent);
                }
            });
            Button btn1 = (Button) convertView.findViewById(R.id.list_item_edit_team_button2);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"btn2 Success",Toast.LENGTH_LONG);
                    Intent intent = new Intent(getActivity(), TeamActivity.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
