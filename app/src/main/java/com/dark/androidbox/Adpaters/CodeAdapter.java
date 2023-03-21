package com.dark.androidbox.Adpaters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;
import com.dark.androidbox.databinding.CodeNodesBinding;
import com.gyso.treeview.adapter.DrawInfo;
import com.gyso.treeview.adapter.TreeViewAdapter;
import com.gyso.treeview.adapter.TreeViewHolder;
import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.model.NodeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeAdapter extends TreeViewAdapter<Codes> {


    public Activity ctx;
    ArrayList<HashMap<String, Object>> rootList = new ArrayList<>();
    NodeEvents events;

    public CodeAdapter(Activity activity, NodeEvents events, ArrayList<HashMap<String, Object>> dataList) {
        this.ctx = activity;
        this.events = events;
        this.rootList = dataList;
    }

    @Override
    public TreeViewHolder<Codes> onCreateViewHolder(@NonNull ViewGroup viewGroup, NodeModel<Codes> model) {
        CodeNodesBinding nodeBinding = CodeNodesBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new TreeViewHolder<>(nodeBinding.getRoot(), model);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder<Codes> holder) {
        View items = holder.getView();

        LinearLayout head_node = items.findViewById(R.id.head_node);

        TextView label = items.findViewById(R.id.label_codeBlock);

        RecyclerView node_item = items.findViewById(R.id.node_items);

        TextView node_id = items.findViewById(R.id.node_id);

        NodeModel<Codes> nodeObj = holder.getNode();

        final Codes blockData = nodeObj.value;

        head_node.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                events.NodeOnLongClick();
                return false;
            }
        });

        node_item.setAdapter(new CodeItemsAdapter(ctx, rootList, events));

        node_item.setLayoutManager(new LinearLayoutManager(node_item.getContext(), LinearLayoutManager.VERTICAL, false));

        label.setText(blockData.label);
        node_id.setText("Node -> ".concat(String.valueOf(blockData.getItemId())));
    }

    @Override
    public BaseLine onDrawLine(DrawInfo drawInfo) {
        return null;
    }
}
