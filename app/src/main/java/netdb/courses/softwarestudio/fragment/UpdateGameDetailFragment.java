package netdb.courses.softwarestudio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import netdb.courses.softwarestudio.GameDataActivity;
import netdb.courses.softwarestudio.R;

/**
 * Created by Bill on 2016/6/19.
 */
public class UpdateGameDetailFragment extends Fragment {
    private String teamA, teamB;
    private ListView listViewA, listViewB;
    private mAdapter adapterA, adapterB;
    List<String> dataList = new ArrayList<String>();

    public UpdateGameDetailFragment() {
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("比賽列表");
        View rootView = inflater.inflate(R.layout.update_game_detail_main, container, false);
        addData();

        listViewA = (ListView)rootView.findViewById(R.id.tab1_listview);
        listViewB = (ListView)rootView.findViewById(R.id.tab2_listview);

        adapterA = new mAdapter();
        adapterB = new mAdapter();
        listViewA.setAdapter(adapterA);
        listViewB.setAdapter(adapterB);

        listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = (String)adapterA.getItem(position);
                Intent intent = new Intent(getActivity(), GameDataActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        listViewB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = (String)adapterB.getItem(position);
                Intent intent = new Intent(getActivity(), GameDataActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });


        teamA = new String("zz");
        teamB = new String("QQ");
        super.onCreate(savedInstanceState);
        TabHost host = (TabHost) rootView.findViewById(R.id.tabhost);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator(teamA);

        host.addTab(spec);

        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator(teamB);
        host.addTab(spec);
        return rootView;
    }

    private void addData(){
        dataList.add("mem1");
        dataList.add("mem2");

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
                    .inflate(R.layout.list_item_update_game,null);
            final TextView textview = (TextView)convertView.findViewById(R.id.update_game_textView);

            textview.setText(str);

            return convertView;
        }
    }
}
