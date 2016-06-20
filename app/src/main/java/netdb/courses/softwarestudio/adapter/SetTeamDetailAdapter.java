package netdb.courses.softwarestudio.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netdb.courses.softwarestudio.R;

/**
 * Created by SeanKang on 2016/6/20.
 */
public class SetTeamDetailAdapter extends BaseAdapter {

    public List<String> dataList = new ArrayList<String>();
    public List<String> playerList = new ArrayList<String>();

    private int index;
    private static HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    private Activity mActivity;
    ViewHolder viewholder;

    public SetTeamDetailAdapter(Activity activity){
        mActivity = activity;
        addData();
        hashMap.clear();
    }

    public int numberOfData(){
        return dataList.size();
    }

    public static class ViewHolder {

        public final TextView textview;
        public final EditText editText;

        public ViewHolder(View view) {
            textview = (TextView) view.findViewById(R.id.set_team_textView);
            editText = (EditText) view.findViewById(R.id.set_team_editText);
        }
    }

    public HashMap<Integer, String> getHashMap(){
        return hashMap;
    }

    private void addData(){
        dataList.clear();
        playerList.clear();
        dataList.add("GGG");
        playerList.add("隊伍名稱:");
    }
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
        convertView = LayoutInflater.from(mActivity.getApplication())
                .inflate(R.layout.list_item_set_team,null);

        if(convertView.getTag() == null){
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewholder = (ViewHolder) convertView.getTag();

        viewholder.editText.setText(str);
        viewholder.textview.setText(pstr);

        viewholder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    index = position;
                return false;
            }
        });

        viewholder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hashMap.put(position, editable.toString());
                dataList.set(position, editable.toString());
            }
        });

        if(hashMap.get(position)!=null){
            viewholder.editText.setText(hashMap.get(position));
        }

        viewholder.editText.clearFocus();
        if(index != -1 && index == position){
            viewholder.editText.requestFocus();
        }

        return convertView;
    }
}
