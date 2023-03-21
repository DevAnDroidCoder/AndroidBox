package com.dark.androidbox.Adpaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dark.androidbox.R;
import com.dark.androidbox.System.Actions;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class ActionList extends BaseAdapter {

    public ArrayList<Actions> mData;

    public ActionList(ArrayList<Actions> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.action_view, parent, false);
        }

        Chip chip = convertView.findViewById(R.id.select_btn);

        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });

        TextView txt = convertView.findViewById(R.id.action_txt);
        txt.setText(mData.get(position).getAction_label());

        return convertView;
    }

}

