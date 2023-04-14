package com.dark.androidbox.Adpaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dark.androidbox.Fragments.EditorFragment;
import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NodeListAdapter extends BaseAdapter {

    public ArrayList<String> mData;

    public NodeEvents events ;

    public NodeListAdapter(ArrayList<String> data, NodeEvents events) {
        mData = data;
        this.events = events;
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
    public View getView(int position, View root, ViewGroup parent) {
        if (root == null) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_nodes_list, parent, false);
        }

        TextView txt_label = root.findViewById(R.id.label_codeBlock);

        MaterialButton addNode =  root.findViewById(R.id.addNode);

        addNode.setOnClickListener(view -> events.AddNode(mData, position));

        txt_label.setText(mData.get(position));

        return root;
    }

}
