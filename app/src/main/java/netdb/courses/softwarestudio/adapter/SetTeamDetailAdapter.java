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
    public List<String> numberList = new ArrayList<String>();

    private int index, nindex;
    private static HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    private static HashMap<Integer, String> numhashMap = new HashMap<Integer, String>();
    private Activity mActivity;
    ViewHolder viewholder;

    public SetTeamDetailAdapter(Activity activity){
        mActivity = activity;
        addData();
        hashMap.clear();
        numhashMap.clear();
    }

    public static class ViewHolder {

        public final TextView textview;
        public final EditText editText;
        public final EditText numberText;

        public ViewHolder(View view) {
            textview = (TextView) view.findViewById(R.id.set_team_textView);
            editText = (EditText) view.findViewById(R.id.set_team_editText);
            numberText = (EditText) view.findViewById(R.id.set_team_numberText);
        }
    }

    public HashMap<Integer, String> getHashMap(){
        return hashMap;
    }

    private void addData(){
        dataList.clear();
        numberList.clear();
        playerList.clear();
        dataList.add("TEST");
        numberList.add("請輸入背號");
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
        String nstr = numberList.get(position);

        convertView = LayoutInflater.from(mActivity.getApplication())
                .inflate(R.layout.list_item_set_team,null);

        if(convertView.getTag() == null){
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewholder = (ViewHolder) convertView.getTag();

        viewholder.editText.setText(str);
        viewholder.textview.setText(pstr);
        viewholder.numberText.setText(nstr);

        numberList.set(position, nstr);
        dataList.set(position, str);

        if(position == 0)
            viewholder.numberText.setFocusable(false);
        else
            viewholder.numberText.setFocusable(true);

        viewholder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    index = position;
                return false;
            }
        });

        viewholder.numberText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    nindex = position;
                return false;
            }
        });

        viewholder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                hashMap.put(position, editable.toString());
                dataList.set(position, editable.toString());
            }
        });

        viewholder.numberText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                numhashMap.put(position, editable.toString());
                numberList.set(position, editable.toString());
            }
        });

        if(hashMap.get(position)!=null){
            viewholder.editText.setText(hashMap.get(position));
            dataList.set(position, hashMap.get(position));
        }
        if(numhashMap.get(position)!=null){
            viewholder.numberText.setText(numhashMap.get(position));
            numberList.set(position, numhashMap.get(position));
        }

        viewholder.numberText.clearFocus();
        viewholder.editText.clearFocus();

        if(nindex != -1 && nindex == position){
            viewholder.numberText.requestFocus();
        }

        if(index != -1 && index == position){
            viewholder.editText.requestFocus();
        }
        return convertView;
    }
}
