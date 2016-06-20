package netdb.courses.softwarestudio.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netdb.courses.softwarestudio.R;
import netdb.courses.softwarestudio.TeamActivity;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateTeamDetailFragment extends Fragment{
    private ListView listView;
    private mAdapter adapter;
    List<String> dataList = new ArrayList<String>();
    List<String> playerList = new ArrayList<String>();
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public UpdateTeamDetailFragment() {
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("修改隊伍");
        addData();
        View rootView = inflater.inflate(R.layout.fragment_update_team_detail, container, false);

        Button btn = (Button) rootView.findViewById(R.id.update_team_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TeamActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) rootView.findViewById(R.id.update_team_listView);
        adapter = new mAdapter();
        listView.setAdapter(adapter);
        return rootView;
    }

    private void addData(){
        dataList.add("mem1");
        dataList.add("mem2");
        playerList.add("player1:");
        playerList.add("player2:");
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
            String pstr = playerList.get(position);
            convertView = LayoutInflater.from(getActivity().getApplication())
                    .inflate(R.layout.list_item_set_team,null);
            final EditText editText = (EditText)convertView.findViewById(R.id.set_team_editText);
            final TextView textview = (TextView)convertView.findViewById(R.id.set_team_textView);
            editText.setText(str);
            textview.setText(pstr);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    hashMap.put(position, editable.toString());
                }
            });
            if(hashMap.get(position)!=null){
                editText.setText(hashMap.get(position));
            }

            return convertView;
        }
    }
}
