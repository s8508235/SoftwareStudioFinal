package netdb.courses.softwarestudio.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import netdb.courses.softwarestudio.R;

/**
 * Created by Bill on 2016/6/19.
 */
public class GameDataFragment extends Fragment {
    private ListView listView;
    private String mTeamDetailStr;
    private mAdapter adapter;
    List<String> dataList = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("James");
        View rootView = inflater.inflate(R.layout.fragment_game_data, container, false);
        addData();
        listView = (ListView)rootView.findViewById(R.id.game_data_listView);

        adapter = new mAdapter();
        listView.setAdapter(adapter);

        return rootView;
    }
    private void addData(){

        dataList.add("AST:");
        dataList.add("STL:");
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
            final String str = dataList.get(position);

            convertView = LayoutInflater.from(getActivity().getApplication())
                    .inflate(R.layout.list_item_game_data,null);
            final TextView textview = (TextView)convertView.findViewById(R.id.game_data_textView);
            NumberPicker numPicker=(NumberPicker) convertView.findViewById(R.id.game_data_numberPicker);

            numPicker.setMaxValue(100);
            numPicker.setMinValue(0);
            numPicker.setValue(0);
            numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener (){
                public void onValueChange(NumberPicker view, int oldValue, int newValue) {
                    textview.setText(str + String.valueOf(newValue));

                }
            });
            textview.setText(str);



            return convertView;
        }
    }
}
