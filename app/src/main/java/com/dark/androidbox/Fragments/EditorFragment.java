package com.dark.androidbox.Fragments;

import static com.dark.androidbox.Utilities.DarkUtilities.ShowMessage;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dark.androidbox.Adpaters.CodeAdapter;
import com.dark.androidbox.Adpaters.Codes;
import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;
import com.dark.androidbox.databinding.FragmentEditorBinding;
import com.gyso.treeview.GysoTreeView;
import com.gyso.treeview.TreeViewEditor;
import com.gyso.treeview.layout.CompactRightTreeLayoutManager;
import com.gyso.treeview.layout.TreeLayoutManager;
import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.line.SmoothLine;
import com.gyso.treeview.model.NodeModel;
import com.gyso.treeview.model.TreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EditorFragment extends Fragment implements NodeEvents {

    public GysoTreeView treeView;
    public TreeViewEditor editor;
    CodeAdapter adapter;
    TreeLayoutManager treeLayoutManager;
    private NodeModel<Codes> parentToRemoveChildren = null;
    private NodeModel<Codes> targetNode;

    public ArrayList<HashMap<String, Object>> dataList = new ArrayList<>();


    public EditorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editor, container, false);

        treeView = root.findViewById(R.id.base_tree_view);

        LoadData();

        adapter = new CodeAdapter(getActivity(), this, dataList);

        treeLayoutManager = getTreeLayoutManager();

        treeView.setAdapter(adapter);
        treeView.setTreeLayoutManager(treeLayoutManager);

        setData(adapter);

        editor = treeView.getEditor();

        //editor.requestMoveNodeByDragging(true);

        return root;
    }

    private TreeLayoutManager getTreeLayoutManager() {
        int space_50dp = 30;
        int space_20dp = 20;
        BaseLine line = getLine();
        //return new BoxRightTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxDownTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);


        //TODO !!!!! the layoutManagers below are just for test don't use in your projects. Just for test now
        //return new TableRightTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableDownTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);

        return new CompactRightTreeLayoutManager(getContext(), space_50dp, space_20dp, line);
        //return new CompactLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactDownTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);

        //return new CompactRingTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new ForceDirectedTreeLayoutManager(this,line);
    }

    private BaseLine getLine() {
        return new SmoothLine(Color.parseColor("#8EBBFF"), 2);
        //return new StraightLine(Color.parseColor("#055287"),2);
        //return new DashLine(Color.parseColor("#F1286C"),3);
        //return new AngledLine();
    }

    private void setData(CodeAdapter adapter) {
        //root
        NodeModel<Codes> root_node = new NodeModel<>(new Codes(0, "Root Node"));
        TreeModel<Codes> treeModel = new TreeModel<>(root_node);

        //child nodes
        NodeModel<Codes> child_node1 = new NodeModel<>(new Codes(1, "Child Node 1"));

        //build relationship
        treeModel.addNode(root_node, child_node1);

        //mark
        parentToRemoveChildren = root_node;
        targetNode = child_node1;

        //set data
        adapter.setTreeModel(treeModel);
    }
    @Override
    public void NodeOnLongClick() {

    }

    @Override
    public void NodeListItemsOnClick(int pos, ArrayList<HashMap<String, Object>> data) {
        ShowMessage(getContext(), new StringBuilder(Objects.requireNonNull(data.get(pos).get("data")).toString()));
    }

    @Override
    public void NodeListItemsOnLongClick() {

    }

    public void LoadData() {
        addData(dataList);
    }

    public void addData(ArrayList<HashMap<String, Object>> list_data) {
        DataArray(list_data, "data", "Click Me");
    }

    public void DataArray(ArrayList<HashMap<String, Object>> list_data, String Key, String Data) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(Key, Data);
        list_data.add(data);
    }
}